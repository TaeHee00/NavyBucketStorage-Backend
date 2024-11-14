package com.kancth.navybucketstorage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NavyBucketStorageApplication {

	public static void main(String[] args) {
		SpringApplication.run(NavyBucketStorageApplication.class, args);
	}

}
