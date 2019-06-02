package tel.schich.abacus.example.domain;

import tel.schich.abacus.Resource;

public class Domain extends SimpleContext implements Resource {

    public Domain(String name) {
        this.map.put("name", name);
    }

    @Override
    public String contextType() {
        return "domain";
    }
}
