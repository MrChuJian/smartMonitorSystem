package com.fjy.springboot;



import java.util.Timer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.fjy.smartMonitorSystem.Thread.NettyServerThread;
import com.fjy.smartMonitorSystem.Timer.DeleteDirTimer;
import com.fjy.smartMonitorSystem.Timer.VideoTimer;


@SpringBootApplication
@ComponentScan(value = { "com.fjy" })
public class SampleTomcatApplication  {
	public static ConfigurableApplicationContext applicationContext = null;
	private static Log logger = LogFactory.getLog(SampleTomcatApplication.class);
	public static void main(String[] args) {
		applicationContext = SpringApplication.run(SampleTomcatApplication.class, args);
    	Timer timer;
    	timer = new Timer(true);
    	timer.schedule(new DeleteDirTimer(), 0, 24L*60*60*1000);
    	timer.schedule(new VideoTimer(), 0, 1000);
    	Thread thread = new NettyServerThread();
		thread.start();
    	
		System.out.println("系统启动成功");
	}
}
