package com.cribl.search;

import static spark.Spark.get;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.cribl.file.FileReadType;
import com.cribl.string.match.SearchAlogType;
import com.google.common.base.Stopwatch;

import spark.Request;
import spark.Response;
import spark.Route;

public class App {
	public static void main(String[] args) {
		get("/echo", new Route() {
			@Override
			public Object handle(Request req, Response res) throws Exception {
				return "Running";
			}
		});

		// TODO Don't have time to make this API and method clear.
		// Ideally we should pass a Request object, have validation class
		// Add Rate limiting
		get("/search", new Route() {
			@Override
			public Object handle(Request request, Response response) {

				try {
					String filePath = request.queryParams("filePath");
					String search = request.queryParams("search");
					String n = request.queryParams("n");
					String fileRead = request.queryParams("fileReadType");
					String searchType = request.queryParams("searchAlgoType");
					int numLines = Integer.parseInt(n == null ? "0" : n);

					FileReadType fileReadType = Enum.valueOf(FileReadType.class,
							fileRead == null ? "Default" : fileRead);

					SearchAlogType searchAlgoType = Enum.valueOf(SearchAlogType.class,
							searchType == null ? "Default" : searchType);

					System.out.println("File Path:" + filePath + " search Word: " + search + " numLine: " + numLines
							+ " fileReadType: " + fileReadType + " searchAlgoType:" + searchAlgoType);

					Stopwatch stopwatch = Stopwatch.createStarted();
					// Main Logic
					ArrayList<String> list = FileSearch.search(filePath, search, numLines, fileReadType,
							searchAlgoType);

					stopwatch.stop();
					System.out.println("File Path:" + filePath + ", search Word: " + search + ", numLine: " + numLines
							+ ", fileReadType: " + fileReadType + ", searchAlgoType:" + searchAlgoType
							+ ", Total time in MilliSeconds: " + stopwatch.elapsed(TimeUnit.MILLISECONDS));
					response.status(200);
					return list;
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					response.status(500);
					return e.getMessage() + ": wrong Argument";
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					response.status(500);
					return e.getMessage();
				} catch (Exception e) {
					e.printStackTrace();
					response.status(500);
					return "Internal Error!";

				}
			}
		});

		System.out.println("All Systems GO!");
	}

}
