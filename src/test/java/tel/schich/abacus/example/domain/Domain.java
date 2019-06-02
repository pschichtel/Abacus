package tel.schich.abacus.example.domain;

import tel.schich.abacus.Resource;
import tel.schich.abacus.example.SimpleContext;

public class Domain extends SimpleContext implements Resource {

    public Domain(String name) {
        this.map.put("name", name);
    }

    @Override
    public String contextType() {
        return "domain";
    }
}
