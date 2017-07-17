package emc.pratice;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private static final String template = "%s";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/xsd")
    public XsdViewComposition View(@RequestParam(value="name", defaultValue="simple name") String name) {
        return new XsdViewComposition(String.format(template, name),
                counter.incrementAndGet(),"simple xsdSchema","SIMPLE CONTENT");
    }
}
