package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.bean.Person;

@Component
public class MyComponent {
    @Autowired
    private Person myService; // This should trigger the rule

    public void doSomething() {
        
    }
}