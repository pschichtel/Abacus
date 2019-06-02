package tel.schich.abacus.action;

import tel.schich.abacus.context.Context;
import tel.schich.abacus.decision.Decision;

public interface Action extends Context {

    String name();

    @Override
    default String contextType() {
        return "action";
    }

    boolean accepts(Decision decision);
}
