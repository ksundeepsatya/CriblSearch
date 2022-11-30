package com.cribl.string.match;

public class InBuiltContains extends SubStringBase {

	public InBuiltContains(String searchString) {
		super(searchString);
	}

	@Override
	protected boolean isSubStringInternal(String text) {
		return text.contains(searchString);
	}

}
