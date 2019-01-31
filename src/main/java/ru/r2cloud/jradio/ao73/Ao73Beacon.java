package ru.r2cloud.jradio.ao73;

import java.io.IOException;

import ru.r2cloud.jradio.Externalizable;
import ru.r2cloud.jradio.util.BitInputStream;

public class Ao73Beacon implements Externalizable {

	private SatID id;
	private FrameType frameType;
	private RealtimeTelemetry realtimeTelemetry;
	private byte[] payload;

	private byte[] rawData;
	private long beginSample;
	private long beginMillis;

	@Override
	public void readExternal(byte[] data) throws IOException {
		this.rawData = data;
		BitInputStream dis = new BitInputStream(data);
		id = SatID.valueOfCode(dis.readUnsignedInt(2));
		frameType = FrameType.valueOfCode(dis.readUnsignedInt(6));
		realtimeTelemetry = new RealtimeTelemetry(dis);
		payload = new byte[200];
		dis.readFully(payload);
	}

	public SatID getId() {
		return id;
	}

	public void setId(SatID id) {
		this.id = id;
	}

	public FrameType getFrameType() {
		return frameType;
	}

	public void setFrameType(FrameType frameType) {
		this.frameType = frameType;
	}

	public byte[] getRawData() {
		return rawData;
	}

	public void setRawData(byte[] rawData) {
		this.rawData = rawData;
	}

	public long getBeginSample() {
		return beginSample;
	}

	public void setBeginSample(long beginSample) {
		this.beginSample = beginSample;
	}

	public long getBeginMillis() {
		return beginMillis;
	}

	public void setBeginMillis(long beginMillis) {
		this.beginMillis = beginMillis;
	}

	public RealtimeTelemetry getRealtimeTelemetry() {
		return realtimeTelemetry;
	}

	public void setRealtimeTelemetry(RealtimeTelemetry realtimeTelemetry) {
		this.realtimeTelemetry = realtimeTelemetry;
	}

	public byte[] getPayload() {
		return payload;
	}

	public void setPayload(byte[] payload) {
		this.payload = payload;
	}

}
