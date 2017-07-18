package emc.pratice;

//        - name
//        - id
//        - xsdSchema
//        - Content
//        - links {
//                - delete
//                - update
//               }


public class XsdViewComposition {
    private final String name;
    private final Long id;
    private final String xsdSchema;
    private final String content;
    private final String deleteURL;
    private final String updateURL;

    public XsdViewComposition(String name, long id, String xsdSchema, String content) {
        this.name = name;
        this.id = id;
        this.xsdSchema = xsdSchema;
        this.content = content;
        this.deleteURL = "http://localhost:8090/" + this.id.toString();
        this.updateURL = "http://localhost:8090/" + this.id.toString();
    }

    public XsdViewComposition() {
        this.name = "null";
        this.id = new Long(0);
        this.xsdSchema = "null";
        this.content = "null";
        updateURL = null;
        deleteURL = null;
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

    public String getDeleteURL() {
        return deleteURL;
    }

    public String getUpdateURL() {
        return updateURL;
    }
}
