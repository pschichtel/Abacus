package tel.schich.abacus.action;

import java.util.Map;

import static java.util.Collections.singletonMap;

public class SimpleAction implements Action {

    private final String name;

    public SimpleAction(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public Map<String, String> contextAttributes() {
        return singletonMap("name", this.name);
    }
}
