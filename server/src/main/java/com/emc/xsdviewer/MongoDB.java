package com.emc.xsdviewer;

import com.mongodb.*;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by nika- on 18.07.2017.
 */

public class MongoDB {

    private MongoClient mongoClient;
    private DB db;
    private DBCollection xsdCollection;
    private GridFS gridfs;
    private final String COLLECTION_NAME = "xsd_files";

    public MongoDB() {
        mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        db = mongoClient.getDB("xsdDB");
        xsdCollection = db.getCollection(COLLECTION_NAME);
        if (xsdCollection == null) {
            xsdCollection = db.createCollection(COLLECTION_NAME, null);
        }
        gridfs = new GridFS(db, COLLECTION_NAME);
    }

    public void add(final XsdViewComposition xsd) {
        XsdViewComposition newXsd = new XsdViewComposition(xsd.getName(), xsd.getXsdSchema());
        BasicDBObject doc = newXsd.toDBObject();
        xsdCollection.insert(doc);
    }

//    public List<XsdViewComposition> getAll() {
//        List<XsdViewComposition> xsdFiles = new ArrayList<XsdViewComposition>();
//        DBCursor cursor = xsdCollection.find();
//        while (cursor.hasNext()) {
//            DBObject dbo = cursor.next();
//            xsdFiles.add(XsdViewComposition.fromDBObject(dbo));
//        }
//        return xsdFiles;
//    }

    public HashSet<String> getAllNames() {
        HashSet<String> xsdFiles = new HashSet<>();
        DBCursor cursor = gridfs.getFileList();
        while (cursor.hasNext()) {
            DBObject object = cursor.next();
            xsdFiles.add((String) object.get("filename"));
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
        gridfs.remove(new BasicDBObject());
    }

    private DBObject findRecordByID(final String ID) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", ID);
        return xsdCollection.findOne(query);
    }

    public void addFile(final MultipartFile multipartFile, final String name) throws IOException {
        InputStream inputStream = getInputStreamFromMultipartFile(multipartFile);
        GridFSInputFile inputFile = gridfs.createFile(inputStream, name);
        inputFile.save();
    }

//    public String getFile(final String name) {
//        StringWriter sw = new StringWriter();
//        InputStream inputStream = getInputStreamFromGridFSD(name);
//        String readLine;
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//        try {
//            while (((readLine = bufferedReader.readLine()) != null)) {
//                System.out.println(readLine);
//                sw.append(readLine);
//            }
//        } catch (Exception e) {
//            //LOG????
//        }
//        return sw.toString();
//    }

//    public String getByName(final String name) {
//        return getFile(name);
//    }

    public InputStream getInputStream(final String name) {
        return getInputStreamFromGridFSD(name);
    }

    public HashSet<String> getAllAttributes() {
        HashSet<String> allAttributes = new HashSet<String>(Arrays.asList("a attribute", "b attribute", "c attribute"));
        //TODO: To register a file in the database, with a list of attributes; In this method, get the file from the database
        return allAttributes;
    }

    private InputStream getInputStreamFromMultipartFile(final MultipartFile multipartFile) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(multipartFile.getBytes());
        byte[] file = new byte[inputStream.available()];
        inputStream.read(file);
        return new ByteArrayInputStream(file);
    }

    private InputStream getInputStreamFromGridFSD(final String name) {
        GridFSDBFile gfsFileOut = (GridFSDBFile) gridfs.findOne(name);
        if (gfsFileOut == null) return null;
        return gfsFileOut.getInputStream();
    }

    public boolean checkName(final String name) {
        HashSet<String> allNames = getAllNames();
        return allNames.contains(name);
    }
}