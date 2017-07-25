package com.emc.xsdviewer;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.springframework.hateoas.Link;

public class XsdViewComposition {
    private String name = "null";
    private String id = "null";
    private String xsdSchema = "null";
    private String content = "null";
    private Link link = new Link("null");
    private final String PORT = "8765";
    private final String URL_TEMPLATE = "http://localhost:" + PORT + "/%s";

    public XsdViewComposition(final String name, final String id, final String xsdSchema, final String content) {
        this.name = name;
        this.id = id;
        this.xsdSchema = xsdSchema;
        this.content = content;
        this.link = new Link(String.format(URL_TEMPLATE, this.id.toString()));
    }

    public XsdViewComposition(final String name, final String xsdSchema, final String content) {
        this.name = name;
        this.id = new ObjectId().toHexString();
        this.xsdSchema = xsdSchema;
        this.content = content;
        this.link = new Link(String.format(URL_TEMPLATE, this.id.toString()));
    }


    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getXsdSchema() {
        return xsdSchema;
    }

    public String getContent() {
        return content;
    }

    public Link getLink() {
        return link;
    }

    public BasicDBObject toDBObject() {
        BasicDBObject document = new BasicDBObject();
        document.put("_id", id);
        document.put("_name", name);
        document.put("_xsd_schema", xsdSchema);
        document.put("_content", content);
        //document.put("_url", URL);
        document.put("_link", link);

        return document;
    }

    public static XsdViewComposition fromDBObject(final DBObject document) {
        XsdViewComposition xsd = new XsdViewComposition(
                (String) document.get("_name"),
                (String) document.get("_id"),
                (String) document.get("_xsd_schema"),
                (String) document.get("_content"));
        return xsd;
    }

    @Override
    public String toString() {
        return "XsdViewComposition{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", xsdSchema='" + xsdSchema + '\'' +
                ", content='" + content + '\'' +
                ", link=" + link +
                '}';
    }
}
