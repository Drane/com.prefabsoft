package com.prefabsoft.web;

import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.prefabsoft.log.Log;

@RooWebScaffold(path = "logs", formBackingObject = Log.class)
@RequestMapping("/logs")
@Controller
public class LogController {
}
