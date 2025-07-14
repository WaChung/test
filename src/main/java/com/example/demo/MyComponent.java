package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.bean.Person;

@Component
public class MyComponent {
	
	
    private Person myService; // This should trigger the rule
    
    private String test;
    
    public void doSomething() {
        
    }
}