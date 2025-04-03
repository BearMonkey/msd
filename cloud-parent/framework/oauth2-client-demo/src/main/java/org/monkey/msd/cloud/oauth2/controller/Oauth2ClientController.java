package org.monkey.msd.cloud.oauth2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Oauth2ClientController
 *
 * @author cc
 * @since 2025/4/1 16:54
 */
@Controller
public class Oauth2ClientController {

    private static final Logger log = LoggerFactory.getLogger(Oauth2ClientController.class);

    @GetMapping("/index")
    public ModelAndView home(/*OAuth2AuthenticationToken token*/) {
//        log.info(String.valueOf(token.getPrincipal()));
        ModelAndView home = new ModelAndView("home");
        Map<String, Object> data = new HashMap<>();
        data.put("message", "Oauth2 Client Demo");
        home.addAllObjects(data);
        return home;
    }
}
