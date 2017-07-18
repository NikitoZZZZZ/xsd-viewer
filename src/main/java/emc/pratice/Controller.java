package emc.pratice;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    private static final String template = "%s";
    private final AtomicLong counter = new AtomicLong();


    private List<XsdViewComposition> listOfXsd = new ArrayList<>(Arrays.asList(
            new XsdViewComposition("Veronika",counter.incrementAndGet(),"Some scheme"+counter,"Some content "+counter),
            new XsdViewComposition("Nikita",counter.incrementAndGet(),"Some scheme"+counter,"Some content "+counter),
            new XsdViewComposition("Vanya",counter.incrementAndGet(),"Some scheme"+counter,"Some content "+counter)
            ));

    @RequestMapping("/all")
    public List<XsdViewComposition> View() {
        return listOfXsd;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/all")
    public void addXsdScheme(@RequestBody XsdViewComposition xsd) {
        listOfXsd.add(xsd);
    }

    @RequestMapping("/all/{name}")
    public XsdViewComposition getXsdScheme(@PathVariable String name){

        return listOfXsd.stream().filter(t->t.getName().equals(name)).findFirst().get();
    }


}
