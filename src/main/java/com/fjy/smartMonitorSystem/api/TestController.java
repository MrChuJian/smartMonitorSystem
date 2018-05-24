package com.fjy.smartMonitorSystem.api;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fjy.smartMonitorSystem.model.Entity;
import com.fjy.smartMonitorSystem.model.SB;
import com.fjy.smartMonitorSystem.util.SocketUtil;

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
	
	@RequestMapping(value = "cmd/{cmd}", method = RequestMethod.GET)
	public ResponseEntity<Entity<String>> ctl(@PathVariable Integer cmd) {
		boolean isSend = false;
    	isSend = SocketUtil.send(cmd);
    	if(isSend) {
    		return Entity.success("发送成功");
    	}
    	return Entity.failure(66, "发送失败,看看硬件连接上没");
	}
}
