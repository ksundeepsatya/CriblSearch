package com.cribl.string.match;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

public abstract class SubStringBase implements ISubString {

    private long totalLength = 0L;
    private long numCalls = 0L;
    private long totalTimeinUs = 0L; 
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

		long micros = stopwatch.elapsed(TimeUnit.MICROSECONDS);
		
		totalLength+=text.length();
		numCalls++;
		totalTimeinUs+=micros;
		return result;
	}
	
	protected abstract boolean isSubStringInternal(String text);
	
	@Override
	public double avgTimeInUsToSubString() {
		// TODO Auto-generated method stub
		return (double) totalTimeinUs/numCalls;
	}

	@Override
	public long avgTextLength() {
		// TODO Auto-generated method stub
		return totalLength/numCalls;
	}

}
