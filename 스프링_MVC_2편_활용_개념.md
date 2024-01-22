## 개념

--------------------------
### 색션1

<details>
<summary>타임리프 - 기본기능</summary>

※ 타임리프 특징
- 서버 사이드 html 렌더링 (ssr)
- 네츄럴 템플릿
- 스프링 통합 지원

- 타임리프는 기본적으로 이스케이프를 제공한다.
  - escape
    - `<` --> `$lt;`
    - `>` --> `$gt;`
  - escape 미 사용
    - `th:text` --> `th:utext`
    - `[[]]` --> `[()]`

- 지역 변수 선언
  - `th:with`
  - 선언한 테그 안에서만 사용할 수 있다.

※ 기본 표현식
- 간단한 표현
  - 변수 표현 : ${}
  - 콘텐츠 안에서 직접 데이터 출력 [[${...}]]
  - 선택 변수 표현 : *{}
  - 메시지 표현 : #{}
  - 링크 url 표현 : @{}
  - 조각 표현 : ~{}
- 문자 연산
  - 문자 합치기 : +
  - 리터럴 대체 : |the name is ${name}|
- 비교와 동등
  - 비교 : >, <, >=, <= (gt, lt, ge, le)
  - 동등 : ==, != (eq, ne)
- 조건 연상
  - if-then : (if) ? (then)
  - if-then-else : (if) ? (then) | (else)
  - Default : (value) ?: (defaultValue)
- 특별한 토큰
  - No-Operation : _
    - 마치 타임리프가 실행되지 않는 것 처럼 동작한다.
    - html의 내용 그대로 활용할 수 있다.

※ 타임리프 유틸리티 객체들
- `#message` : 메시지, 국제화 처리
- `#uris` : url 이스케이프 지원
- `#dates` : `java.util.Date`서식 지원
- `#calendars` : `java.util.Calendars` 서식 지원
- `#temporals` : 자바 8 날짜 서식 지원
- `#numbers` : 숫자 서식 지원
- `#strings` : 문자 관련 편의 기능
- `#objects` : 객체 관련 기능 제공
- `#bools` : boolean 관련 기능 제공
- `#arrays` : 배열 관련 기능 제공
- `#list`, `#sets`, `#maps` : 컬렉션 관련 기능 제공
- `#ids` : 아이디 처리 관련 기능 제공

※ 타임리프 태그 속성(Attribute)
타임리프는 주로 html 태크에 `th:*` 속성을 지정하는 방식으로 동작.  
`th:*`로 속성을 적용하면 기존 속성을 대체한다. 기존 속성이 없으면 새로 만든다.

- `<input type="checkbox" name="active" checked="false" />`
  - checkbox 는 checked 속성만 있어도 체크가 되어버진다. false 임에도 불구하고
  - 하지만 checked 속성이 없으면 체크가 안된다.
  - 이러만 불편함 때문에 `th:checked` 로 사용한다.

※ 반복
`th:each`
- `<tr th:each="user : ${users}">`
- `list` 뿐만 아니라 배열, `java.util.Iterable`, `java.util.Enumeration`을 구현한 모든 객체를 반복해 사용할 수 있다.

`userStat`
- `<tr th:each="user userStat : ${users}">`
  - 반복의 두번째 파라미터를 설정해서 반복의 상태를 확인 할 수 있다.
  - 생략하면 지정한 변수(`user`) + `Stat`가 된다.
- 반복 상태 유지 기능
  - `index` : 0 부터 시작하는 값
  - `count` : 1 부터 시작하는 값
  - `event`, `odd` : 홀수, 짝수 여부 (`boolean`)
  - `first`, `last` : 처음. 마지막 여부 (`boolean`)
  - `current` : 현재 객체

※ 조건부
`if`, `unless`(if의 반대)
`switch` : `*`은 만족하는 조건이 없을 때 사용하는 default

※ 자바스크립트 인라인
`<script th:inline="javasript>`
자바스크립트에서 타임리프를 편리하게 사용할 수 있는 자바스크립트 인라인 기능 제공

- 텍스트 렌더링
  - `var username = [[${user.username}]];`
    - 인라인 사용 전 : `var username = userA;`
    - 인라인 사용 후 : `var username = "userA"`

- 자바 스크립트 내추럴 템플릿
  - `var username2 = /*[[${user.username}]]*/ "test username"`
    - 인라인 사용 전 : `var user = BasicController.User(username=userA, age=10);`
      - toString() 호출된 값
    - 인라인 사용 후 : `var user = {"username":"userA", "age":10};`
      - 객체를 json 형태로 변환

※ 템플릿 조각
`template/fragment/footer :: copy` : `template/fragment/footer.html`템플릿에 있는 `th:fragment="copy"`라는 부분을 템플릿 조각으로 가져와서 사용한다는 의미

- 부분 포함 insert
  - `<div th:insert="~{template/fragment/gooter :: copy}"></div>`
  - `th:insert`를 사용하면 `div` 내부에 추가

