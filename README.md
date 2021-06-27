# market-site-server

# 목적
* 신상 상품 및 중고 상품 거래 서비스 기능들을 구현함으로써 Backend System의 도메인 이해
* 대규모 트래픽의 이해 및 대규모 트래픽에도 견고한 어플리케이션의 구현
* ex) 당근마켓의 중고거래 사이트 + 아이디어스 상품 거래 사이트 구현

# 기획
* 판매 및 중고 상품 거래 사이트 이용자에게 각종 물품들의 관련 정보를 업로드 하고<br>
  중개 플랫폼을 활용함으로써 원하는 물품을 거래할 수 있게 구현
* 구매자 / 판매자의 구분 없이 자유롭게 물품을 등록 및 구매 가능하게 구현
* 화면설계 : [ProtoType](https://ovenapp.io/view/dfupMDaIEzl6UIfPpVNzzi9Ub6eAdjST/J34oK)

# 프로젝트 주요 기능
* 회원가입 및 로그인
* 아이디 중복 체크, 비밀번호 암호화 등 회원가입 및 로그인 관련 부가 기능 개발
* 판매 상품 및 중고 상품 구분에 따른 상품 판매
* 중고물품 구매 희망 시 판매자와 연락 기능 개발
* 상품 구매 이력에 조회 기능 개발

# 기술적인 집중 요소
* 객체지향의 기본 원리와 spring의 IOC/DI, AOP, ASP 활용과 의미 있는 코드 작성
* 라이브러리 및 기능 추가 시 이유있는 선택과 사용 목적 고려

# 브렌치 관리 전략
Git Flow를 사용하여 branch를 관리<br>
모든 branch는 pull request 리뷰 완료 후 merge

![branch](https://user-images.githubusercontent.com/80434153/119424297-21597700-bd40-11eb-8305-30d44f03abdf.png)

* Master : 개발, 테스트 완료 후 검증이 완료된 코드가 있는 branch
* Develop : 완전히 개발이 끝난 부분에 대해서만 Merge를 진행합니다.
* Feature : 기능 개발을 진행할 때 사용합니다.
* Release : 배포를 준비할 때 사용합니다.
* Hot-Fix : 배포를 진행한 후 발생한 버그를 수정해야 할 때 사용합니다.

브랜치 관리 전략 참고 문헌
* http://woowabros.github.io/experience/2017/10/30/baemin-mobile-git-branch-strategy.html

# 사용 기술 및 환경
* Spring Boot, Maven, Mybatis, Mysql, Redis, Java8

# 프로젝트 DB ERD

![marketSite_ERD](https://user-images.githubusercontent.com/80434153/122196386-28ece580-ced2-11eb-80bc-b3fb9eaf794c.png)
