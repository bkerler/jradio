package ru.r2cloud.jradio.cute;

import java.io.DataInputStream;
import java.io.IOException;

import ru.r2cloud.jradio.ax25.Ax25Beacon;
import ru.r2cloud.jradio.ccsds.PrimaryHeader;
import ru.r2cloud.jradio.fec.ccsds.UncorrectableException;
import ru.r2cloud.jradio.util.BitInputStream;

public class CuteBeacon extends Ax25Beacon {

	private PrimaryHeader primary;
	private SecondaryHeader secondary;
	private BctSoh bctSoh;
	private PrimaryHeader payloadPrimary;
	private CutePayloadSwStat payloadSwStat;
	private byte[] unknownPayload;

	@Override
	public void readBeacon(DataInputStream dis) throws IOException, UncorrectableException {
		BitInputStream bis = new BitInputStream(dis);
		primary = new PrimaryHeader(bis);
		if (primary.isSecondaryHeader()) {
			secondary = new SecondaryHeader(dis);
		}
		switch (primary.getApplicationProcessId()) {
		case 0x56:
			bctSoh = new BctSoh(dis);
			break;
		case 0x1ff:
			System.out.println(Integer.toHexString(primary.getApplicationProcessId()));
			payloadPrimary = new PrimaryHeader(bis);
			switch (payloadPrimary.getApplicationProcessId()) {
			case 1:
				payloadSwStat = new CutePayloadSwStat(dis);
				break;
			default:
				unknownPayload = new byte[dis.available()];
				dis.readFully(unknownPayload);
				break;
			}
			break;
		default:
			unknownPayload = new byte[dis.available()];
			dis.readFully(unknownPayload);
			break;
		}
	}

	public PrimaryHeader getPrimary() {
		return primary;
	}

	public void setPrimary(PrimaryHeader primary) {
		this.primary = primary;
	}

	public SecondaryHeader getSecondary() {
		return secondary;
	}

	public void setSecondary(SecondaryHeader secondary) {
		this.secondary = secondary;
	}

	public BctSoh getBctSoh() {
		return bctSoh;
	}

	public void setBctSoh(BctSoh bctSoh) {
		this.bctSoh = bctSoh;
	}

	public PrimaryHeader getPayloadPrimary() {
		return payloadPrimary;
	}

	public void setPayloadPrimary(PrimaryHeader payloadPrimary) {
		this.payloadPrimary = payloadPrimary;
	}

	public CutePayloadSwStat getPayloadSwStat() {
		return payloadSwStat;
	}

	public void setPayloadSwStat(CutePayloadSwStat payloadSwStat) {
		this.payloadSwStat = payloadSwStat;
	}

	public byte[] getUnknownPayload() {
		return unknownPayload;
	}

	public void setUnknownPayload(byte[] unknownPayload) {
		this.unknownPayload = unknownPayload;
	}

}