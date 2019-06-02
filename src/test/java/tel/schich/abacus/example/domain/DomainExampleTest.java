package tel.schich.abacus.example.domain;

import static tel.schich.abacus.context.ContextBundle.EMPTY;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tel.schich.abacus.PolicyBundle;
import tel.schich.abacus.Resource;
import tel.schich.abacus.Subject;
import tel.schich.abacus.action.SimpleAction;
import tel.schich.abacus.decision.Decision;
import tel.schich.abacus.decision.GrantedDecision;
import tel.schich.abacus.eval.UncachedPolicyEvaluator;
import tel.schich.abacus.matcher.AttributeValueMatcher;
import tel.schich.abacus.matcher.AttributesContainsMatcher;
import tel.schich.abacus.matcher.Matcher;

public class DomainExampleTest {

    public static final String DOMAIN_SCHICH_TEL = "schich.tel";
    public static final String DOMAIN_BREH_ME = "breh.me";
    public static final String ACTION_DELETE = "delete";
    public static final String ACTION_MODIFY = "modify";
    public static final String SUBJECT_ROLE_USER = "User";
    public static final String SUBJECT_ROLE_ADMIN = "Admin";

    // Subjects
    private Subject userSubject = new DomainOwnerSubject(SUBJECT_ROLE_USER, DOMAIN_SCHICH_TEL);
    private Subject adminSubject = new DomainOwnerSubject(SUBJECT_ROLE_ADMIN, "*");

    // Resources
    private Resource domain1 = new Domain(DOMAIN_SCHICH_TEL);
    private Resource domain2 = new Domain(DOMAIN_BREH_ME);

    // Actions
    private SimpleAction deleteAction = new SimpleAction(ACTION_DELETE);
    private SimpleAction modifyAction = new SimpleAction(ACTION_MODIFY);

    // Evaluator
    private UncachedPolicyEvaluator evaluator;


    @BeforeEach
    void setUp() {

        Matcher selector = new AttributeValueMatcher("subject", "role", SUBJECT_ROLE_ADMIN);

        PolicyBundle polDefAdmin = PolicyBundle.define(selector)
                .withPolicy(modifyAction, new GrantedDecision(null))
                .build();

        selector = new AttributeValueMatcher("subject", "role", SUBJECT_ROLE_USER)
                .and(new AttributesContainsMatcher("subject", "domain", "domain", "name"));

        PolicyBundle polDefUser = PolicyBundle.define(selector)
                .withPolicy(deleteAction, new GrantedDecision(null))
                .withPolicy(modifyAction, new GrantedDecision(null))
                .build();

        evaluator = new UncachedPolicyEvaluator()
                .addPolicies(polDefAdmin).addPolicies(polDefUser);
    }

    @Test
    void testUser() {
        Decision decision;
        // Can user delete owned domain
        decision = evaluator.decide(userSubject, domain1, deleteAction, EMPTY);
        Assert.assertTrue(decision instanceof GrantedDecision);
        // Can user delete other domain
        decision = evaluator.decide(userSubject, domain2, deleteAction, EMPTY);
        Assert.assertFalse(decision instanceof GrantedDecision);

        // Can user modify owned domain
        decision = evaluator.decide(userSubject, domain1, modifyAction, EMPTY);
        Assert.assertTrue(decision instanceof GrantedDecision);
        // Can user modify other domain
        decision = evaluator.decide(userSubject, domain2, deleteAction, EMPTY);
        Assert.assertFalse(decision instanceof GrantedDecision);
    }

    @Test
    void testAdmin() {
        Decision decision;
        // Can admin delete any domain
        decision = evaluator.decide(adminSubject, domain1, deleteAction, EMPTY);
        Assert.assertFalse(decision instanceof GrantedDecision);
        // Can admin delete any domain
        decision = evaluator.decide(adminSubject, domain2, deleteAction, EMPTY);
        Assert.assertFalse(decision instanceof GrantedDecision);
        // Can admin modify domain
        decision = evaluator.decide(adminSubject, domain1, modifyAction, EMPTY);
        Assert.assertTrue(decision instanceof GrantedDecision);
        // Can admin modify domain
        decision = evaluator.decide(adminSubject, domain2, modifyAction, EMPTY);
        Assert.assertTrue(decision instanceof GrantedDecision);
    }

}
