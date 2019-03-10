package com.zfoo.web.river.facade;

import com.zfoo.event.EventContext;
import com.zfoo.event.model.anno.EventReceiver;
import com.zfoo.storage.model.anno.ResInjection;
import com.zfoo.storage.model.vo.Storage;
import com.zfoo.web.river.event.HelloEvent;
import com.zfoo.web.river.storage.StudentResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2019-03-10 19:43
 */
@Controller
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Value("${name}")
    private String name;

    @ResInjection
    Storage<String, StudentResource> resources;

    // localhost:8080/hello
    @GetMapping(value = "/hello")
    public String hello() {
        // event
        EventContext.getEventBusManager().syncSubmit(HelloEvent.valueOf("hello"));

        // storage
        for (StudentResource resource : resources.getAll()) {
            logger.info(resource.toString());
        }
        return "hello";
    }

    @EventReceiver
    public void helloEvent(HelloEvent event) {
        logger.info("receive [msg:{}]", event.getMsg());
    }

    //@Scheduler(value = "helloScheduler", cronExpression = "0/5 * * * * ?")
    public void helloScheduler() {
        logger.info("helloScheduler [date:{}]", new Date());
    }


    /**
     * 404 error
     *
     * @return
     */
    @RequestMapping("/404")
    public String error404() {
        return "index.html";
    }

    /**
     * 500 error
     *
     * @return
     */
    @RequestMapping("/500")
    public String error500() {
        return "index.html";
    }

}
