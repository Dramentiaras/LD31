package com.goudagames.ld31.util;

public class Time {

	private static long prevTime, time;
	private static float delta;
	
	public static long getTime() {
		
		return System.currentTimeMillis();
	}
	
	public static void init() {
		
		time = prevTime = getTime();
	}
	
	public static void update() {
		
		prevTime = time;
		time = getTime();
		delta = (float)(time - prevTime) / 1000f;
	}
	
	public static float getDelta() {
		
		return delta;
	}
}
