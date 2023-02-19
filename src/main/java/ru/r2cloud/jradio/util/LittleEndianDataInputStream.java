package ru.r2cloud.jradio.util;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

public class LittleEndianDataInputStream implements DataInput {

	private byte[] readBuffer = new byte[8];
	private final DataInputStream dis;

	public LittleEndianDataInputStream(DataInputStream dis) {
		this.dis = dis;
	}

	public int read(byte[] b) throws IOException {
		return dis.read(b);
	}

	public int read() throws IOException {
		return dis.read();
	}

	@Override
	public final int readUnsignedByte() throws IOException {
		return dis.readUnsignedByte();
	}

	public int readUnsigned3Bytes() throws IOException {
		int b1 = dis.readUnsignedByte();
		int b2 = dis.readUnsignedByte();
		int b3 = dis.readUnsignedByte();
		if ((b1 | b2 | b3) < 0)
			throw new EOFException();
		return (b3 << 16) | (b2 << 8) | b1;
	}

	public long readUnsigned5Bytes() throws IOException {
		int b1 = dis.readUnsignedByte();
		int b2 = dis.readUnsignedByte();
		int b3 = dis.readUnsignedByte();
		int b4 = dis.readUnsignedByte();
		int b5 = dis.readUnsignedByte();
		if ((b1 | b2 | b3 | b4 | b5) < 0)
			throw new EOFException();
		return ((long)b5 << 32) | ((long)b4 << 24) | (b3 << 16) | (b2 << 8) | b1;
	}
	
	public long readUnsigned6Bytes() throws IOException {
		int b1 = dis.readUnsignedByte();
		int b2 = dis.readUnsignedByte();
		int b3 = dis.readUnsignedByte();
		int b4 = dis.readUnsignedByte();
		int b5 = dis.readUnsignedByte();
		int b6 = dis.readUnsignedByte();
		if ((b1 | b2 | b3 | b4 | b5 | b6) < 0)
			throw new EOFException();
		return ((long)b6 << 40) | ((long)b5 << 32) | ((long)b4 << 24) | (b3 << 16) | (b2 << 8) | b1;
	}
	
	@Override
	public final int readUnsignedShort() throws IOException {
		int ch1 = dis.read();
		int ch2 = dis.read();
		if ((ch1 | ch2) < 0)
			throw new EOFException();
		return (ch2 << 8) + ch1;
	}

	public int[] readUnsignedShort(int size) throws IOException {
		int[] result = new int[size];
		for (int i = 0; i < result.length; i++) {
			result[i] = readUnsignedShort();
		}
		return result;
	}

	public final long readUnsignedInt() throws IOException {
		return readUnsignedInt(dis);
	}

	public static final long readUnsignedInt(DataInputStream dis) throws IOException {
		int ch1 = dis.read();
		int ch2 = dis.read();
		int ch3 = dis.read();
		int ch4 = dis.read();
		if ((ch1 | ch2 | ch3 | ch4) < 0)
			throw new EOFException();
		return ((ch4 << 24) | (ch3 << 16) | (ch2 << 8) | ch1) & 0xFFFFFFFFL;
	}

	@Override
	public final float readFloat() throws IOException {
		return Float.intBitsToFloat(readInt());
	}

	public float[] readFloat(int size) throws IOException {
		float[] result = new float[size];
		for (int i = 0; i < result.length; i++) {
			result[i] = readFloat();
		}
		return result;
	}

	@Override
	public final short readShort() throws IOException {
		return readShort(dis);
	}

	public static final short readShort(DataInputStream dis) throws IOException {
		int ch1 = dis.read();
		int ch2 = dis.read();
		if ((ch1 | ch2) < 0)
			throw new EOFException();
		return (short) ((ch2 << 8) + ch1);
	}

	@Override
	public final int skipBytes(int n) throws IOException {
		return dis.skipBytes(n);
	}

	@Override
	public final long readLong() throws IOException {
		dis.readFully(readBuffer, 0, 8);
		return ((long) readBuffer[7] << 56) + ((long) (readBuffer[6] & 255) << 48) + ((long) (readBuffer[5] & 255) << 40) + ((long) (readBuffer[4] & 255) << 32) + ((long) (readBuffer[3] & 255) << 24) + ((readBuffer[2] & 255) << 16) + ((readBuffer[1] & 255) << 8) + (readBuffer[0] & 255);
	}

	@Override
	public final byte readByte() throws IOException {
		return dis.readByte();
	}

	@Override
	public void readFully(byte[] b) throws IOException {
		dis.readFully(b);
	}

	@Override
	public void readFully(byte[] b, int off, int len) throws IOException {
		dis.readFully(b, off, len);
	}

	@Override
	public boolean readBoolean() throws IOException {
		return dis.readBoolean();
	}

	@Override
	public char readChar() throws IOException {
		return dis.readChar();
	}

	@Override
	public int readInt() throws IOException {
		int ch1 = dis.read();
		int ch2 = dis.read();
		int ch3 = dis.read();
		int ch4 = dis.read();
		if ((ch1 | ch2 | ch3 | ch4) < 0)
			throw new EOFException();
		return ((ch4 << 24) + (ch3 << 16) + (ch2 << 8) + ch1);
	}

	@Override
	public double readDouble() throws IOException {
		return Double.longBitsToDouble(readLong());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @deprecated see java.io.DataInputStream.readLine
	 */
	@Override
	@Deprecated
	public String readLine() throws IOException {
		return dis.readLine();
	}

	@Override
	public String readUTF() throws IOException {
		return dis.readUTF();
	}

	public int available() throws IOException {
		return dis.available();
	}

	public short[] readShort(int size) throws IOException {
		short[] result = new short[size];
		for (int i = 0; i < result.length; i++) {
			result[i] = readShort();
		}
		return result;
	}

	public int[] readUnsignedByte(int size) throws IOException {
		int[] result = new int[size];
		for (int i = 0; i < result.length; i++) {
			result[i] = readUnsignedByte();
		}
		return result;
	}

	public DataInputStream getBigEndianDataInputStream() {
		return dis;
	}

	public long readVariableUnsignedInt(int numberOfBytes) throws IOException {
		long result = 0;
		for (int i = 0; i < numberOfBytes; i++) {
			int nextByte = readUnsignedByte() << (8 * i);
			result += nextByte;
		}
		return result;
	}
}
