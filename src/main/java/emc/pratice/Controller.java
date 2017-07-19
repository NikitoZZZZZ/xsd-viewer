package emc.pratice;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {

    MongoDB db = new MongoDB();

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<XsdViewComposition> getAll() {
        return db.getAll();
    }

    @RequestMapping(value = "/{ID}", method = RequestMethod.GET)
    public Object get(@PathVariable Long ID) {
        XsdViewComposition object = db.getByID(ID);
        if (object != null)
            return object;
        return new String("XSD with ID: " + ID + " not found");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/all")
    public void add(@RequestBody XsdViewComposition xsd) {
        //TODO: there must be load xsd-file
        db.add(xsd);
    }

    @RequestMapping(value = "/{ID}", method = RequestMethod.DELETE)
    public String delete(@PathVariable Long ID) {
        db.removeByID(ID);
        //TODO: create notification, if xsd is not exist
        return new String("XSD with ID: " + ID.toString() + " removed");
    }

    @RequestMapping(value = "/all", method = RequestMethod.DELETE)
    public String delete() {
        db.clear();
        return new String("BD is empty");
    }
}
