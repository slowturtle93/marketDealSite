package com.market.server.aop;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.market.server.utils.SessionUtil;

import lombok.extern.log4j.Log4j2;


/**
 * @Component
 * 자동으로 탐지하기 위해서는 별도의 @Component 어노테이션을 추가해야 한다.
 * (아니면 대신 스프링의 컴포넌트 스캐너의 규칙마다 제한을 하는 커스텀 스테레오타입 어노테이션을 추가해야 한다.)
 * 
 * @Aspect
 * 해당 빈이 Aspect로 작동한다.
 * @Aspect가 명시된 빈에는 어드바이스(Advice)라 불리는 메써드를 작성할 수 있다.
 * 대상 스프링 빈의 메써드의 호출에 끼어드는 시점과 방법에 따라
 * @Before, @After, @AfterReturning, @AfterThrowing, @Around
 * 
 * @Log4j2
 * Login Check할때 aop의 Aspect 애노테이션을 이용하여
 * 로그인 체크 중복되는 코드를 제거하기 위해 어드바이스(Advice)를 정의하는 class 입니다.
 * 
 * @author haksong
 *
 */
@Component
@Aspect
@Log4j2
// 어노테이션으로 로그인 여부를 검사하기 위한 클래스
public class LoginCheckAspect {
	
	@Before("@annotation(com.market.server.aop.LoginCheck) && @ annotation(loginCheck)")
	public void adminLoginCheck(LoginCheck loginCheck) throws Throwable{
		HttpSession session = ((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getRequest().getSession();
		String id = null;
		
		String userType = loginCheck.type().toString();
		switch (userType) {
			case "ADMIN":{
				id = SessionUtil.getLoginAdminId(session);
				break;
			}
			case "USER":{
				id = SessionUtil.getLoginUserId(session);
				break;	
			}
		}
		
		if(id == null) {
			throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED, "로그인한 id값을 확인해주세요.") {};
		}
	}

}
