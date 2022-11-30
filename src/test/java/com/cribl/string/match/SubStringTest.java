package com.cribl.string.match;

import static org.junit.Assert.*;

import org.junit.Test;

public class SubStringTest {

	@Test
	public void testBoyerMoore() {

		String word = "World";
		BoyerMooreStringSearch search  = new BoyerMooreStringSearch(word);
		testSubStringFound(search);

		
		word = "unierse";
		search  = new BoyerMooreStringSearch(word);
		testSubStringNotFound(search);
	
	}
	
	@Test
	public void testRabinKarp() {

		String word = "World";
		ISubString search  = new RabinKarpStringSearch(word);
		testSubStringFound(search);

		
		word = "unierse";
		search  = new RabinKarpStringSearch(word);
		testSubStringNotFound(search);
		
		double c = search.avgTimeInUsToSubString();
	}
	
	@Test
	public void testNativeContains() {

		String word = "World";
		ISubString search  = new InBuiltContains(word);
		testSubStringFound(search);

		
		word = "unierse";
		search  = new InBuiltContains(word);
		testSubStringNotFound(search);
	}

	@Test
	public void NoSearch() {

		String word = "World";
		ISubString search  = new NoSearch(word);
		String text = "Hello World";
		assertFalse( search.isSubString(text));

		
		word = "unierse";
		search  = new InBuiltContains(word);
		testSubStringNotFound(search);
		
	}

	
	private void testSubStringFound(ISubString search) {
		String text = "Hello World";
		 assertTrue( search.isSubString(text));
		 assertEquals(search.avgTextLength(), text.length());

	}
	
	private void testSubStringNotFound(ISubString search) {
		String text = "Hello World";
		 assertFalse( search.isSubString(text));
		 assertEquals(search.avgTextLength(), text.length());
	}

}
