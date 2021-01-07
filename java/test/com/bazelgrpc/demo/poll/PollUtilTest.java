package com.bazelgrpc.demo.poll;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Before;
import com.bazelgrpc.demo.poll.PollProto.Poll;
import com.bazelgrpc.demo.poll.PollUtil;

/**
 * Poll Util Testing
 */
public class PollUtilTest {

    @Test
    public void createTestingPoll() throws Exception {
        Poll poll = PollUtil.createTestingPoll();
        assertEquals(poll.getPollId(), 1001);
    }

}
