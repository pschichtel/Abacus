package tel.schich.abacus.example.minecraft;

import tel.schich.abacus.Subject;
import tel.schich.abacus.example.SimpleContext;

public class User extends SimpleContext implements Subject {

    public User(String role) {
        map.put("role", role);
    }

}
