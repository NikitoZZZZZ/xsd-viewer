package emc.pratice;

import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class Controller {

    private final AtomicLong counter = new AtomicLong();

    Storage storage = new Storage();

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Storage get() {
        return storage;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/all")
    public void add(@RequestBody XsdViewComposition xsd) {
        storage.add(xsd);
    }

    @RequestMapping(value = "/{ID}", method = RequestMethod.DELETE)
    public String delete(@PathVariable Long ID) {
        storage.deleteByID(ID);
        //TO DO:
        //create notification, if this xsd is not exist
        return new String("XSD with ID: " + ID.toString() + " removed");
    }

    @RequestMapping(value = "/all", method = RequestMethod.DELETE)
    public String delete(@PathVariable String name) {
        storage.deleteAll();
        //TO DO:
        //create notification, if this xsd is not exist
        return new String("XSD with name: " + name + " removed");
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public Object get(@PathVariable String name) {
        XsdViewComposition object = storage.getByName(name);
        if (object != null)
            return object;
        else return new String("XSD not found");
    }


}
