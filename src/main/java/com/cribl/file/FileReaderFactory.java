package com.cribl.file;

import java.io.IOException;

public class FileReaderFactory {

	public static IFileReader getFileReader(FileReadType readerType, String filePath) throws IOException {
		switch (readerType) {
		case ApacheReverse:
			return new ApacheReverseFileReader(filePath);
		case ForwardRead:
			return new BufferedForwardRead(filePath);
		case RandomAccessReverse:
		case Default:
		default:
			return new RandomFileAccessReverseRead(filePath);
		}
	}
}
