package ru.r2cloud.jradio;

import java.io.EOFException;
import java.io.IOException;

public class ArrayMessageInput implements MessageInput {

	private final byte[] input;
	private final Context context;
	private boolean read = false;

	public ArrayMessageInput(byte[] input) {
		this(new Context(), input);
	}

	public ArrayMessageInput(Context context, byte[] input) {
		this.input = input;
		this.context = context;
	}

	@Override
	public byte[] readBytes() throws IOException {
		if (read) {
			throw new EOFException();
		}
		read = true;
		return input;
	}

	@Override
	public void close() throws IOException {
		// do nothing
	}

	@Override
	public Context getContext() {
		return context;
	}
}
