package tel.schich.abacus;

import tel.schich.abacus.context.Context;

public interface Subject extends Context {
    @Override
    default String contextType() {
        return "subject";
    }
}
