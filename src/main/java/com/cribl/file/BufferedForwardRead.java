package com.cribl.file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class BufferedForwardRead extends FileReaderBase {

	BufferedReader bufferedReader;
	public BufferedForwardRead(String filePath) throws FileNotFoundException
	{
		super(filePath);
		bufferedReader = new BufferedReader(new FileReader(file));
		
	}
	
	@Override
	protected String readLineInternal() throws IOException
	{
		return bufferedReader.readLine();
	}

	@Override
	public void close() throws IOException {
		if (bufferedReader != null)
		{
			bufferedReader.close();
		}
		
	}
	
	
}
