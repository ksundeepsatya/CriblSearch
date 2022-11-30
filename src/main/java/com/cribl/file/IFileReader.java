package com.cribl.file;

import java.io.Closeable;
import java.io.IOException;

public interface IFileReader extends Closeable{

	public String readLine() throws IOException;
	
	public double avgTimeToReadLineInMicroSeconds();
	
	public long avgReadLength();
}
