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
* 아이디 중복 체크, 비밀번호 암호화, 회원정보 탈퇴 등 회원가입 및 로그인 관련된 기타 기능 추가
* 물품 카테고리에 따른 검색 기능 개발
* 회원별 등급에 따른 혜택 기능 개발
* 판매자 후기 점수 및 판매 상품 후기 점수에 따라 추천
* 구매자/판매자 거래내역 구축
* 상품 등록, 삭제, 수정 기능 개발
* 구매한 물품과 유사 물품 추천 기능 개발

# 사용 기술 및 환경
* Spring Boot, Maven, Mybatis, Mysql, Redis, Java8

# 프로젝트 DB ERD

![marketSite_ERD](https://user-images.githubusercontent.com/80434153/117936307-ae173480-b33f-11eb-97a8-be5c387d5b4b.png)
