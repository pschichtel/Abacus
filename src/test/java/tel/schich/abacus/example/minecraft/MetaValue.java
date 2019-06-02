package tel.schich.abacus.example.minecraft;

import tel.schich.abacus.decision.Decision;

public class MetaValue implements Decision {
    private String value;

    public MetaValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
