package com.emc.xsdviewer;

import com.emc.xsdviewer.XSDParser.XSDTreeObject;
import com.mongodb.DBObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
public class Controller {

    MongoDB db = new MongoDB();
    private XSDTreeObject tree;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<DBObject> getAll() {
        return db.getAll();
    }

//    @RequestMapping(value = "/{ID}", method = RequestMethod.GET)
//    public Object get(@PathVariable String ID) {
//        XsdViewComposition object = db.getByID(ID);
//        if (object != null)
//            return object;
//        return new String("XSD with ID: " + ID + " not found");
//    }

    @RequestMapping(value = "/{NAME}", method = RequestMethod.GET)
    public Object get(@PathVariable String NAME) {
        //Object object = db.getByName(NAME);
    	tree = new XSDTreeObject(db.getInputStream(NAME));
        if (/*object*/tree != null)
            return /*object*/tree;
        return new String("XSD with NAME: " + NAME + " not found");
    }


    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    public ResponseEntity<?> add(final @RequestParam("xsdScheme") MultipartFile uploadFile,
    	final @RequestParam("name") String name) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(uploadFile.getBytes());
            byte[] file = new byte[inputStream.available()];
            inputStream.read(file);
            db.addFile(file, name);
            //db.getFile(name);

        } catch (Exception e) {
            //LOGGER.info(e.getMessage());
            return new ResponseEntity<>(org.springframework.http.HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(org.springframework.http.HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.PUT, value = "/{ID}")
    public Object update(@PathVariable String ID, @RequestBody XsdViewComposition xsd) {
        db.updateNameByID(ID, xsd.getName());
        return db.getByID(ID);
    }

    @RequestMapping(value = "/{ID}", method = RequestMethod.DELETE)
    public String delete(@PathVariable String ID) {
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