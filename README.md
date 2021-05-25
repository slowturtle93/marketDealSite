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

# 브렌치 관리 전략
Git Flow를 사용하여 branch를 관리<br>
모든 branch는 pull request 리뷰 완료 후 merge

![branch](https://user-images.githubusercontent.com/80434153/119424297-21597700-bd40-11eb-8305-30d44f03abdf.png)

* Master : 개발, 테스트 완료 후 검증이 완료된 코드가 있는 branch
* Develop : 완전히 개발이 끝난 부분에 대해서만 Merge를 진행합니다.
* Feature : 기능 개발을 진행할 때 사용합니다.
* Release : 배포를 준비할 때 사용합니다.
* Hot-Fix : 배포를 진행한 후 발생한 버그를 수정해야 할 때 사용합니다.

# 사용 기술 및 환경
* Spring Boot, Maven, Mybatis, Mysql, Redis, Java8

# 프로젝트 DB ERD

![marketSite_ERD](https://user-images.githubusercontent.com/80434153/117936307-ae173480-b33f-11eb-97a8-be5c387d5b4b.png)
