package hello.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : hello.springmvc.basic.requestmapping
 * @name : spring-basic-server
 * @date : 2023. 08. 30. 030 오후 4:32
 * @modifyed :
 * @description :
 **/

@RestController
public class MappingController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)
    public String mappingGetV1() {
        log.info("mappingGetV1");
        return "ok";
    }

    @GetMapping(value = "/mapping-get-v2")
    public String mappingGetV2() {
        log.info("mappingGetV2");
        return "ok";
    }

    /**
     * PathVariable 사용  ( 경로 변수 )
     */
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String data) {
        log.info("mappingPath userId = {}", data);
        return "ok";
    }

    /**
     * PathVariable 사용  ( 경로 변수 )
     * 변수 명과 같으면 위에 지정 이름을 생략할 수 있다.
     */
    @GetMapping("/mapping2/{userId}")
    public String mappingPath2(@PathVariable String userId) {
        log.info("mappingPath2 userId = {}", userId);
        return "ok";
    }

    /**
     * PathVariable 다중 사용
     *
     * @param userId
     * @param orderId
     * @return
     */
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable Long orderId) {
        log.info("mappingPath userId = {}, orderId = {}", userId, orderId);
        return "ok";
    }

    /**
     * 특정 헤더로 추가 매핑
     * headers 안에 조건식 가능
     *
     * @return
     */
    @GetMapping(value = "/mapping-header", headers = "mode=debug")
    public String mappingHeader() {
        log.info("mappingHeader");
        return "ok";
    }

    /**
     * Content-Type 헤더 기반 추가 매핑 Media Type
     * consumes : 내가 해당 타입만 받겠다.
     *            요청 해더의 content-type
     * @return
     */
    @PostMapping(value = "/mapping-consume", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String mappingConsumes() {
        log.info("mappingConsumes");
        return "ok";
    }

    /**
     * Accept 헤더 기반 Media Type
     * produces : 내가 해당 타입으로 보내겠다.
     *            요청 Accept의 content-type
     * @return
     */
    @PostMapping(value = "/mapping-consume", produces = MediaType.TEXT_HTML_VALUE)
    public String mappingProduces() {
        log.info("mappingConsumes");
        return "ok";
    }
}
