package com.tcdt.qlnvchatluong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(basePackages = { "com.tcdt.qlnvchatluong.table" })
@EnableFeignClients(basePackages = "com.tcdt.qlnvchatluong.service.feign")
public class QlnvChatLuongApplication {
	public static void main(String[] args) {
		SpringApplication.run(QlnvChatLuongApplication.class, args);
	}

}
