package com.example.demo.bean;

import java.util.concurrent.atomic.AtomicLong;

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

	Boolean isSameNumberValue(AtomicLong a, AtomicLong b) {
		return a.equals(b); // Noncompliant, this is true only if a == b
	}
}