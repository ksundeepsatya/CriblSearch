package com.cribl.file;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class RandomFileAccessReverseRead extends FileReaderBase {

	private final FileChannel channel;
	private long filePos;
	private ByteBuffer buf;
	private int bufPos;
	private ByteArrayOutputStream baos = new ByteArrayOutputStream();
	private RandomAccessFile randomAccessFile;
	private String encoding = "UTF-8";

	long pointer;

	public RandomFileAccessReverseRead(String filePath) throws IOException {
		super(filePath);

		randomAccessFile = new RandomAccessFile(file, "r");
		channel = randomAccessFile.getChannel();
		filePos = randomAccessFile.length();
	}

	@Override
	public void close() throws IOException {
		if (randomAccessFile != null)
			randomAccessFile.close();

	}

	@Override
	public String readLineInternal() throws IOException {
		byte c;
		while (true) {
			if (bufPos < 0) {
				if (filePos == 0) {
					if (baos == null) {
						return null;
					}
					String line = bufToString();
					baos = null;
					return line;
				}
				long start = Math.max(filePos - BUFFER_SIZE, 0);
				long end = filePos;
				long len = end - start;
				buf = channel.map(FileChannel.MapMode.READ_ONLY, start, len);
				bufPos = (int) len;
				filePos = start;
				// Ignore Empty New Lines
				c = buf.get(--bufPos);
				if (c == '\r' || c == '\n')
					while (bufPos > 0 && (c == '\r' || c == '\n')) {
						bufPos--;
						c = buf.get(bufPos);
					}
				if (!(c == '\r' || c == '\n'))
					bufPos++;// IS THE NEW LENE
			}
			/*
			 * This will ignore all blank new lines.
			 */
			while (bufPos-- > 0) {
				c = buf.get(bufPos);
				if (c == '\r' || c == '\n') {
					// skip \r\n
					while (bufPos > 0 && (c == '\r' || c == '\n')) {
						c = buf.get(--bufPos);
					}
					// restore cursor
					if (!(c == '\r' || c == '\n'))
						bufPos++;// IS THE NEW Line
					return bufToString();
				}
				baos.write(c);
			}
		}
	}

	private String bufToString() throws UnsupportedEncodingException {
		if (baos.size() == 0) {
			return "";
		}
		byte[] bytes = baos.toByteArray();
		for (int i = 0; i < bytes.length / 2; i++) {
			byte t = bytes[i];
			bytes[i] = bytes[bytes.length - i - 1];
			bytes[bytes.length - i - 1] = t;
		}
		baos.reset();
		if (encoding != null)
			return new String(bytes, encoding);
		else
			return new String(bytes);
	}

}
