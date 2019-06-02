package tel.schich.abacus.matcher;

import tel.schich.abacus.context.ContextBundle;
import tel.schich.abacus.matcher.Matcher;

import java.util.Set;

public class AttributesEqualMatcher implements Matcher {

    private final String leftType;
    private final String leftName;
    private final String rightType;
    private final String rightName;

    public AttributesEqualMatcher(String leftType, String leftName, String rightType, String rightName) {
        this.leftType = leftType;
        this.leftName = leftName;
        this.rightType = rightType;
        this.rightName = rightName;
    }

    @Override
    public boolean match(ContextBundle bundle) {
        Set<String> leftValue = bundle.lookup(leftType, leftName);
        Set<String> rightValue = bundle.lookup(rightType, rightName);
        return leftValue.equals(rightValue);
    }
}
