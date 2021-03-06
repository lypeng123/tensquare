package com.tensquare.qa;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import util.IdWorker;
import util.JwtUtils;

@SpringBootApplication
public class QaApplication {

	public static void main(String[] args) {
		SpringApplication.run(QaApplication.class, args);
	}

	@Bean
	public IdWorker idWorkker(){
		return new IdWorker(1, 1);
	}

	/**
	 * 把JwtUtils加入到ioc容器中
	 * @return
	 */
	@Bean
	public JwtUtils jwtUtils(){
		return new JwtUtils();
	}
	
}
