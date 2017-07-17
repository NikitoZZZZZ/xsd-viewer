package emc.pratice;

/**
 * Created by nikita on 17/07/2017.
 */



//        - name
//        - id
//        - xsdSchema
//        - Content


public class XsdViewComposition {

    private final String name;
    private final long id;
    private final String xsdSchema;
    private final String content;

    public XsdViewComposition(String name, long id, String xsdSchema, String content) {
        this.name = name;
        this.id = id;
        this.xsdSchema = xsdSchema;
        this.content = content;
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
}
