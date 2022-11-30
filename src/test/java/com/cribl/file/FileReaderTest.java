package com.cribl.file;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class FileReaderTest {

	String[] lines = { "First", "Second", "Third", "Fourth", "Fifth" };
	String filePath = "filePath";
	File file;

	@Before
	public void CreateFile() throws IOException {
		file = new File(filePath);
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		for (String line : lines)
			raf.writeBytes(line + "\n");
		raf.close();
	}

	@Test
	public void testRFA() throws IOException {
		try (IFileReader fileReader = new RandomFileAccessReverseRead(filePath)) {
			testReverse(fileReader);
		}
	}

	@Test
	public void testReverseFileReader() throws IOException {
		try (IFileReader fileReader = new ApacheReverseFileReader(filePath)) {
			testReverse(fileReader);
		}

	}

	@Test
	public void testForwardReader() throws IOException {

		try (IFileReader fileReader = new BufferedForwardRead(filePath)) {
			testForward(fileReader);
		}
	}

	private void testReverse(IFileReader fileReader) throws IOException {
		ArrayList<String> list = new ArrayList<>();
		String line = "";
		while ((line = fileReader.readLine()) != null)
			list.add(line);

		int i = lines.length - 1;
		for (String l : list) {
			assertEquals(l, lines[i--]);
		}
	}

	private void testForward(IFileReader fileReader) throws IOException {
		ArrayList<String> list = new ArrayList<>();
		String line = "";
		while ((line = fileReader.readLine()) != null)
			list.add(line);

		int i = 0;
		for (String l : list) {
			assertEquals(l, lines[i++]);
		}
	}

}
