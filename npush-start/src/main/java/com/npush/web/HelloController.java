
package com.npush.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class HelloController {
	 @RequestMapping("/hello")
	 @ResponseBody
	 public String index() {
	     return "Hello World";
	 }
	 

	 @RequestMapping("/hello2")
	 public String hello2(Map<String, Object> model) {
		 model.put("name", "习大大");
	     return "hello";
	 }
}