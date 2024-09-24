package com.hostmdy.ecommerce.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface MapValidationErrorService {
	ResponseEntity<Map<String, String>> validate(BindingResult result);
}
