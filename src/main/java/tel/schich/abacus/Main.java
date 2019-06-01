package tel.schich.abacus;

import java.util.Map;

import tel.schich.abacus.action.SimpleAction;
import tel.schich.abacus.context.ContextBundle;
import tel.schich.abacus.context.ContextExtractor;
import tel.schich.abacus.decision.GrantedDecision;
import tel.schich.abacus.example.Role;
import tel.schich.abacus.matcher.AttributeValueMatcher;
import tel.schich.abacus.matcher.Matcher;

import static java.util.Collections.singletonMap;

public class Main {

    public static void main(String[] args) {

        Role admin = new Role("Admin");
        Matcher selector = new AttributeValueMatcher("role", "name", "Admin").and(new AttributeValueMatcher("domain", "name", "schich.tel"));

        ContextExtractor<String> domainExtractor = new ContextExtractor<String>() {
            @Override
            public String extractType(String obj) {
                return "domain";
            }

            @Override
            public Map<String, String> extractAttributes(String obj) {
                return singletonMap("name", obj);
            }
        };

        ContextBundle context = ContextBundle.create()
                .with(admin)
                .with("schich.tel", domainExtractor)
                .build();

        PolicyBundle polDef = PolicyBundle.define(selector)
                .withPolicy(new SimpleAction("delete"), new GrantedDecision(null))
                .build();

        System.out.println(selector.match(context));


//        role[name=Admin] {
//            cubeengine.world.delete.world: deny;
//            cubeengine.world.delete: grant;
//            cubeengine.world.delete: "meta";
//            cubeengine.world.delete: grant;
//            cubeengine.world.delete: "grant";
//        }

    }
}
