package com.cribl.file;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

public abstract class FileReaderBase implements IFileReader {

	protected static final int BUFFER_SIZE = 8192;
	private long totalLength = 0L;
    private long numCalls = 0L;
    private long totalTimeinUs = 0L; 
    File file;
    public FileReaderBase(String filePath)
    {
    	file = new File(filePath);
    }
    
	@Override
	public String readLine() throws IOException {
		Stopwatch stopwatch = Stopwatch.createStarted();
		String result = readLineInternal();
		stopwatch.stop();

		long micros = stopwatch.elapsed(TimeUnit.MICROSECONDS);
		
		if (result != null)
		{ 
			totalLength+=result.length();
			numCalls++;
			totalTimeinUs+=micros;
		}
		return result;
	}
	
	protected abstract String readLineInternal() throws IOException;

	@Override
	public double avgTimeToReadLineInMicroSeconds() {
		return totalTimeinUs/numCalls;
	}
	
	@Override
	public long avgReadLength() {
		return totalLength/numCalls;
	}

}
