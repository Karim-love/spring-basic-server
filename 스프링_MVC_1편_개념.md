## 개념

--------------------------
### 색션2. 웹 애플리케이션 이해

※ 동시 요청 - 멀티 쓰레드
- 멀티 쓰레드에 대한 부분은 WAS가 처리
- 개발자가 멀티 쓰레드 관련 코드를 신경쓰지 않아도 됨
- 개발자는 마치 싱글 쓰레드 프로그래밍을 하듯이 편리하게 소스 코드를 개발
- 멀티 쓰레드 환경이므로 싱글톤 객체(서블릿, 스프링 빈)는 주의해서 사용

※ 
- SSR - 서버 사이드 렌더링 
  + 서버에서 최종 HTML을 생성해서 클라이언트에 전달
- CSR - 클라이언트 사이드 렌더링
  + HTML 결과를 자바스크립트를 사용해 웹 브라우저에서 동적으로 생성해서 적용
  + 주로 동적인 화면에 사용, 웹 환경을 마치 앱 처럼 필요한 부분부분 변경할 수 있음
    + ex ) 구글 지도, gmail, 캘린더 

※ 자바 백엔드 웹 기술 역사
- WebFlux
  - 특징
    - 비동기 넌 블러킹 처리
    - 최소 쓰레드로 최대 성능 - 쓰레드 컨텍스트 스위칭 비용 효율화
    - 함수형 스타일로 개발 - 동시 처리 코드 효율화
    - 서블릿 기술 사용 X

--------------------------
### 색션3. 서블릿

※ HttpServletRequest 개요
- 역할
  - HTTP 요청 메시지를 파싱
- 요청 메시지
  - Start Line
    - http 메소드
    - url
    - 쿼리 스트링
    - 스키마, 프로토콜
  - 헤더
    - 헤더 조회
  - 바디
    - form 파라미터 형식 조회
    - message body 데이터 직접 조회
  - **임시 저장소 기능**
    - 해당 HTTP 요청이 시작부터 끝날 때 까지 유지되는 임시 저장소 기능
    - 저장 : `request.setAttribute(name, value)`
    - 조회 : `request.setAttribute(name)`
  - **세션 관리 기능**
    - `request.getSession(create: true)`

※ HTTP 요청 데이터 - 개요
- **GET** - 쿼리 파라미터
  - /url?username=lim?age=29
  - 메시지 바디 없이, URL의 쿼리 파라미터에 데이터를 포함해서 전달
  - ex) 검색, 필터, 페이징등에서 많이 사용하는 방식
- **POST** - HTML Form
  - content-type : application/x-www-form-urlencoded
  - 메시지 바디에 쿼리 파라미터 형식으로 전달 : username=lim?age=29
  - ex) 회원 가입, 상품 주문, HTML Form 사용
- **HTTP message body**에 데이터를 직접 담아서 요청
  - HTTP API에서 주로 사용, JSON, XML, TEXT
  - 데이터 형식은 주로 JSON
  - POST, PUT, PATCH

--------------------------
### 색션3. 서블릿, JSP, MVC 패턴

※ Model View Controller
하나의 서블릿이나, jsp로 처리하던 것을 컨트롤터와 뷰라는 영여으로 서로 나눈 것
- **Controller**
  -  HTTP 요청을 받아서 파라미터를 검증하고, 비즈니스 로직을 실행한다. 그리고 뷰에 전달할 결과 데이터를 조회해서 모델에 담는다.
- **Modell**
  - 뷰에 출력할 데이터를 담아둔다.
  - 뷰가 필요한 데이터르 모두 모델에 담아서 전달해주는 덕분에 뷰는 비즈니스 로직이나 데이터 접근을 몰라도되고, 화면을 렌더링 하는 일에 집중할 수 있다.
- **View**
  - 모델에 담겨있는 데이터를 사용해서 화면을 그리는 일에 집중한다. 여기서는 html을 생성하는 부분을 말한다.

--------------------------
### 색션4. MVC 프레임워크 만들기

※ 프론트 컨트롤러 패턴 소개
- 프론트 컨트롤러 서블릿 하나로 클라이언트의 요청을 받음
- 프론트 컨트롤러가 요청에 맞는 컨트로러를 찾아서 호출
- 압구를 하나로!
- 공통 처리 기능
- 프론트 컨트롤러를 제외한 나머지 컨트롤러는 서블릿을 사용하지 않아도 됨

※ 스프링 웹 MVC와 프론트 컨트롤러
- 핵심은 `FrontController`
- `DispatcherServlet`이 FrontController 패턴으로 구현되어 있음.

