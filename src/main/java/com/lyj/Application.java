package com.lyj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@MapperScan("com/lyj/dao") //扫描mybatis注解
@ImportResource(locations={"classpath:application-bean.xml"})	//可以引入xml配置文件中的信息
@SpringBootApplication()
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
