package tel.schich.abacus.context;

import java.util.Map;

public interface ContextExtractor<T> {
    String extractType(T obj);
    Map<String, String> extractAttributes(T obj);
}
