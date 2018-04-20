package com.fjy.springboot;



import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.fjy.smartMonitorSystem.api.FileController;


@SpringBootApplication
@ComponentScan(value = { "com.fjy" })
public class SampleTomcatApplication  {
	public static ConfigurableApplicationContext applicationContext = null;
	private static Log logger = LogFactory.getLog(SampleTomcatApplication.class);
	public static void main(String[] args) {
		applicationContext = SpringApplication.run(SampleTomcatApplication.class, args);
		FileController fileController = applicationContext.getBean(FileController.class);
    	Timer timer;
    	timer = new Timer(true);
    	timer.schedule(new TimerTask() {
    		public void run(){ 
    			fileController.deleteDir();
    		} 
    	}, 0, 60*60*1000);
		System.out.println("系统启动成功");
	}
}
