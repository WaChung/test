package com.example.demo.bean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.annotation.SingletonVariable;

@Component
@Scope("singleton") // change to prototype
public class Person {
	
	@SingletonVariable
	private String name;
	
	public Person personSingleton() {
		return new Person();
	}

}