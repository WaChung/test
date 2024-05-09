package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.trace.log.MethodTrace;
@Service
public class TestServiceImpl implements TestService {

	@Override
    @MethodTrace
	public void test() {
		System.out.println("Test Service");
		
	}

	@Override
    @MethodTrace
	public void test2() {
		System.out.println("Test Service2");
		
	}

}
