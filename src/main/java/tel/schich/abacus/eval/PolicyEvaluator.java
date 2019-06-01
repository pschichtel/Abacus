package tel.schich.abacus.eval;

import tel.schich.abacus.Resource;
import tel.schich.abacus.Subject;
import tel.schich.abacus.action.Action;
import tel.schich.abacus.context.ContextBundle;
import tel.schich.abacus.decision.Decision;

import static tel.schich.abacus.context.ContextBundle.EMPTY;

public interface PolicyEvaluator {
    default Decision decide(Subject subject, Resource resource, Action action) {
        return decide(subject, resource, action, EMPTY);
    }

    Decision decide(Subject subject, Resource resource, Action action, ContextBundle context);
}
