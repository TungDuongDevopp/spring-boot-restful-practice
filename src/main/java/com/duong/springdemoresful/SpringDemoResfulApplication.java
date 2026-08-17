package com.duong.springdemoresful;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class SpringDemoResfulApplication {

 static void main(String[] args) {
		SpringApplication.run(SpringDemoResfulApplication.class, args);
	}

}
