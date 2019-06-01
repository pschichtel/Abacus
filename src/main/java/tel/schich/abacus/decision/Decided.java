package tel.schich.abacus.decision;

import tel.schich.abacus.eval.PolicyEvaluator;

public interface Decided extends Decision {
    PolicyEvaluator getSource();
}