※ V3  서블릿 종속성 제거
- 컨트롤러 입장에서는 HttpServletRequest, HttpServletResponse 가 꼭 필요하지 않다.
  - 요청 파라 미터 정보는 자바의 Map으로 대신 넘기도록 하면 컨트롤러가 서블릿 기술을 몰라도 동작할 수 있다.
  - request 객체를 Model로 사용하는 대신에 별도의 Model 객체를 만들어서 반환하면 된다.
- 뷰 이름 중복 제거
  - V2 컨트롤러에 지정하는 뷰 이름에 중복이 있는 것을 확인
  - 프론트 컨트롤러에서 처리하도록 단순화

※ V4 단순하고 실용적인 컨트롤러

※ V5 유연한 컨트롤러
- 어댑터 패턴
  - 다양한 컨트롤러 인터페이스를 무리 없이 사용하기 위한 패턴
- 핸들러 어댑터
  - frontController 와 핸들러(컨트롤러) 사이 중간 역할
  - 해당 어댑터로인해 다양한 종류의 컨트롤러 호출 가능
- 핸들러
  - 컨트롤러의 이름을 더 넓은 범위인 핸들러로 변경
  - 이유는 어댑터가 있기 때문에 컨트롤러의 개념 뿐만 아니라 어떠한 것이든 해당하는 종류의 어댑터만 있으면 다 처리할 수 있기 때문

--------------------------
### 색션5. 스프링 MVC 구조 이해
※ DispatcherServlet 구조 살펴보기
`org.springframwork,web.servlet.DispatcherServlet`
- 스프링 MVC도 프론트 컨트롤러 패던으로 구현되어 있다.
- 스프링의 핵심

**DispatcherServlet 서블릿 등록**
- `DispatcherServlet`도 부모 클래스에서 `HttpServlet`을 상속 받아서 사용하고, 서블릿으로 동작한다.
  - DispatcherServlet -> FrameworkServlet -> HttpServletBean -> HttpServlet
- 스프링 부트는 `DispatcherServlet`을 서블릿으로 자동으로 등록하면서 *모든 경로(urlPatterns="/")* 에 대해서 매핑한다.
  - 더 자세한 경로가 우선순위가 높다. 그래서 기존에 등록한 서블릿도 함께 동작한다.

**요청 흐름**
- 서블릿이 호출 되면 `HttpServlet`이 제공하는 `service()`가 호출된다.
- 스프링 MVC는 `DispatcherServlet`의 부모인 `FramworkServlet`에서 `service()`를 오버라이드 해두었다.
- `FramworkServlet.service()`를 시작으로 여러 메서드가 호출되면서 `DispatcherServlet.doDispatch()`가 호출 된다.
- `DispatcherServlet`의 핵심인 `doDispatch()` 

**SpringMVC 구조 및 동작 순서**
1. **핸들러 조회** : 핸들러 매핑을 통해 요청 URL에 매핑된 핸들러(컨트롤러)를 조회한다.
2. **핸들러 어댑터 조회** : 핸들러를 실행할 수 있는 핸들러 어댑터를 조회한다.
3. **핸들러 어댑터 실행** : 핸들러 어댑터를 실행한다.
4. **핸들러 실행** : 핸들러 어댑터가 실제 핸들러를 실행한다.
5. **ModelAndView 반환** : 핸들러 어댑터는 핸들러가 반환하는 정보를 ModelAndView로 **변환**해서 반환한다.
6. **viewResolver 호출** : 뷰 리졸버를 찾고 실행한다.
7. **View 반환** : 뷰 리졸버는 뷰의 논리 이름을 물리 이름으로 바꾸고, 렌더링 역할을 담당하는 뷰 객체를 반환한다.
8. **뷰 렌더링** : 뷰를 통해서 뷰를 렌더링 한다.

**인터페이스 설펴보기**
- `DispatcherServlet` 코드의 변경 없이, 원하는 기능을 변경하거나 확장할 수 있다.
- 원하는 인터페이스들만 구현해서 `DispatcherServlet`에 등록하면 나만의 컨트롤러를 만들 수 있다.

**주요 인터페이스 목록**
- 핸들러 매핑 : `org.springframwork,web.servlet.HanlerMapping`
- 핸들러 어댑터 : `org.springframwork,web.servlet.HandlerAdapter`
- 뷰 리졸버 : `org.springframwork,web.servlet.ViewResolver`
- 뷰 : `org.springframwork,web.servlet.View`

