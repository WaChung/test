package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aia.oae.SingletonVariable;
import com.example.demo.bean.Person;

@Component
public class MyComponent {
	
	@Autowired
    private Person myService; // This should trigger the rule
	
    private Person myService2; // This should trigger the rule
    
	@SingletonVariable
	private String test2;
	
    private String test;

    private int test3;

	@SingletonVariable	
    private int test4;
    
    public static final String AAA = "AAA";
    
    public void doSomething() {
        
    }
}