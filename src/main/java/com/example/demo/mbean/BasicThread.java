package com.example.demo.mbean;

import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource
public class BasicThread implements BasicThreadMXBean {

	@ManagedOperation
	public Long getNumberOfTimedWaitingThread() {
//    	long t = Thread.getAllStackTraces().keySet().stream().filter( s
//    			-> t.getState().compareTo(t.getState().TIMED_WAITING) == 0).count();
		long t = Thread.getAllStackTraces().keySet().stream()
				.filter(a -> a.getState().compareTo(Thread.State.TIMED_WAITING) == 0).count();
		return t;
	}

	@Override
	@ManagedOperation
	public Long getNumberOfWaitingThread() {

		long t = Thread.getAllStackTraces().keySet().stream()
				.filter(a -> a.getState().compareTo(Thread.State.WAITING) == 0).count();
		return t;
	}

	@Override
	@ManagedOperation
	public Long getNumberOfBlockedThread() {
		long t = Thread.getAllStackTraces().keySet().stream()
				.filter(a -> a.getState().compareTo(Thread.State.BLOCKED) == 0).count();
		return t;
	}

	@Override
	@ManagedOperation
	public Long getNumberOfNewThread() {
		long t = Thread.getAllStackTraces().keySet().stream().filter(a -> a.getState().compareTo(Thread.State.NEW) == 0)
				.count();
		return t;
	}

	@Override
	@ManagedOperation
	public Long getNumberOfRunnableThread() {
		long t = Thread.getAllStackTraces().keySet().stream()
				.filter(a -> a.getState().compareTo(Thread.State.RUNNABLE) == 0).count();
		return t;
	}

	@Override
	public String getEnv() {
		// TODO Auto-generated method stub
		return "SIT";
	}

}
