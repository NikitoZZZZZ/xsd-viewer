package com.emc.xsdviewer;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.emc.xsdviewer.parser.Settings;
import com.emc.xsdviewer.parser.XsdTreeObject;


@RestController
public class Controller {

    private MongoDB db = new MongoDB();
    private XsdTreeObject tree;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public HashSet<String> getAll() {
        return db.getAllNames();
    }

    @RequestMapping(value = "/{NAME}", method = RequestMethod.GET)
    public ResponseEntity<?> get(final @PathVariable String name,
                                 final @RequestParam(value = "attributes", required = false)
                                 HashSet<String> attributes) {

        Settings settings = new Settings();
        InputStream inputStream = db.getInputStream(name);
        if (attributes != null) {
            settings.setXsdNodesNames(attributes);
        }

        if (inputStream != null) {
            tree = new XsdTreeObject(db.getInputStream(name));
            return new ResponseEntity<>(tree.getTree(settings), HttpStatus.OK);
        }
        return new ResponseEntity<>("XSD with name: " + name + " not found", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/allAttributes", method = RequestMethod.GET)
    public HashSet<String> get() {
        HashSet<String> setOfAttributes = db.getAllAttributes();
        return setOfAttributes;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> add(final @RequestParam("xsdScheme") MultipartFile uploadFile,
                                 final @RequestParam("name") String name) {
        if (db.checkName(name)) {
            return new ResponseEntity<>("This name (" + name + ") is already taken", HttpStatus.OK);
        }
        try {
            db.addFile(uploadFile, name);
        } catch (final IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("XSD-file added", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{ID}")
    public ResponseEntity<?> update(final @PathVariable String id,
            final @RequestBody XsdViewComposition xsd) {
        db.updateNameByID(id, xsd.getName());
        return new ResponseEntity<>(db.getByID(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(final @PathVariable String id) {
        db.removeByID(id);
        //TODO: create notification, if xsd is not exist
        return new ResponseEntity<>("XSD with id: " + id.toString() + " removed", HttpStatus.OK);
    }

    @RequestMapping(value = "/all", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete() {
        db.clear();
        return new ResponseEntity<>("BD is empty", HttpStatus.OK);
    }
}