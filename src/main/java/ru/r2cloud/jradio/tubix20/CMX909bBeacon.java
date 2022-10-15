package ru.r2cloud.jradio.tubix20;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import ru.r2cloud.jradio.Beacon;
import ru.r2cloud.jradio.crc.Crc16Ccitt;
import ru.r2cloud.jradio.fec.Crc16CcittFec;
import ru.r2cloud.jradio.fec.Hamming;
import ru.r2cloud.jradio.fec.ccsds.UncorrectableException;
import ru.r2cloud.jradio.mobitex.Header;
import ru.r2cloud.jradio.util.Deinterleave;
import ru.r2cloud.jradio.util.GapData;

public abstract class CMX909bBeacon extends Beacon {

	public static final int MAX_SIZE = 1 + 1 + 1 + 6 + 2 + 32 * 30;
	public static final int BLOCK_SIZE_BYTES = 18;

	private Header header;

	// unable to decode
	private byte[] shortDataBlock;
	private String callsign;

	@Override
	public void readBeacon(byte[] data) throws IOException, UncorrectableException {
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
		header = new Header(dis);
		if (header.getControl1().getType() == null) {
			throw new UncorrectableException("unknown message type");
		}
		byte[] callsignBytes = new byte[6];
		dis.readFully(callsignBytes);
		int expectedCallsignCrc = (dis.readUnsignedByte() << 8) | dis.readUnsignedByte();
		int crc16 = Crc16Ccitt.calculate(callsignBytes);
		boolean crcMatched = (crc16 == expectedCallsignCrc);
		if (!crcMatched) {
			crcMatched = Crc16CcittFec.fix1bitUsingCrc(callsignBytes, expectedCallsignCrc);
		}
		// callsign is not covered by FEC blocks and most likely would be corrupted
		// continue further as callsign is not that important
		// set it up only if CRC matched
		if (crcMatched) {
			callsign = new String(callsignBytes, StandardCharsets.ISO_8859_1);
		}

		MobitexRandomizer randomizer = new MobitexRandomizer();
		switch (header.getControl1().getType()) {
		case ACK:
		case ERROR_CORRECTION:
			shortDataBlock = readShortDataBlock(randomizer, dis);
			break;
		default:
			readFrameData(readDataBlocks(header.getControl1().getNumberOfBlocks(), randomizer, dis));
			break;
		}
	}

	public static GapData readGapDataBlocks(int numberOfBlocks, MobitexRandomizer randomizer, DataInputStream dis) throws IOException, UncorrectableException {
		GapData result = null;
		int gaps = 0;
		for (int i = 0; i < numberOfBlocks; i++) {
			try {
				byte[] block = readDatablock(randomizer, dis, BLOCK_SIZE_BYTES);
				if (result == null) {
					result = new GapData(numberOfBlocks);
					for (int j = 0; j < gaps; j++) {
						result.gap(BLOCK_SIZE_BYTES);
					}
				}
				result.write(block);
			} catch (UncorrectableException e) {
				if (result != null) {
					result.gap(BLOCK_SIZE_BYTES);
				}
				gaps++;
			}
		}
		if (result == null) {
			throw new UncorrectableException("no blocks recovered");
		}
		return result;
	}

	public static byte[] readDataBlocks(int numberOfBlocks, MobitexRandomizer randomizer, DataInputStream dis) throws IOException, UncorrectableException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		for (int i = 0; i < numberOfBlocks; i++) {
			try {
				byte[] block = readDatablock(randomizer, dis, BLOCK_SIZE_BYTES);
				baos.write(block);
			} catch (UncorrectableException e) {
				// if some data recovered, then return it
				// at least some SourcePacket might be recovered
				if (baos.size() > 0) {
					return baos.toByteArray();
				} else {
					// if this is the first block and we cannot recover it
					// then throw Exception. most likely whole packet is invalid
					throw e;
				}
			}
		}
		return baos.toByteArray();
	}

	protected abstract void readFrameData(byte[] data) throws IOException;

	public static byte[] readShortDataBlock(MobitexRandomizer randomizer, DataInputStream dis) throws IOException, UncorrectableException {
		return readDatablock(randomizer, dis, 4);
	}

	private static byte[] readDatablock(MobitexRandomizer randomizer, DataInputStream dis, int length) throws IOException, UncorrectableException {
		byte[] blockData = new byte[length + 2 + (length + 2) * 4 / 8];
		dis.readFully(blockData);
		randomizer.shuffle(blockData);

		byte[] data = Arrays.copyOfRange(blockData, 0, length + 2);
		byte[] fecData = Arrays.copyOfRange(blockData, length + 2, blockData.length);

		byte[] deinterleaved = Deinterleave.deinterleaveBits(data, 8, data.length);
		byte[] fecDeinterleaved = Deinterleave.deinterleaveBits(fecData, 4, data.length);
		for (int i = 0; i < deinterleaved.length; i++) {
			deinterleaved[i] = (byte) Hamming.decode12b8((deinterleaved[i] << 4) | (fecDeinterleaved[i] & 0xFF));
		}

		int crc16 = Crc16Ccitt.calculateReverse(deinterleaved, 0, length);
		int expectedCrc = ((deinterleaved[deinterleaved.length - 2] & 0xFF) << 8) | (deinterleaved[deinterleaved.length - 1] & 0xFF);
		if (crc16 != expectedCrc) {
			throw new UncorrectableException("bad data block. crc mismatch");
		}
		return Arrays.copyOfRange(deinterleaved, 0, length);
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public byte[] getShortDataBlock() {
		return shortDataBlock;
	}

	public void setShortDataBlock(byte[] shortDataBlock) {
		this.shortDataBlock = shortDataBlock;
	}

	public String getCallsign() {
		return callsign;
	}

	public void setCallsign(String callsign) {
		this.callsign = callsign;
	}

}
