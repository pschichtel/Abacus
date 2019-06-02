package tel.schich.abacus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import tel.schich.abacus.action.Action;
import tel.schich.abacus.context.ContextBundle;
import tel.schich.abacus.decision.Decision;
import tel.schich.abacus.matcher.Matcher;

import static java.util.Collections.emptyList;

public class PolicyBundle {
    private final Matcher selector;
    private final Map<String, List<Decision>> decisionMap;

    private PolicyBundle(Matcher selector, Map<String, List<Decision>> decisionMap) {
        this.selector = selector;
        this.decisionMap = decisionMap;
    }

    public boolean matches(ContextBundle context) {
        return selector.match(context);
    }

    public List<Decision> lookup(Action action) {
        return this.decisionMap.getOrDefault(action.name(), emptyList()).stream()
                .filter(action::accepts)
                .collect(Collectors.toList());
    }

    public static PolicyDefinitionBuilder define(Matcher matcher) {
        return new PolicyDefinitionBuilder(matcher);
    }

    public static class PolicyDefinitionBuilder {
        private final Matcher matcher;
        private final Map<String, List<Decision>> decisionMap;

        private PolicyDefinitionBuilder(Matcher matcher) {
            this.matcher = matcher;
            this.decisionMap = new HashMap<>();
        }

        public PolicyDefinitionBuilder withPolicy(Action action, Decision decision) {
            this.decisionMap.computeIfAbsent(action.name(), a -> new ArrayList<>()).add(decision);
            return this;
        }

        public PolicyBundle build() {
            return new PolicyBundle(matcher, Collections.unmodifiableMap(this.decisionMap));
        }
    }
}
