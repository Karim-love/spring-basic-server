package hello.springmvc.basic;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : hello.springmvc.basic
 * @name : spring-basic-server
 * @date : 2023. 08. 30. 030 오후 4:07
 * @modifyed :
 * @description :
 **/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogTestController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        logger.trace("trace log = {}", name);
        logger.debug("debug log = {}", name);
        logger.info("info log = {}", name);
        logger.warn("warn log = {}", name);
        logger.error("error log = {}", name);
        return "ok";
    }
}
