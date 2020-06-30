package com.hm.packer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan(basePackages = "com.hm.packer.model.mapper")
public class PackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PackerApplication.class, args);
	}

}
