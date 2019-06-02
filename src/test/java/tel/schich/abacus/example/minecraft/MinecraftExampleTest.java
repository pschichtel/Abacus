package tel.schich.abacus.example.minecraft;

import static tel.schich.abacus.context.ContextBundle.EMPTY;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tel.schich.abacus.PolicyBundle;
import tel.schich.abacus.Resource;
import tel.schich.abacus.Subject;
import tel.schich.abacus.action.SimpleAction;
import tel.schich.abacus.context.ContextBundle;
import tel.schich.abacus.decision.Decision;
import tel.schich.abacus.decision.DeniedDecision;
import tel.schich.abacus.decision.GrantedDecision;
import tel.schich.abacus.eval.UncachedPolicyEvaluator;
import tel.schich.abacus.matcher.AttributeValueMatcher;
import tel.schich.abacus.matcher.Matcher;

import java.util.Collections;

public class MinecraftExampleTest {

    public static final String GUEST = "Guest";
    public static final String MEMBER = "Member";
    public static final String ADMIN = "Admin";

    // Subjects
    private Subject guestSubject = new User(GUEST);
    private Subject userSubject = new User(MEMBER);
    private Subject adminSubject = new User(ADMIN);

    // Resources
    private Resource DUMMY = () -> Collections.emptyMap();

    // Actions
    private SimpleAction buildPermAction = new SimpleAction("perm.build");
    private SimpleAction prefixAction = new SimpleAction("prefix");

    // Evaluator
    private UncachedPolicyEvaluator evaluator;

    @BeforeEach
    void setUp() {

        /*
        role[name=Guest] {
            build.perm: deny
            prefix: "Gast"
        }

        role[name=Member] {
            prefix: "Mitglied"
        }

        role[name=Member] region[world=main] {
            build.perm: allow
        }

        role[name=Admin] {
            build.perm: allow
            prefix: "Admin"
        }
         */

        Matcher guestSelector = new AttributeValueMatcher("subject", "role", GUEST);
        Matcher memberSelector = new AttributeValueMatcher("subject", "role", MEMBER);
        Matcher memberInMainSelector = new AttributeValueMatcher("subject", "role", MEMBER)
                                  .and(new AttributeValueMatcher("subject", "world", "main"));
        Matcher adminSelector = new AttributeValueMatcher("subject", "role", ADMIN);

        PolicyBundle polDefGuest = PolicyBundle.define(guestSelector)
                .withPolicy(prefixAction, new MetaValue("GuestPrefix"))
                .withPolicy(buildPermAction, new DeniedDecision(null))
                .build();

        PolicyBundle polDefUser = PolicyBundle.define(memberSelector)
                .withPolicy(prefixAction, new MetaValue("MemberPrefix"))
                .build();

        PolicyBundle polDefUserInMain = PolicyBundle.define(memberInMainSelector)
                .withPolicy(buildPermAction, new GrantedDecision(null))
                .build();

        PolicyBundle polDefAdmin = PolicyBundle.define(adminSelector)
                .withPolicy(prefixAction, new MetaValue("AdminPrefix"))
                .withPolicy(buildPermAction, new GrantedDecision(null))
                .build();

        evaluator = new UncachedPolicyEvaluator()
                .addPolicies(polDefGuest)
                .addPolicies(polDefUser)
                .addPolicies(polDefUserInMain)
                .addPolicies(polDefAdmin);
    }

    @Test
    void testGuest() {
        Decision decision;

        decision = evaluator.decide(guestSubject, DUMMY, buildPermAction, EMPTY);
        Assert.assertTrue(decision instanceof DeniedDecision);

        decision = evaluator.decide(guestSubject, DUMMY, prefixAction, EMPTY);
        Assert.assertTrue(decision instanceof MetaValue && "GuestPrefix".equals(((MetaValue) decision).getValue()));
    }

    @Test
    void testUser() {
        Decision decision;

        decision = evaluator.decide(userSubject, DUMMY, buildPermAction, EMPTY);
        Assert.assertFalse(decision instanceof GrantedDecision);
        // TODO Member inheritance Assert.assertTrue(decision instanceof DeniedDecision);

        decision = evaluator.decide(userSubject, DUMMY, buildPermAction, ContextBundle.create().with("subject", "world", "main").build());
        Assert.assertTrue(decision instanceof GrantedDecision);

        decision = evaluator.decide(userSubject, DUMMY, prefixAction, EMPTY);
        Assert.assertTrue(decision instanceof MetaValue && "MemberPrefix".equals(((MetaValue) decision).getValue()));
    }

    @Test
    void testAdmin() {
        Decision decision;

        decision = evaluator.decide(adminSubject, DUMMY, buildPermAction, EMPTY);
        Assert.assertTrue(decision instanceof GrantedDecision);

        decision = evaluator.decide(adminSubject, DUMMY, prefixAction, EMPTY);
        Assert.assertTrue(decision instanceof MetaValue && "AdminPrefix".equals(((MetaValue) decision).getValue()));
    }

}
