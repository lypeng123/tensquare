package com.tensquare.user;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import util.IdWorker;
import util.JwtUtils;

@SpringBootApplication
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

	@Bean
	public IdWorker idWorkker(){
		return new IdWorker(1, 1);
	}

	/**
	 *	把BCryptPasswordEncoder对象存入IOC容器中
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	/**
	 * 把JWTUUtils对象放到IOC容器
	 */
	@Bean
	public JwtUtils jwtUtils(){
		return new JwtUtils();
	}

}
