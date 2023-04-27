## Spring Boot JWT Tutorial by 정은구
- [Spring Boot JWT Tutorial by inflearn](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-jwt)

--------------------------
### 색션1. JWT 소개, 프로젝트 생성

※ JWT ? 
- Json Web Token
- RFC 7519 웹 표준
- Json 객체를 사용해서 토큰 자체에 정보들을 저장하고 있는 web token

※ JWT 구조
- header
  - signature를 해싱하기 위한 알고리즘 정보
- payload
  - 서버-클라이언트가 주고받는 시스템에서 `실제로 사용하기 위한 데이터`
- signature
  - 서버가 해당 토큰이 유효한 토큰인지 검증 문자열

※ JWT 장점
- `중앙의 인증서버`와 `데이터` 스토어에 대한 `의존성이 없기` 때문에 `수평적 확장에 유리`
- Base64 URL Safe Encoding > URL, Cookie, Header 모두 사용 가능

※ JWT 단점
- `Payload의 정보가 많아지면` `트레픽의 크기`가 `커질` 수 있으므로 데이터 설계 고려가 필요
- `토큰`이 서버에 저장되지 않고, `클라이언트쪽에 저장`되므로 서버에서는 클라이언트의 토큰을 `조작할 수 없음`


### 색션2. Security 설정, Data 설정