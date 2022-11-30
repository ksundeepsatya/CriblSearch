package com.cribl.string.match;

public class NoSearch extends SubStringBase {

	public NoSearch(String searchString) {
		super(searchString);
	}

	@Override
	protected boolean isSubStringInternal(String text) {
		// Always returns false
		return false;
	}

}
