package com.cribl.search;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.cribl.file.FileReadType;
import com.cribl.string.match.SearchAlogType;

/**
 * Unit test for search.
 */
public class AppTest {
	String[] lines = { "First Search", "Second", "Third Search", "Fourth", "Fifth Search" };
	String filePath = "filePath";
	File file;

	@Before
	public void CreateFile() throws IOException {
		file = new File(filePath);
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		for (String line : lines)
			raf.writeBytes(line + "\n");
		raf.close();

		FileSearch.DIRECTORY_BASE = ".";
	}

	@Test
	public void testSearch() throws FileNotFoundException, IOException {

		ArrayList<String> result = FileSearch.search(filePath, "Search", 2, FileReadType.Default,
				SearchAlogType.Default);

		assertEquals(result.size(), 2);
		assertEquals(result.get(0), lines[4]);

		assertEquals(result.get(1), lines[2]);
	}
	
	@Test
	public void testSearchAll() throws FileNotFoundException, IOException {

		ArrayList<String> result = FileSearch.search(filePath, "Search", 0, FileReadType.ForwardRead,
				SearchAlogType.Default);

		assertEquals(result.size(), 3);
		assertEquals(result.get(0), lines[4]);

		assertEquals(result.get(1), lines[2]);

		assertEquals(result.get(2), lines[0]);
	}
	
	@Test
	public void testSearchPermutation() throws FileNotFoundException, IOException {
		
		FileReadType[] filereadTypes = {FileReadType.ApacheReverse, FileReadType.RandomAccessReverse};
		SearchAlogType[] searchAlgoTypes = {SearchAlogType.BoyerMoore, SearchAlogType.JavaContains, SearchAlogType.RabinKarp};
		
		for(FileReadType fileReadType : filereadTypes)
		{
			for (SearchAlogType searchAlogType : searchAlgoTypes)
			{
				ArrayList<String> result = FileSearch.search(filePath, "Search", 2, fileReadType,
						searchAlogType);

				assertEquals(result.size(), 2);
				assertEquals(result.get(0), lines[4]);

				assertEquals(result.get(1), lines[2]);
			}
		}
	}
}
