package com.cribl.string.match;

public class SubStringBuilderFactory {

	public static ISubString getSubStringFinder(SearchAlogType algoType, String searchString)
	{
		switch (algoType) {
		case BoyerMoore:
			return new BoyerMooreStringSearch(searchString);
		case RabinKarp:
			return new RabinKarpStringSearch(searchString);
		case JavaContains:
			return new InBuiltContains(searchString);
		case NoSearch:
			return new NoSearch(searchString);
		case Default:
		default:
			return new BoyerMooreStringSearch(searchString);
		}
	}
}
