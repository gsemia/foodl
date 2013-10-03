package ch.baal.foodl.core;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Tests the main Foodl Class.
 * User: rolf
 */
public class FoodlTest {

    @Test
    public void foodl() {
        assertThat("shelby", is("shelby"));
    }
}
