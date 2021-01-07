package com.bazelgrpc.demo.poll;

import java.util.List;
import java.util.ArrayList;
import com.bazelgrpc.demo.poll.PollProto.Poll;
import com.google.inject.Singleton;

@Singleton
public class FakePollHandler implements PollHandler {

    private List<Poll> polls = new ArrayList();

    @Override
    public List<Poll> getPolls() {
        return polls;
    }

    public void addPoll(Poll poll) {
        polls.add(poll);
    }

    public void reset() {
        polls.clear();
    }

}
