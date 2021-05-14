package com.market.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/*
 * @SpringBootApplication
 * 
 * SpringBoot의 자동 설정, 스프링 Bean 읽기와 생성을 모두 자동으로 설정됩니다.
 * 특히나 @SpringBootApplication이 있는 위치부터 설정을 읽어가기 때문에 이 클래스는 항상 프로젝트의 최상단에 위치 해야만 합니다.
 * 
 * @EnableCaching
 * 
 * Spring에서 Caching을 사용하겠다고 선언합니다.
 * 
 * @EnableRedisHttpSession
 * 
 * springSessionRepositoryFilter Bean을 생성한다. 이 필터를 통해 Spring Session <=> Redis 연결을
 * 지원줄 수 있습니다. 이를 통해 Session에 저장하는 사용자의 로그인 정보가 Redis에 저장되고, 
 * 여러 WAS를 사용하더라도 Session 정보를 하나의 Redis에서 관리할 수 있습니다.
 */
@SpringBootApplication
@EnableCaching
@EnableRedisHttpSession
public class MarketServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketServerApplication.class, args);
	}

}
