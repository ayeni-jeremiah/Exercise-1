package com.ayeni.exercise;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExerciseController {

	@GetMapping("/")
	String sayHello() {
		return "Hello";
	}
	
//	@GetMapping("/")
//	public Map<String, String> sayHello2() {
//	    HashMap<String, String> map = new HashMap<>();
//	    map.put("key", "value");
//	    map.put("foo", "bar");
//	    map.put("aa", "bb");
//	    return map;
//	}
}
