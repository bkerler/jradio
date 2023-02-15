package ru.r2cloud.jradio;

import java.io.IOException;

import ru.r2cloud.jradio.fec.ccsds.UncorrectableException;

public abstract class Beacon implements Externalizable {

	private byte[] rawData;
	private long beginSample;
	private long beginMillis;
	private long endSample;
	private RxMetadata rxMeta;

	@Override
	public void readExternal(byte[] data) throws IOException, UncorrectableException {
		this.rawData = data;
		readBeacon(data);
	}

	public abstract void readBeacon(byte[] data) throws IOException, UncorrectableException;

	public byte[] getRawData() {
		return rawData;
	}

	public void setRawData(byte[] rawData) {
		this.rawData = rawData;
	}

	public long getBeginMillis() {
		return beginMillis;
	}

	public void setBeginMillis(long beginMillis) {
		this.beginMillis = beginMillis;
	}

	public long getBeginSample() {
		return beginSample;
	}
	
	public void setBeginSample(long beginSample) {
		this.beginSample = beginSample;
	}

	public long getEndSample() {
		return endSample;
	}
	
	public void setEndSample(long endSample) {
		this.endSample = endSample;
	}
	
	public RxMetadata getRxMeta() {
		return rxMeta;
	}

	public void setRxMeta(RxMetadata rxMeta) {
		this.rxMeta = rxMeta;
	}
}