**HandlerMapping**
- 핸들러 매핑에서 이 컨트롤러를 찾을 수 있어야 한다.
- ex) 스프링 빈의 이름으로 핸들러를 찾을 수 있는 핸들러 매핑이 필요하다.
**HandlerAdapter**
- 핸들러 매핑을 통해 찾은 핸들러를 실행할 수 있는 핸들러 어댑터가 필요하다.
- ex) `Controller` 인터페이스를 실행할 수 있는 핸들러 어댑터를 찾고 실행해야 한다.

**스프링 부트가 자동 등록하는 핸들러 매핑과 핸들러 어댑터**

**HandlerMapping**
```
0 = RequestMappingHandlerMapping : 어노테이션 기반의 컨트롤러인 @RequestMapping에서 사용
1 = BeanNameUrlHandlerMapping    : 스프링 빈의 이름으로 핸들러는 찾음
```

**HandlerAdapter**
```
0 = RequestMappingHandlerAdapter   : 어노테이션 기반의 컨트롤러인 @RequestMapping에서 사용
1 = HttpRequestHandlerAdapter      : HttpRequestHandler 처리
2 = SimpleControllerHandlerAdapter : Controller 인터페이스 ( 어노테이션X, 과거에 사용 ) 처리
```
핸들러 매핑도, 핸들러 어뎁터도 모두 순서대로 찾고 만약 없으면 다음 순서로 넘어간다.

**@RequestMapping**
`RequestMappingHandlerMapping`, `RequestMappingHandlerAdapter` 최고 우선 순의 매핑 및 어댑터

**ViewResolver**
```
1 = BeanNameViewResolver         : 빈 이름으로 뷰를 찾아서 반환한다.
2 = InternalResourceViewResolver : JSP를 처리할 수 있는 뷰를 반환한다.
```

**RequestMapping**
- `RequestMappingHandlerMapping`
  - 스프링 빈 중에서 `@RequestMapping` 또는 `@Controller`가 클래스 레벨에 붙어 있는 경우 매핑 정보로 인식한다.
- `RequestMappingHandlerAdapter`

**Model**
- `save()`, `members()`를 보면 Model을 파라미터로 받는것을 확인할 수 있다.

**ViewName 직접 반환**
- 뷰의 논리 이름을 반환할 수 있다.

**@RequestParam 사용**
- 스프링은 HTTP 요청 파라미터를 `@RequestParam`으로 받을 수 있다.
- `@RequestParam("username")`은 `reqeust.getParameter("username")`와 거의 같은 코드라 생각하면 된다.

--------------------------
### 색션6. 스프링 MVC-기본 기능

※ 매핑 정보
`@RestController`
- `@Controller`는 반환 값이 `String`이면 뷰 이름으로 인식된다. 그래서 **뷰를 찾고 뷰가 랜더링**된다.
- `@RestController`는 반환 값으로 뷰를 찾는 것이 아니라, **Http 메세지 바디에 바로 입력**한다.

※ 로그 사용 시 장점
- 스레드 정보, 클래스 이름 같은 부가 정보를 함께 볼 수 있고, 출력 모양을 조정할 수 있다.
- 로그 레벨에 따라 개발 서버에서는 모든 로그를 출력하고, 운영서버에서는 출력하지 않는 등 로그를 상황에 맞게 조절할 수 있다.
- 시스템 아웃 콘솔에만 출력하는 것이 아니라, 파일이나 네트워크 등, 로그를 별도의 위치에 남길 수 있다. 특히 파일로 남길 때는 일별, 특정 용량에 따라 로그를 분할하는 것도 가능하다.
- 성능도 일반 sout 보다 좋다.

※ HTTP 요청 데이터 조회
**GET - 쿼리 파라미터**
- /url **?username=hello&age=20**
- 메세지 바디 없이, URL의 쿼리 파라미터에 데이터를 포함해서 전달
- ex ) 검색, 필터, 페이징등에서 많이 사용하는 방식
- `HttpServletRequest`의 `request.getParameter()`를 사용

**POST - HTML Form**
- content-type:application/x-www-form-urlencoded
- 메세지 바디에 쿼리 파라미터 형식으로 전달 username=hello&age=20
- ex ) 회원 가입, 상품 주문, HTML Form 사용
- `HttpServletRequest`의 `request.getParameter()`를 사용
  
**HTTP message body**에 데이터를 직접 담아서 요청
- HTTP API에서 주로 사용, JSON, XML, TEXT
- 데이터 형식은 주로 JSON 사용
- POST, PUT, PATCH

