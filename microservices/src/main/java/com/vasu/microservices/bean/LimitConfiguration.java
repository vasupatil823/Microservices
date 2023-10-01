package com.vasu.microservices.bean;

public class LimitConfiguration {

	private int maximum;
	private int minimum;
	
	public LimitConfiguration() {
		
	}
	
	public LimitConfiguration(int maximim, int minimum) {
		super();
		this.maximum = maximim;
		this.minimum = minimum;
	}

	public int getMaximum() {
		return maximum;
	}

	public int getMinimum() {
		return minimum;
	}
	
	
}
