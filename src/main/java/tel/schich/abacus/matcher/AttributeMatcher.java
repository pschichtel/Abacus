package tel.schich.abacus.matcher;

import tel.schich.abacus.context.ContextBundle;

public class AttributeMatcher implements Matcher {
    private final String typeName;
    private final String attributeName;

    public AttributeMatcher(String typeName, String attributeName) {
        this.typeName = typeName;
        this.attributeName = attributeName;
    }

    @Override
    public boolean match(ContextBundle bundle) {
        return bundle.has(typeName, attributeName);
    }
}
