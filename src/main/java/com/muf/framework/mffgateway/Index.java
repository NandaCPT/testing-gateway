package com.muf.framework.mffgateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Index {
	
	@GetMapping("/home")
	public String home() {
		String response = "OK";
		return response;
		
	}

}
