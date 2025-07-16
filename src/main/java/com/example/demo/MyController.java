package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.aia.oae.framework.annotation.SingletonVariable;
import com.example.demo.bean.Person;

@Controller
public class MyController {
	
	@Autowired
    private Person myService; // Allow
	
    private Person myService2; //Not Allow
    
	@SingletonVariable //Allow
	private String test2; 
	
    private String test; // not Allow

    @SingletonVariable	//allow
    private int test4; 

    private int test3;// not Allow
    
    public static final String AAA = "AAA";//Allow
    
    private static final String BBB = "BBB";//Allow
     
    public void doSomething() {
        
    }
}
