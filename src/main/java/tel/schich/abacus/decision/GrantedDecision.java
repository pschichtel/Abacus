package tel.schich.abacus.decision;

import tel.schich.abacus.eval.PolicyEvaluator;

public final class GrantedDecision implements Decided {
    private final PolicyEvaluator source;

    public GrantedDecision(PolicyEvaluator source) {
        this.source = source;
    }

    @Override
    public PolicyEvaluator getSource() {
        return source;
    }
}
