package com.emc.xsdviewer.server;

import com.mongodb.*;

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

    private AtomicLong counter = new AtomicLong();

    public MongoDB() {
        mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        db = mongoClient.getDB("xsdDB");
        xsdCollection = db.getCollection("xsd_files");
        if (xsdCollection == null) {
            xsdCollection = db.createCollection("xsd_files", null);
        }

        // HOW to assign an ID?????????
        counter.set(xsdCollection.getCount());
        counter.incrementAndGet();
        //THIS code in order for DB to be not empty, after the refactoring must be removed!!!!
        add(new XsdViewComposition(counter.toString(),
                "xsd schema " + counter.toString(), "content " + counter.toString()));
    }

    public void add(XsdViewComposition xsd) {
        XsdViewComposition newXsd = new XsdViewComposition(xsd.getName(), xsd.getXsdSchema(), xsd.getContent());
        BasicDBObject doc = newXsd.toDBObject();
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

    public XsdViewComposition getByID(final String ID) {
        DBObject result = findRecordByID(ID);
        if (result != null) return XsdViewComposition.fromDBObject(result);
        return null;
    }

    public void updateNameByID(final String ID, final String newName) {
        BasicDBObject newData = new BasicDBObject();
        newData.put("_name", newName);
        newData.put("_id", ID);
        BasicDBObject searchQuery = new BasicDBObject().append("_id", ID);
        xsdCollection.update(searchQuery, newData);

    }

    public void removeByID(final String id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", id);
        xsdCollection.remove(query);
    }

    public void clear() {
        xsdCollection.remove(new BasicDBObject());
    }

    private DBObject findRecordByID(final String ID) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", ID);
        return xsdCollection.findOne(query);
    }
}