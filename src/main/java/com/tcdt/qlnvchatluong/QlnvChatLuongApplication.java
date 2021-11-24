package com.tcdt.qlnvchatluong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(basePackages = { "com.tcdt.qlnvchatluong.table" })
public class QlnvChatLuongApplication {
	public static void main(String[] args) {
		SpringApplication.run(QlnvChatLuongApplication.class, args);
	}

}
