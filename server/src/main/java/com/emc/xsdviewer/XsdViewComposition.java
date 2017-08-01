package com.emc.xsdviewer;

import org.bson.types.ObjectId;
import org.springframework.hateoas.Link;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class XsdViewComposition {
    private String name = "null";
    private String id = "null";
    private String xsdSchema = "null";
    private Link link = new Link("null");
    private final String port = "8765";
    private final String urlTemplate = "http://localhost:" + port + "/%s";

    public XsdViewComposition(final String name, final String id, final String xsdSchema) {
        this.name = name;
        this.id = id;
        this.xsdSchema = xsdSchema;
        this.link = new Link(String.format(urlTemplate, this.id.toString()));
    }

    public XsdViewComposition(final String name, final String xsdSchema) {
        this.name = name;
        this.id = new ObjectId().toHexString();
        this.xsdSchema = xsdSchema;
        this.link = new Link(String.format(urlTemplate, this.id.toString()));
    }


    public String getName() {
        return name;
    }

    public String getXsdSchema() {
        return xsdSchema;
    }

    public Link getLink() {
        return link;
    }

    public String getId() {
        return id;
    }

    public BasicDBObject toDBObject() {
        BasicDBObject document = new BasicDBObject();
        document.put("_id", id);
        document.put("_name", name);
        document.put("_xsd_schema", xsdSchema);
        document.put("_link", link);

        return document;
    }

    public static XsdViewComposition fromDBObject(final DBObject document) {
        XsdViewComposition xsd = new XsdViewComposition(
                (String)document.get("_name"),
                (String)document.get("_id"),
                (String)document.get("_xsd_schema"));
        return xsd;
    }

    @Override
    public String toString() {
        return "XsdViewComposition{"
                + "name='" + name + '\''
                + ", id='" + id + '\''
                + ", xsdSchema='" + xsdSchema + '\''
                + ", link=" + link
                + '}';
    }
}
