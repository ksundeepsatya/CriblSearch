package com.cribl.string.match;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

public abstract class SubStringBase implements ISubString {

    private long totalLength = 0L;
    private long numCalls = 0L;
    private long totalTimeinMs = 0L; 
    protected String searchString;
    public SubStringBase(String searchString)
    {
    	this.searchString = searchString;
    }
	@Override
	public boolean isSubString(String text) {
		
		Stopwatch stopwatch = Stopwatch.createStarted();
		boolean result = isSubStringInternal(text);
		stopwatch.stop();

		long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		
		totalLength+=text.length();
		numCalls++;
		totalTimeinMs+=millis;
		return result;
	}
	
	protected abstract boolean isSubStringInternal(String text);
	
	@Override
	public double avgTimeInMsToSubString() {
		// TODO Auto-generated method stub
		return (double) totalTimeinMs/numCalls;
	}

	@Override
	public long avgTextLength() {
		// TODO Auto-generated method stub
		return totalLength/numCalls;
	}

}
