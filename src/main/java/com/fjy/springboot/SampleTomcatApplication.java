package com.fjy.springboot;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@ComponentScan(value = { "com.fjy" })
public class SampleTomcatApplication  {
	private static Log logger = LogFactory.getLog(SampleTomcatApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(SampleTomcatApplication.class, args);
		System.out.println("系统启动成功");
	}

}
