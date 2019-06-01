package tel.schich.abacus.eval;

import java.util.ArrayList;
import java.util.List;

import tel.schich.abacus.PolicyBundle;
import tel.schich.abacus.Resource;
import tel.schich.abacus.Subject;
import tel.schich.abacus.action.Action;
import tel.schich.abacus.context.ContextBundle;
import tel.schich.abacus.decision.Decision;

public class UncachedPolicyEvaluator implements PolicyEvaluator {

    private final List<PolicyBundle> defintions;

    public UncachedPolicyEvaluator() {
        this.defintions = new ArrayList<>();
    }

    public void addPolicies(PolicyBundle bundle) {
        this.defintions.add(bundle);
    }

    @Override
    public Decision decide(Subject subject, Resource resource, Action action, ContextBundle context) {
        return null;
    }
}
