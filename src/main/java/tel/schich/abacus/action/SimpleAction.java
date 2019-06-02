package tel.schich.abacus.action;

import java.util.Map;

import static java.util.Collections.singletonMap;

import tel.schich.abacus.decision.Decision;

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

    @Override
    public boolean accepts(Decision decision) {
        // TODO only accept decisions with the correct "return type"
        return true;
    }
}
