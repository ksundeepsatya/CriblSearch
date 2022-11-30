package com.cribl.search;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.jetty.util.StringUtil;

import com.cribl.file.FileReadType;
import com.cribl.file.FileReaderFactory;
import com.cribl.file.IFileReader;
import com.cribl.string.match.ISubString;
import com.cribl.string.match.SearchAlogType;
import com.cribl.string.match.SubStringBuilderFactory;

public class FileSearch {

	// TODO Improve this hard coding
	public static String DIRECTORY_BASE = "/hostlog";

	public static ArrayList<String> search(String fileName, String search, int n, FileReadType readerType,
			SearchAlogType alogType) throws FileNotFoundException, IOException {
		if (search == null || StringUtil.isBlank(search))
			throw new IllegalArgumentException("search word");
		if (fileName == null || StringUtil.isBlank(fileName))
			throw new IllegalArgumentException("fileName");
		String filePath = DIRECTORY_BASE + "/" + fileName;
		File f = new File(filePath);
		if (!f.exists()) {
			throw new FileNotFoundException("fileName: " + fileName + " Not found in /var/log");
		}

		ArrayList<String> list = new ArrayList<String>();

		try (IFileReader reader = FileReaderFactory.getFileReader(readerType, filePath)) {
			ISubString subString = SubStringBuilderFactory.getSubStringFinder(alogType, search);

			String readLine = "";
			while ((readLine = reader.readLine()) != null) {
				if (subString.isSubString(readLine))
					list.add(readLine);
				if (list.size() == n && readerType != FileReadType.ForwardRead)
					break;
			}
			
			System.out.println("Average Time to read a line in micro seconds: " + reader.avgTimeToReadLineInMicroSeconds());
			System.out.println("Average Time to search a line in micro seconds: " + subString.avgTimeInUsToSubString());
			System.out.println("Average line length in micro seconds: " + reader.avgReadLength());
			
		}

		if (readerType == FileReadType.ForwardRead)
			Collections.reverse(list);
		return list;
	}
}
