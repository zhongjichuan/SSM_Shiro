package com.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SiteMeshController {

	@RequestMapping("/siteMesh/index")
	public String index(){
		
		return "siteMesh/index";
	}
	@RequestMapping("/siteMesh/index2")
	public String index2(){
		
		return "siteMesh/index2";
	}
	@RequestMapping("/siteMesh/index3")
	public String index3(){
		
		return "siteMesh/index3";
	}
}
