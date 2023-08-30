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