package tel.schich.abacus.context;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.emptyMap;
import static java.util.Collections.emptySet;

public class ContextBundle {

    public static final ContextBundle EMPTY = new ContextBundle(Collections.emptyMap());

    private final Map<String, Map<String, Set<String>>> attributes;

    private ContextBundle(Map<String, Map<String, Set<String>>> attributes) {
        this.attributes = attributes;
    }

    public Set<String> lookup(String typeName, String attributeName) {
        return this.attributes
                .getOrDefault(typeName.toLowerCase(), emptyMap())
                .getOrDefault(attributeName.toLowerCase(), emptySet());
    }

    public boolean has(String typeName) {
        return this.attributes.containsKey(typeName.toLowerCase());
    }

    public boolean has(String typeName, String attributeName) {
        return lookup(typeName, attributeName).isEmpty();
    }

    public boolean contains(String typeName, String attributeName, String value) {
        return lookup(typeName, attributeName).contains(value);
    }

    public static ContextBundleBuilder create() {
        return new ContextBundleBuilder();
    }

    public static class ContextBundleBuilder {
        private Map<String, Map<String, Set<String>>> attributes;

        private ContextBundleBuilder() {
            this.attributes = new HashMap<>();
        }

        public ContextBundleBuilder with(Context... contexts) {
            for (Context context : contexts) {
                String typeName = context.contextType();
                Map<String, String> attributes = context.contextAttributes();

                with(typeName, attributes);
            }
            return this;
        }

        public ContextBundleBuilder with(ContextBundle other) {
            other.attributes.forEach((key, otherMap) -> {
                Map<String, Set<String>> map = this.attributes.computeIfAbsent(key.toLowerCase(), k -> new HashMap<>());
                otherMap.forEach((name, values) -> map.computeIfAbsent(name.toLowerCase(), n -> new HashSet<>()).addAll(values));
            });
            return this;
        }

        public <T> ContextBundleBuilder with(T obj, ContextExtractor<T> extractor) {
            String typeName = extractor.extractType(obj);
            Map<String, String> attributes = extractor.extractAttributes(obj);

            return with(typeName, attributes);
        }

        public ContextBundleBuilder with(String typeName, Map<String, String> attributes) {
            Map<String, Set<String>> typeAttributes = this.attributes.computeIfAbsent(typeName, key -> new HashMap<>());
            attributes.forEach((name, value) -> {
                typeAttributes.computeIfAbsent(name, n -> new HashSet<>()).add(value);
            });
            return this;
        }

        public ContextBundleBuilder with(String typeName, String attributeName, String value) {
            this.attributes
                    .computeIfAbsent(typeName.toLowerCase(), key -> new HashMap<>())
                    .computeIfAbsent(attributeName.toLowerCase(), n -> new HashSet<>())
                    .add(value);

            return this;
        }

        public ContextBundle build() {
            this.attributes.forEach((typeName, typeAttributes) -> {
                for (Map.Entry<String, Set<String>> entry : typeAttributes.entrySet()) {
                    entry.setValue(Collections.unmodifiableSet(entry.getValue()));
                }
            });

            ContextBundle out = new ContextBundle(this.attributes);
            this.attributes = null;
            return out;
        }
    }
}
