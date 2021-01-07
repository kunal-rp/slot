package com.bazelgrpc.demo.poll;

import java.util.List;
import com.bazelgrpc.demo.poll.PollProto.Poll;

public interface PollHandler {

    public List<Poll> getPolls();

}
