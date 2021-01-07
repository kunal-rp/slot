package com.bazelgrpc.demo.poll;

import java.util.List;
import com.bazelgrpc.demo.poll.PollProto.Poll;
import com.google.inject.Singleton;

@Singleton
public class MainPollHandler implements PollHandler {

    private PollClient client = new PollClient();

    @Override
    public List<Poll> getPolls() {
        return client.callGetPoll().getPollsList();
    }

}
