package tel.schich.abacus.action;

import tel.schich.abacus.context.Context;

public interface Action extends Context {

    String name();

    @Override
    default String contextType() {
        return "action";
    }
}
