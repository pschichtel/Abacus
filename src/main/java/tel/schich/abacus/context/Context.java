package tel.schich.abacus.context;

import java.util.Map;

public interface Context {
    String contextType();
    Map<String, String> contextAttributes();
}
