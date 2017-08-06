package com.emc.xsdviewer.webapp;

/**
 * Created by ivan on 20.07.17.
 */

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LandingPageController {

<<<<<<< HEAD
    @RequestMapping("/a")
=======
    @RequestMapping("/")
>>>>>>> 68bdbb565e41a0df1b1627803631837c1987fa6b
    public String showHomePage(Map<String, Object> model) {
        return "static/mainform.html";
    }
}
