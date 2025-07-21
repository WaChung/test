package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.aia.oae.framework.annotation.SingletonVariable;
import com.example.demo.bean.Person;

@Controller
public class MyController {
	
	@Autowired
    private Person myService3; // Allow
	
    private Person myService4; //Not Allow
    
	@SingletonVariable //Allow
	private String st3; 
	
    private String st5; // not Allow

    @SingletonVariable	//allow
    private int i1; 

    private int i2;// not Allow
    
    public static final String AAA = "AAA";//Allow
    
    private static final String BBB = "BBB";//Allow
     
}
