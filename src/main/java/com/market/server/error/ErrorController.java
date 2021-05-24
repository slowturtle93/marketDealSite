package com.market.server.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.market.server.error.exception.DuplicateIdException;

@RestControllerAdvice
public class ErrorController {
	
	/**
     * 패키지 명을 제외한 클래스 이름을 반환한다.
     * 
     * @param e 에러
     * @return
     */
    private static String getSimpleName(Exception e) {
      return e.getClass().getSimpleName();
    }

    @ResponseStatus(HttpStatus.CONFLICT) // 반환할 상태코드 설정한다.
    @ExceptionHandler(DuplicateIdException.class) // 처리할 에러를 설정한다.
    public ErrorMsg handleDuplicateIdException(DuplicateIdException e) {
      // Exception 객체의 현지화 메시지와 클래스 이름을 반환한다.
      return new ErrorMsg(e.getLocalizedMessage(), getSimpleName(e));
    }
}
