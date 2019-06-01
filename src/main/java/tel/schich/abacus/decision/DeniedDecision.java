package tel.schich.abacus.decision;

import tel.schich.abacus.eval.PolicyEvaluator;

public final class DeniedDecision implements Decided {
    private final PolicyEvaluator source;

    public DeniedDecision(PolicyEvaluator source) {
        this.source = source;
    }

    @Override
    public PolicyEvaluator getSource() {
        return source;
    }
}
