package tel.schich.abacus.matcher;

import tel.schich.abacus.context.ContextBundle;

public class TypeMatcher implements Matcher {

    private final String typeName;

    public TypeMatcher(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public boolean match(ContextBundle bundle) {
        return bundle.has(typeName);
    }
}