- 부분 포함 replace
  - `<div th:replace="~{template/fragment/gooter :: copy}"></div>`
  - `th:insert`를 사용하면 `div` 를 대체한다.

- 부분 포한 단순 표현식
  - `<div th:replace="template/fragment/gooter :: copy"></div>`
  - `~{}`를 사용하는 것이 원칙이지만, 템플릿 조각을 사용하는 코드가 단ㅇ순하면 이 부분을 생략할 수 있다.

※ 템플릿 레이아웃
`<head>`에 공통으로 사용하는 `css`,`javascript`같은 공통 정보들을 한 곳에 모아두고, 공통으로 사용하지만  
각 페이지마다 필요한 정보를 더 추가해서 사용하고 싶을 때 사용
- `common_header(~{::title},~{::link})` 
  - ::title : 현재 페이지의 title 태그들을 전달한다.
  - ::link : 현재 페이지의 link 태그들을 전달한다.

※ 템플릿 레이아웃2
`html`에 `th:fragment`속성이 정의되어 있다.  
이 레이아웃 파일을 기본으로 하고 여기에 필요한 내용을 전달해서 부분부분 변경하는 것으로 이해하면 된다.

</details>

### 색션2

<details>
<summary>타임리프 - 스프링 통합과 폼</summary>

※ 타임리프 스프링 통합

**스프링 통합으로 추가 되는 기능들**
- SpringEL 문법 통합
- `${@myBean.doSometing()}`처럼 스프링 빈 호출 지원
- 편리한 폼 관리를 위한 추가 속성
  - `th:object` (기능 강화, 폼 커맨드 객체 선택)
  - `th:field`, `th:errors`, `th:errorclass`
- 폼 컴포넌트 기능
  - checkbox, radio button, List 등을 편리하게 사용할 수 있는 기능 지원
- 스프링 메시지, 국제화 기능의 편리한 통합
- 스프링의 검증, 오류 처리 통합
- 스프링의 변환 서비스 통합(ConversionService)

※ 입력 폼 처리

- `th:object` : 커맨드 객체를 지정한다.
- `*{}` : 선택 변수 식이라고 한다. `th:object`에서 선택한 객체에 접근한다.
- `th:field`
  - HTML 태그의 `id`, `name`, `value` 속성을 자동으로 처리해 준다.

**렌더링 전**
`<input type="text: th:field="*{itemName}" />`

**렌더링 후**
`<input type="text: id="itemName" th:value="*{itemName}" />`

※ 체크 박스 - 멀티
**@ModelAttribute**의 특별한 사용법  
등록 폼, 상세화면, 수정 폼에서 모두 서울, 부산, 제주라는 체크 박스를 반복해서 보여주어야 한다.  
이렇게 하려면 각각의 컨트롤러에서 model.addAttribute() 을 사용해서 페크 박스를 구성하는 데이터를 반복해서 넣어주어야 한다.
`@ModelAttribute`는 이렇게 컨트롤러에 있는 별도의 메소드에 적용할 수 있다.  
이렇게 하면 해당 컨트롤러를 요청할 때 `regions`에서 반환한 값이 자동으로 모델(model)에 담긴다.  
근데 호출 시 마다 호출됨... 성능 문제.. 바뀌지 않으면 static으로 빼자..

※ 라디오 버튼

타임리프에서 enum 직접 접근 ( 비 추천 )
```html
<div th:each="type : ${T(hello.itemservice.domain.item.ItemType).values()}"
```
`${T(hello.itemservice.domain.item.ItemType).values()}` 스프링EL 문법으로 enum을 직접 사용할 수 있다.  
`values()`를 호출하면 해당 enum의 모든 정보가 배열로 반환한다.

</details>

### 색션3

<details>
<summary>메시지, 국제화</summary>

※ 메시지, 국제화 소개

**메시지**
한 설정 파일로 메시지들을 관리 할 수 있다.
- messages.properties
  - `item.itemName = 상품명`
- addForm.html
  - `<label for="itemName" th:text="#{item.itemName}">상품명</label>`
- 파라미터 사용법
  - `hello.name = 안녕 {0}`
  - `<p th:text="#{hello.name(${item.itemName})}"></p>`

**국제화**
설정 파일들을 나라별로 관리한다.
- messages_ko.properties, messages_en.properties
- 일반 적으로 HTTP `accept-language` 헤더 값 사용
- 스프링은 기본적인 메시지와 국제화 기능을 모두 제공, 타임리프도 스프링이 제공하는 메시지와 국제화 기능을 편리하게 통합해서 제공

**LocaleResolver**
스프링은 `Locale`선택 방식을 변경할 수 있도록 `LocaleResolver`라는 인터페이스를 제공  
스프링 부트는 기본으로 `accept-language`를 활용하는 `AcceptHeaderLocalResolver`를 사용

**LocaleResolver 변경**
만약 `Locale` 선택 방식을 변경하면 `LocaleResolver`의 구현체를 변경해서 쿠키나 세션 기반의 `Locale` 선택 기능을 사용할 수 있다.

</details>


### 색션4

<details>
<summary>검증1 - Validation</summary>

※

</details>