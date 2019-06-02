package tel.schich.abacus.example.domain;

import tel.schich.abacus.context.Context;

import java.util.HashMap;
import java.util.Map;

public abstract class SimpleContext implements Context {

    protected Map<String, String> map = new HashMap<>();

    @Override
    public Map<String, String> contextAttributes() {
        return this.map;
    }
}
