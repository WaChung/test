package com.example.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.ReflectionException;

public class DynamicApplicationInfo implements DynamicMBean{

	// Counter per thread
	private static Map<String, Integer> counterPerThread = new HashMap<>();

	private MBeanInfo dMBeanInfo = null;

	public DynamicApplicationInfo() {

		buildDynamicMBeanInfo();
	}

	// Method to increment counter for given thread.
	public static Integer incrementOfThread(String threadName) {
		if (counterPerThread.get(threadName) == null) {
			counterPerThread.put(threadName, 0);
		} else 
			counterPerThread.put(threadName + counterPerThread.get(threadName) + 1 , 0);
		//counterPerThread.put(threadName, counterPerThread.get(threadName) + 1);
		System.out.println("Incremented for thread " + threadName + " count = " + counterPerThread.get(threadName));
		return counterPerThread.get(threadName);
	}

	@Override
	public Object getAttribute(String attribute)
			throws AttributeNotFoundException, MBeanException, ReflectionException {
		counterPerThread.put(attribute, counterPerThread.get(attribute) + 1);
		return counterPerThread.get(attribute);
	}

	@Override
	public MBeanInfo getMBeanInfo() {

		return dMBeanInfo;
	}

	private void buildDynamicMBeanInfo() {

		MBeanAttributeInfo[] dAttributes = new MBeanAttributeInfo[counterPerThread.keySet().size()];

		// Dynamically Build one attribute per thread.
		List<String> threadNames = counterPerThread.keySet().stream().collect(Collectors.toList());
		for (int i = 0; i < dAttributes.length; i++) {
			dAttributes[i] = new MBeanAttributeInfo(threadNames.get(i), Integer.class.getSimpleName(),
					"Counter for thread", true, false, false);
		}

		dMBeanInfo = new MBeanInfo(this.getClass().getName(), null, dAttributes, null, null,
				new MBeanNotificationInfo[0]);
	}

	/*
	 * For this example, we won't have implementation for below methods.
	 */
	@Override
	public void setAttribute(Attribute attribute)
			throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
		// No setters for this example

	}

	@Override
	public AttributeList getAttributes(String[] attributes) {
		// Skip for this example
		return null;
	}

	@Override
	public AttributeList setAttributes(AttributeList attributes) {
		// No setters for this example
		return null;
	}

	@Override
	public Object invoke(String actionName, Object[] params, String[] signature)
			throws MBeanException, ReflectionException {
		// No Operations for this example
		return null;
	}
}
