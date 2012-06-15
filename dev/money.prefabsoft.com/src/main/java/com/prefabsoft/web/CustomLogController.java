package com.prefabsoft.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.prefabsoft.web.util.LogUtilities;

@Controller
@RequestMapping("/log")
public class CustomLogController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> post(@RequestParam("level") String level, 
			@RequestParam("message") String message, 		
			@RequestParam("logger") String logger, 		
			@RequestParam("timestamp") String timestamp, 		
			@RequestParam("url") String url, 		
			@RequestParam("layout") String layout){
		
		log.debug("level: {} message: {}", level, message);
		LogUtilities.logToDb(level, "@"+timestamp+" "+message);
		
		return new ResponseEntity<String>(HttpStatus.ACCEPTED);
	}
}