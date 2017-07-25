package com.emc.xsdviewer.webapp;

/**
 * Created by ivan on 20.07.17.
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class LandingPageController {

    @RequestMapping("/abc")
    public String showHomePage(Map<String, Object> model) { return "static/index.html"; }
}
