package com.emc.xsdviewer;

import com.emc.xsdviewer.XSDParser.Settings;
import com.emc.xsdviewer.XSDParser.XSDTreeObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@RestController
public class Controller {

    private MongoDB db = new MongoDB();
    private XSDTreeObject tree;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<String> getAll() {
        return db.getAll();
    }

    @RequestMapping(value = "/{NAME}", method = RequestMethod.GET)
    public Object get(final @PathVariable String NAME,
                      final @RequestParam(value = "attributes", required = false) HashSet<String> attributes) {

        Settings settings = new Settings();
        InputStream inputStream = db.getInputStream(NAME);
        if (attributes != null) {
            settings.setXSDNodesNames(attributes);
        }

        if (inputStream != null) {
            tree = new XSDTreeObject(db.getInputStream(NAME));
            return tree.getTree(settings);
        }
        return new String("XSD with NAME: " + NAME + " not found");
    }

    @RequestMapping(value = "/allAttributes", method = RequestMethod.GET)
    public HashSet<String> get() {
        return new HashSet<String>(Arrays.asList("a attribute", "b attribute", "c attribute"));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    public ResponseEntity<?> add(final @RequestParam("xsdScheme") MultipartFile uploadFile,
                                 final @RequestParam("name") String name) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(uploadFile.getBytes());
            byte[] file = new byte[inputStream.available()];
            inputStream.read(file);
            db.addFile(file, name);

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