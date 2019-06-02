package tel.schich.abacus.example.domain;

import tel.schich.abacus.Subject;
import tel.schich.abacus.example.SimpleContext;

public class DomainOwnerSubject extends SimpleContext implements Subject {

    public DomainOwnerSubject(String role, String domains) {
        map.put("role", role);
        // TODO set
        map.put("domain", domains);
    }

}
