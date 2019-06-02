package tel.schich.abacus.matcher;

import tel.schich.abacus.context.ContextBundle;
import tel.schich.abacus.matcher.Matcher;

import java.util.Set;

public class AttributesContainsMatcher implements Matcher {

    private final String type;
    private final String name;
    private final String containsType;
    private final String containsName;

    public AttributesContainsMatcher(String type, String name, String containsType, String containsName) {
        this.type = type;
        this.name = name;
        this.containsType = containsType;
        this.containsName = containsName;
    }

    @Override
    public boolean match(ContextBundle bundle) {
        Set<String> value = bundle.lookup(type, name);
        Set<String> containsValue = bundle.lookup(containsType, containsName);
        return value.containsAll(containsValue);
    }
}
