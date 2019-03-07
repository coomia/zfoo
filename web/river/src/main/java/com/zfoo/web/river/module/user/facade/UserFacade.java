package com.zfoo.web.river.module.user.facade;

import com.zfoo.event.model.anno.EventReceiver;
import com.zfoo.web.river.module.login.event.LoginEvent;
import com.zfoo.web.river.module.user.manager.IUserManager;
import com.zfoo.web.river.module.user.resource.UserPrivilegeResource;
import com.zfoo.scheduler.model.anno.Scheduler;
import com.zfoo.util.JsonUtils;
import com.zfoo.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018/10/20
 */
@Controller
@RequestMapping("")
public class UserFacade {

    private static final Logger logger = LoggerFactory.getLogger(UserFacade.class);

    @Value("${river.version}")
    private String version;

    @Autowired
    private IUserManager userManager;

    @EventReceiver
    public void handleSyncLoginEvent(LoginEvent event) {
        logger.info(StringUtils.MULTIPLE_HYPHENS);
        logger.info("handleSyncLoginEvent [facade:{}] receives [event:{}]", UserFacade.class.getSimpleName(), JsonUtils.object2String(event));

        logger.info(StringUtils.MULTIPLE_HYPHENS);
        for (UserPrivilegeResource resource : userManager.getAllUserPrivilegeResource()) {
            logger.info("{}", JsonUtils.object2String(resource));
            System.out.println(resource.getPrivilege());
            System.out.println(JsonUtils.object2String(resource));
        }

    }


    @Scheduler(value = "userScheduler", cronExpression = "0/5 * * * * ?")
    public void userScheduler() {
        logger.info(StringUtils.MULTIPLE_HYPHENS);
        logger.info("time is [time:{}]", new Date().toString());
        logger.info("version is [version:{}]", version);
    }


}
