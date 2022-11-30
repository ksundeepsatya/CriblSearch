package com.cribl.file;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.input.ReversedLinesFileReader;

public class ApacheReverseFileReader extends FileReaderBase {

	ReversedLinesFileReader reverseReader;

	public ApacheReverseFileReader(String filePath) throws IOException {
		super(filePath);
		reverseReader = new ReversedLinesFileReader(file, BUFFER_SIZE, Charset.defaultCharset());
	}

	@Override
	public void close() throws IOException {
		if (reverseReader != null)
			reverseReader.close();

	}

	@Override
	protected String readLineInternal() throws IOException {
		return reverseReader.readLine();
	}

}