※ 바인딩 오류
- `age=abc` 처럼 숫자가 들어가야할 곳에 문자를 넣으면 `BindException`이 발생한다.
-  이런 오류는 검증 부분에서 세세히 다뤄야 한다.

※ 생략 가능
- `@ModelAttribute` 및 `@RequestParam` 생략 가능
- 스프링은 해당 생략시 다음과 같은 규칙을 적용한다.
  - `String`, `int`, `Integer` 같은 단순 타입 = `@RequestParam`
  - 나머지 = `@ModelAttribute` ( argument resolver 로 지정해둔 타입 외 )

※ HTTP message body 에 데이터를 직접 담아서 요청
- HTTP API 에서 주로 사용, JSON, XML, TEXT
- 데이터 형식은 주로 JSON
- POST, PUT, PATCH
- 요청 파라미터와 다르게, HTTP 메시지 바디를 통해 데이터가 직접 데이터가 넘어오는 경우는 `@RequestParam`, `@ModelAttribute`를 사용할 수 없다.
- HTTP 메세지 바디의 데이터를 `InputStream`을 사용해서 직접 읽을 수 있다.

※ 스프링 MVC는 다음 파라미터를 지원한다.
- **HttpEntity** : Http header, body 정보를 편리하게 조회
  - 메세지 바디 정보를 직접 조회
  - 요청 파라미터를 조회하는 기능과 관계 없음 `@RequestParam`, `@ModelAttribute` => X
- **HttpEntity** 응답에도 사용 가능
  - 메세지 바디 정보 직접 반환
  - 헤더 정보 포함 가능
  - view 조회 X
- **HttpEntity** 를 상속받은 다음 객체들도 같은 기능을 제공한다.
  - **RequestEntity**
    - HttpMethod, url 정보가 추가, 요청에서 사용
  - **ResponseEntity**
    - HTTP 상태 코드 설정 가능, 응답에서 사용
    - `return new ResponseEntity<String>("Hello World", responseHeaders, HttpStatus.CREATED)`
- **RequestBody**
  - `@RequestBody`를 사용하면 HTTP 메세지 바디 정보를 편리하게 조회할 수 있다. 참고로 헤더 정보가 필요하다면 `HttpEntity`를 사용하거나 `@RequestHeader`를 사용하면 된다.
- **ResponseBody**
  - `@ResponseBody`를 사용하면 응답 결과를 HTTP 메시지 바디에 직접 담아서 전달할 수 있다.
- **요청 파라미터 vs HTTP 메세지 바디**
  - 요청 파라미터를 조회하는 기능 : `@ModelAttribute`, `@RequestParam`
  - HTTP 메시지 바디를 직접 조회하는 기능 `@RequestBody`

※ HTTP 요청 메시지 - JSON
- requestBodyV3
  - HttpMessageConvert 사용 -> MappingJackson2HttpMessageConverter
    - content-type : application/json
  - `@RequestBody` 생략 불가능
    - 생략해 버리면 `@ModelAttribute`가 된다.
- requestBodyV5
  - `@RequestBody` 요청
    - Json 요청 -> Http 메세지 컨버터 -> 객체
  - `@ResponseBody` 응답
    - 객체 -> Http 메시지 컨버터 -> Json 응답
    - HttpMessageConvert 사용 -> MappingJackson2HttpMessageConverter
      - Accept : application/json

※ HTTP 응답 - 정적 리소스, 뷰 템플릿
- **정적 리소스**
  - 정적인 http, css, js을 제공할 때 사용
- **뷰 템플릿**
  - 웹 브라우저에 동적인 html을 제공할 때 사용
- **Http 메시지**
  - HTTP API를 제공하는 경우에는 HTML이 아니라 데이터를 전달해야 하므로, HTTP 메세지 바디에 JSON같은 형식으로 데이터를 실어서 응답

- **String을 반환하는 경우 -View or Http message**
  - `@ResponseBody` 가 없으면 `response/hello`로 뷰 리졸버가 실행되어 뷰를 찾고, 렌더링 한다.
  - `@ResponseBody` 가 있으면 뷰 리졸버를 실행하지 않고, Http 메시지 바디에 직접 `response/hello`라는 문자가 입력된다.

※ HTTP 메세지 컨버터
- http accept 헤더와 서버의 컨트롤러 반환 타입 정보 들을 조합해 converter 선택
- `canRead()`, `canWrite()` : 메시지 컨버터가 해당 클래스, 미디어 타입을 지원하는지 체크
- `read()`, `write()` : 메시지 컨버터를 통해서 메시지를 읽고 쓰는 기능

