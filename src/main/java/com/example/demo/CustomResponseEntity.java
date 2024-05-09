package com.example.demo;

import java.io.Serializable;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

public class CustomResponseEntity<T> extends ResponseEntity<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomResponseEntity(HttpStatusCode status) {
		super(status);
	}

	public CustomResponseEntity(MultiValueMap<String, String> headers, HttpStatusCode status) {
		super(headers, status);
	}

	public CustomResponseEntity(T body, HttpStatusCode status) {
		super(body, status);
	}

	public CustomResponseEntity(T body, MultiValueMap<String, String> headers, HttpStatusCode statusCode) {
		super(body, headers, statusCode);
	}

	public CustomResponseEntity(T body, MultiValueMap<String, String> headers, int rawStatus) {
		super(body, headers, rawStatus);
	}

}