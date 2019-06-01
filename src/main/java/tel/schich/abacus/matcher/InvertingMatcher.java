package tel.schich.abacus.matcher;

import tel.schich.abacus.context.ContextBundle;

public class InvertingMatcher implements Matcher {
    private Matcher subMatcher;

    public InvertingMatcher(Matcher subMatcher) {
        this.subMatcher = subMatcher;
    }

    @Override
    public boolean match(ContextBundle bundle) {
        return !subMatcher.match(bundle);
    }
}
