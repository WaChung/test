package com.example.demo.mbean;

public interface BasicThreadMXBean {

	Long getNumberOfTimedWaitingThread();

	Long getNumberOfWaitingThread();

	Long getNumberOfBlockedThread();

	Long getNumberOfNewThread();

	Long getNumberOfRunnableThread();
	
	String getEnv();
}
