package com.prefabsoft.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HttpInfoController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "/test")
	public ResponseEntity<String> in(HttpServletRequest request, HttpEntity<String> requestEntity){
		
		String prettyRequestDataAsJsonString = LogUtilities.logRequestDataAsJsonString(request, requestEntity);
		
		/*ResponseEntity<String> responseEntity;
		if (request.getHeader("accept").contains("application/json")) {
			responseEntity = 
		}*/
		
		return new ResponseEntity<String>(prettyRequestDataAsJsonString,HttpStatus.ACCEPTED);
	}

}
