package tel.schich.abacus.example;

import java.util.HashMap;
import java.util.Map;

import tel.schich.abacus.context.Context;

public class Role implements Context {

    private final String name;

    public Role(String name) {
        this.name = name;
    }

    @Override
    public String contextType() {
        return "role";
    }

    @Override
    public Map<String, String> contextAttributes() {
        Map<String, String> attribtes = new HashMap<>();
        attribtes.put("name", this.name);
        return attribtes;
    }
}
