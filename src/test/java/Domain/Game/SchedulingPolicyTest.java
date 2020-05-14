package Domain.Game;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SchedulingPolicyTest {

    private SchedulingPolicy schedulingPolicy;

    @Before
    public void setUp() throws Exception {
        schedulingPolicy = SchedulingPolicy.getDefaultSchedulingPolicy();
    }

    @Test
    public void getDefaultSchedulingPolicyUTest() {
        assertTrue(schedulingPolicy.equals(2,2,2));
    }

}