package tel.schich.abacus.matcher;

import tel.schich.abacus.context.ContextBundle;
import tel.schich.abacus.matcher.value.ValueMatcher;

import static tel.schich.abacus.matcher.value.SimpleValueMatcher.EQUALITY;

public class AttributeValueMatcher implements Matcher {
    private final String typeName;
    private final String attributeName;
    private final String value;
    private final ValueMatcher matcher;

    public AttributeValueMatcher(String typeName, String attributeName, String value) {
        this(typeName, attributeName, value, EQUALITY);
    }

    public AttributeValueMatcher(String typeName, String attributeName, String value, ValueMatcher matcher) {
        this.typeName = typeName;
        this.attributeName = attributeName;
        this.value = value;
        this.matcher = matcher;
    }

    @Override
    public boolean match(ContextBundle bundle) {
        return bundle.lookup(typeName, attributeName).stream().anyMatch(s -> matcher.match(s, value));
    }
}
