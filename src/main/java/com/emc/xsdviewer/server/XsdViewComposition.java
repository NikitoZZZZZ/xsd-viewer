package com.emc.xsdviewer.server;

//        - name
//        - id
//        - xsdSchema
//        - Content
//        - URL


import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Scanner;

public class XsdViewComposition {
    private final String name;
    private final Long id;
    private final String xsdSchema;
    private final String content;
    private final String URL;
    private final String PORT = "8090";
    private final String URL_TEMPLATE = "http://localhost:" + PORT + "/%s";

    public XsdViewComposition(String name, long id, String xsdSchema, String content) {
        this.name = name;
        this.id = id;
        this.xsdSchema = xsdSchema;
        this.content = content;
        this.URL = new String(String.format(URL_TEMPLATE, this.id.toString()));
    }

    public XsdViewComposition() {
        this.name = "null";
        this.id = new Long(0);
        this.xsdSchema = "null";
        this.content = "null";
        URL = new String(String.format(URL_TEMPLATE, this.id.toString()));
    }


    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public String getXsdSchema() {
        return xsdSchema;
    }

    public String getContent() {
        return content;
    }

    public String getURL() {
        return URL;
    }

    public BasicDBObject toDBObject() {
        BasicDBObject document = new BasicDBObject();

        document.put("ID", id);
        document.put("NAME", name);
        document.put("XSD_SCHEMA", xsdSchema);
        document.put("CONTENT", content);
        document.put("URL", URL);

        return document;
    }

    public static XsdViewComposition fromDBObject(final DBObject document) {
        XsdViewComposition xsd = new XsdViewComposition(
                (String) document.get("NAME"),
                (long) document.get("ID"),
                (String) document.get("XSD_SCHEMA"),
                (String) document.get("CONTENT"));

        return xsd;
    }

    @Override
    public String toString() {
        return "XsdViewComposition{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", xsdSchema='" + xsdSchema + '\'' +
                ", content='" + content + '\'' +
                ", URL='" + URL + '\'' +
                ", PORT='" + PORT + '\'' +
                ", URL_TEMPLATE='" + URL_TEMPLATE + '\'' +
                '}';
    }

    public static void saveToFile(MultipartFile uploadFile,String path){
        try {
            final BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(path)));
            stream.write(uploadFile.getBytes());
            stream.close();
        } catch (Exception e){
            System.out.println("Error with saving");
        }
    }

    public  static void printFromVolatileMemory(ByteArrayInputStream inputFile){

        Scanner scanner = new Scanner(inputFile);
        scanner.useDelimiter("\\Z");//To read all scanner content in one String
        String data = "";
        if (scanner.hasNext())
            data = scanner.next();
        System.out.println(data);
    }

}
