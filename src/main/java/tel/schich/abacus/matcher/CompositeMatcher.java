package tel.schich.abacus.matcher;

import java.util.function.BiFunction;

import tel.schich.abacus.context.ContextBundle;

public class CompositeMatcher implements Matcher {
    private final Matcher left;
    private final Matcher right;
    private final BiFunction<Boolean, Boolean, Boolean> composition;

    public CompositeMatcher(Matcher left, Matcher right, BiFunction<Boolean, Boolean, Boolean> composition) {
        this.left = left;
        this.right = right;
        this.composition = composition;
    }

    @Override
    public boolean match(ContextBundle bundle) {
        return composition.apply(left.match(bundle), right.match(bundle));
    }

    public static CompositeMatcher and(Matcher left, Matcher right) {
        return new CompositeMatcher(left, right, (l, r) -> l && r);
    }

    public static CompositeMatcher or(Matcher left, Matcher right) {
        return new CompositeMatcher(left, right, (l, r) -> l || r);
    }
}
