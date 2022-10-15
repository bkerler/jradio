package ru.r2cloud.jradio.beesat4;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.r2cloud.jradio.fec.ccsds.UncorrectableException;
import ru.r2cloud.jradio.tubix20.TUBiX20Beacon;

public class Beesat4Beacon extends TUBiX20Beacon {

	private List<TransferFrame> frames = new ArrayList<>();

	@Override
	public void readBeacon(DataInputStream dis) throws IOException, UncorrectableException {
		while (true) {
			try {
				TransferFrame cur = new TransferFrame();
				cur.readExternal(dis);
				frames.add(cur);
			} catch (EOFException e) {
				break;
			}
		}
	}

	public List<TransferFrame> getFrames() {
		return frames;
	}

	public void setFrames(List<TransferFrame> frames) {
		this.frames = frames;
	}

}