- 우선 순위
- 0 = **ByteArrayHttpMessageConverter**
  - `byte[]` 데이터를 처리
  - class type : `byte[]`, 미디어 타입 : `*/*`
  - request ex : `@RequestBody byte[] data`
  - response ex : `@ResponseBody return byte[]` 쓰기 미디어 타입: `application/octet-stream`

- 1 = **StringHttpMessageConverter**
  - `String` 문자로 데이터를 처리
  - class type : `String`, 미디어 타입 : `*/*`
  - request ex : `@RequestBody String data`
  - response ex : `@ResponseBody return "ok"` 쓰기 미디어 타입: `text/plain`
    - ex) String 타입이고 미디어 타입이 application/json 일 시 해당 converter 작동

- 2 = **MappingJackson2HttpMessageConverter**
  - class type : 객체 또는 `HashMap`, 미디어 타입 : `application/json` 관련
  - request ex : `@RequestBody HelloData data`
  - response ex : `@ResponseBody return helloData` 쓰기 미디어 타입: `application/json`

- 요청 데이터 읽기
  - http 요청이 오고, 컨트롤러에서 `@RequestBody`, `HttpEntuty`파라미터를 사용
  - 메시지 컨버터가 메시지를 읽을 수 있는지 확인하기 위해 `canRead()`를 호출
    - 대상 클래스 타입을 지원하는가
      - ex) `@RequestBody`의 대상 클래스(`byte[]`, `String`, 객체 )
    - http 요청의 Content-Type 미디어 타입을 지원하는가
      - ex) `text/plain`, `application/json`, `*/*`
    - `canRead()`조건을 만족하면 `read()`를 호출해서 객체 생성하고 반환

- 응답 데이터 생성
  - 컨트롤러에서 `@ResponseBody`, `HttpEntuty`로 값이 반환
  - 메시지 컨버터가 메시지를 쓸 수 있는지 확인하기 위해 `canWrite()`를 호출 
    - 대상 클래스 타입을 지원하는가
      - ex) return의 대상 클래스 (`byte[]`, `String`, 객체 )
    - http 요청의 Accept 미디어 타입을 지원하는가 (더 정확히는 `@RequestMapping`의 `produces`)
      - ex) `text/plain`, `application/json`, `*/*`
    - `canWrite()`조건을 만족하면 `write()`를 호출해서 http 응답 메시지 바디에 데이터를 생성

※ 요청 매핑 핸들러 어뎁터 구조
- `@ReqeustMapping`을 처리하는 핸들러 어댑터 `RequestMappingHandlerAdapter`

- **RequestMappingHandlerAdapter**
  - Dispatcher Servlet -> RequestMapping -> `argument resolver` -> handler -> return value handler(컨트롤러의 반환 값을 변환)

- **Argument resolver** - http 메시지 컨버터 사용
  - 파라미터의 형식을 유연하게 처리하게 해줌
  - 어노테이션 기반 컨트롤러를 처리하는 `RequestMappingHandlerAdaptor`는 바로 이 `Argument resolver`를 호출해서
    컨트롤러가 필요로 하는 다양한 파라미터의 값(객체)을 생성한다.
    그리고 이렇게 파라미터의 값이 모두 준비되면 컨트롤러를 호출하면서 값을 넘겨준다.

- **동작 방식**
  - `argument resolver`의 `supportsParamter()`를 호출해서 해당 파라미터를 지원하는지 체크
  - 지원하면 `esolveArgument()`를 호출해서 실제 객체를 생성
  - 생성된 객체가 턴트롤러 호출 시 넘어간다.

- **ReturnValueHandler** - http 메시지 컨버터 사용
  - `HandlerMethodReturnValueHandler`를 줄여서 `returnValdueHandler`로 부른다.
  - 응답 값을 변환하고 처리

--------------------------
### 색션7. 웹 페이지 만들기

※ PRF Post/Redirect/Get
- **문제 상황**
**새로고침은 내가 마지막에 했던 행위를 또 하는 것**
따라서 상품 저장하고 상품 상세 부분에서 새로고침을 하면 
```
양식 다시 제출 확인
```
메세지가 뜨고 계속으로 누르면 상품을 또 저장하게 된다.

※ RedirectAttribute
- **문제 상황**
`redirect:/basic/items/"+item.getId()`에서 변수 처럼 더하는 값이 문자열이면  
URL 인코딩이 안되기 때문에 위험하다. 그래서 `RedirectAttribute` 를 사용

- **RedirectAttribute**
URL 인코딩도 해주고, `pathVarible`, 쿼리 파라미터까지 처리해 준다.