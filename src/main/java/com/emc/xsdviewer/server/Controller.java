package com.emc.xsdviewer.server;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
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

    @RequestMapping(method = RequestMethod.PUT, value = "/{ID}")
    public Object update(@PathVariable Long ID, @RequestBody XsdViewComposition xsd){
        db.updateNameByID(ID, xsd.getName());
        return db.getByID(ID);
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

        @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("xsdScheme") MultipartFile uploadFile){

        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(uploadFile.getBytes());
            byte[] file = new byte[inputStream.available()];
            inputStream.read(file);
            db.addFile(file,"xsd1");
            db.getFile("xsd1");

            //if we want to look on a file, we can print a file to console
            //XsdViewComposition.printFromVolatileMemory(inputFile);

            //if we want to save file localy without DB
            //XsdViewComposition.saveToFile(uploadFile,uploadFile.getName());

        }

        catch(Exception e){
            //LOGGER.info(e.getMessage());
            return new ResponseEntity<>(org.springframework.http.HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(org.springframework.http.HttpStatus.OK);
    }

}


