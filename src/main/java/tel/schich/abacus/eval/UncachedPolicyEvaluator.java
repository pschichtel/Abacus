package tel.schich.abacus.eval;

import static tel.schich.abacus.decision.Undecided.UNDECIDED;

import tel.schich.abacus.PolicyBundle;
import tel.schich.abacus.Resource;
import tel.schich.abacus.Subject;
import tel.schich.abacus.action.Action;
import tel.schich.abacus.context.ContextBundle;
import tel.schich.abacus.decision.Decision;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        ContextBundle bundle = ContextBundle.create().with(subject, resource, action).with(context).build();

        // TODO order defs by fishes
        for (PolicyBundle defintion : defintions) {
            if (defintion.matches(bundle)) {
                Optional<Decision> matchingDecision = defintion.lookup(action).stream().findFirst();
                if (matchingDecision.isPresent()) {
                    return matchingDecision.get();
                }
            }
        }

        return UNDECIDED;
    }
}
