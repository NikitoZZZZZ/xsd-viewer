package com.emc.xsdviewer;

import com.emc.xsdviewer.XSDParser.Settings;
import com.emc.xsdviewer.XSDParser.XSDTreeObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;

@RestController
public class Controller {

    private MongoDB db = new MongoDB();
    private XSDTreeObject tree;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public HashSet<String> getAll() {
        return db.getAllNames();
    }

    @RequestMapping(value = "/{NAME}", method = RequestMethod.GET)
    public ResponseEntity<?> get(final @PathVariable String NAME,
                                 final @RequestParam(value = "attributes", required = false)
    							 HashSet<String> attributes) {

        Settings settings = new Settings();
        InputStream inputStream = db.getInputStream(NAME);
        if (attributes != null) {
            settings.setXSDNodesNames(attributes);
        }

        if (inputStream != null) {
            tree = new XSDTreeObject(db.getInputStream(NAME));
            return new ResponseEntity<>(tree.getTree(settings), HttpStatus.OK);
        }
        return new ResponseEntity<>("XSD with NAME: " + NAME + " not found", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/allAttributes", method = RequestMethod.GET)
    public HashSet<String> get() {
        HashSet<String> setOfAttributes = db.getAllAttributes();
        return setOfAttributes;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> add(final @RequestParam("xsdScheme") MultipartFile uploadFile,
                                 final @RequestParam("name") String name) {
        if (db.checkName(name))
            return new ResponseEntity<>("This name (" + name + ") is already taken", HttpStatus.OK);
        try {
            db.addFile(uploadFile, name);
        } catch (final IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("XSD-file added", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{ID}")
    public ResponseEntity<?> update(final @PathVariable String ID,
    		final @RequestBody XsdViewComposition xsd) {
        db.updateNameByID(ID, xsd.getName());
        return new ResponseEntity<>(db.getByID(ID), HttpStatus.OK);
    }

    @RequestMapping(value = "/{ID}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(final @PathVariable String ID) {
        db.removeByID(ID);
        //TODO: create notification, if xsd is not exist
        return new ResponseEntity<>("XSD with ID: " + ID.toString() + " removed", HttpStatus.OK);
    }

    @RequestMapping(value = "/all", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete() {
        db.clear();
        return new ResponseEntity<>("BD is empty", HttpStatus.OK);
    }
}