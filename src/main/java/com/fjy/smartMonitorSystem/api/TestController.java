package com.fjy.smartMonitorSystem.api;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fjy.smartMonitorSystem.model.Entity;

import io.swagger.annotations.Api;

@Api(value = "测试控制器", tags = { "试一试 120.77.34.35:3838/shabi/ojbk 是不是真的objk" })
@Controller
public class TestController {
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<Entity<String>> test1() {
		return Entity.success("ojbk");
	}
	
	@RequestMapping(value = "ojbk", method = RequestMethod.GET)
	public ResponseEntity<Entity<String>> test2() {
		return Entity.success("完全ojbk");
	}
}
