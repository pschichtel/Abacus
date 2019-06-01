package tel.schich.abacus;

import tel.schich.abacus.context.Context;

public interface Resource extends Context {
    @Override
    default String contextType() {
        return "resource";
    }
}
