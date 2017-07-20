package com.emc.xsdviewer.server;

import com.mongodb.*;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by nika- on 18.07.2017.
 */

public class MongoDB {

    private MongoClient mongoClient;
    private DB db;
    private DBCollection xsdCollection;
    private GridFS gridfs;


    private AtomicLong counter = new AtomicLong();

    public MongoDB() {


        mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        db = mongoClient.getDB("xsdDB");
        xsdCollection = db.getCollection("xsd_files");
        if (xsdCollection == null) {
            xsdCollection = db.createCollection("xsd_files", null);
        }

         gridfs = new GridFS(db,"xsd collections");

        // HOW to assign an ID?????????
        counter.set(xsdCollection.getCount());

        //THIS code in order for DB to be not empty, after the refactoring must be removed!!!!
        add(new XsdViewComposition(counter.toString(), counter.incrementAndGet(),
                "xsd schema " + counter.toString(), "content " + counter.toString()));
    }

    public void add(XsdViewComposition xsd) {
        BasicDBObject doc = xsd.toDBObject();
        xsdCollection.insert(doc);

    }
    public void addXsd(XsdViewComposition xsd) {
        BasicDBObject doc = xsd.toDBObject();
        xsdCollection.insert(doc);

    }

    public List<XsdViewComposition> getAll() {
        List<XsdViewComposition> xsdFiles = new ArrayList<XsdViewComposition>();
        DBCursor cursor = xsdCollection.find();
        while (cursor.hasNext()) {
            DBObject dbo = cursor.next();
            xsdFiles.add(XsdViewComposition.fromDBObject(dbo));
        }
        return xsdFiles;
    }

    public XsdViewComposition getByID(final Long ID) {
        DBObject result = findRecordByID(ID);
        if (result != null) return XsdViewComposition.fromDBObject(result);
        return null;
    }

    public void updateNameByID(final Long ID, final String newName) {
        BasicDBObject newData = new BasicDBObject();
        newData.put("NAME", newName);
        newData.put("ID", ID);
        BasicDBObject searchQuery = new BasicDBObject().append("ID", ID);
        xsdCollection.update(searchQuery, newData);

    }

    public void removeByID(Long id) {
        BasicDBObject query = new BasicDBObject();
        query.put("ID", id);
        xsdCollection.remove(query);
    }

    public void clear() {
        xsdCollection.remove(new BasicDBObject());
    }

    private DBObject findRecordByID(final Long ID) {
        BasicDBObject query = new BasicDBObject();
        query.put("ID", ID);
        return xsdCollection.findOne(query);
    }

    public void addFile(byte[] file,String name){
        InputStream InputStream = new ByteArrayInputStream(file);
        GridFSInputFile inputfile = gridfs.createFile(InputStream,name);
        inputfile.save();


    }

    public void getFile(String name){
        GridFSDBFile gfsFileOut = (GridFSDBFile) gridfs.findOne(name);
        System.out.println(gfsFileOut.getInputStream());
        InputStream is = gfsFileOut.getInputStream();

        String readLine;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        try{
            while (((readLine = br.readLine()) != null)) {
                System.out.println(readLine);
            }
        } catch (Exception e){

        }

    }

}