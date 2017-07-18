package emc.pratice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by nika- on 18.07.2017.
 */
public class Storage {

    private final AtomicLong counter = new AtomicLong();

    public ArrayList<XsdViewComposition> xsdObjects = new ArrayList<XsdViewComposition>(Arrays.asList(
            new XsdViewComposition("Veronika", counter.incrementAndGet(), "Some scheme" + counter, "Some content " + counter),
            new XsdViewComposition("Nikita", counter.incrementAndGet(), "Some scheme" + counter, "Some content " + counter),
            new XsdViewComposition("Vanya", counter.incrementAndGet(), "Some scheme" + counter, "Some content " + counter)
    ));

    public void add(XsdViewComposition object) {
        xsdObjects.add(object);
    }

    public XsdViewComposition getByName(final String name) {
        if (xsdObjects.stream()
                .filter(obj -> obj.getName() == name)
                .findFirst()
                .isPresent())
            return xsdObjects.stream()
                    .filter(obj -> obj.getName() == name)
                    .findFirst()
                    .get();
        else
            return null;
    }

    public void deleteByID(final Long ID) {
        xsdObjects.removeIf(object -> object.getId() == ID);
    }

    public void deleteAll() {
        xsdObjects.clear();
    }
}
