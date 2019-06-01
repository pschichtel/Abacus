package tel.schich.abacus.matcher.value;

import java.util.Objects;

public enum SimpleValueMatcher implements ValueMatcher {
    EQUALITY {
        @Override
        public boolean match(String left, String right) {
            return Objects.equals(left, right);
        }
    },
    EQUALITY_IGNORE_CASE {
        @Override
        public boolean match(String left, String right) {
            if (left == null) {
                return false;
            }
            return left.equalsIgnoreCase(right);
        }
    },
    STARTS_WITH {
        @Override
        public boolean match(String left, String right) {
            if (left == null) {
                return false;
            }
            return left.startsWith(right);
        }
    },
    ENDS_WITH {
        @Override
        public boolean match(String left, String right) {
            if (left == null) {
                return false;
            }
            return left.endsWith(right);
        }
    }
}
