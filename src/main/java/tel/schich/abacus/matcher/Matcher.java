package tel.schich.abacus.matcher;

import tel.schich.abacus.context.ContextBundle;
import tel.schich.abacus.matcher.value.ValueMatcher;

public interface Matcher {
    boolean match(ContextBundle bundle);

    default Matcher and(Matcher right) {
        return CompositeMatcher.and(this, right);
    }
}
