package com.bazelgrpc.demo.services;

import com.google.inject.AbstractModule;
import com.bazelgrpc.demo.poll.PollHandler;
import com.bazelgrpc.demo.poll.MainPollHandler;

public class ServiceModule extends AbstractModule{

    @Override
    protected void configure() {
        bind(PollHandler.class).to(MainPollHandler.class);
    }
}
