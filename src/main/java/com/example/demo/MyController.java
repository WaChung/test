package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.aia.oae.framework.annotation.SingletonVariable;
import com.example.demo.bean.Person;

@Scope
@Controller
public class MyController {
	
	@Autowired
    private Person myService; // Allow
	
    private Person myService2; //Not Allow
    
	@SingletonVariable //Allow
	private String st1; 
	
    private String st2; // not Allow

    @SingletonVariable	//allow
    private int i1; 

    private int i2;// not Allow
    
    public static final String AAA = "AAA";//Allow
    
    private static final String BBB = "BBB";//Allow
     
    public void doSomething() {
        
    }
}
