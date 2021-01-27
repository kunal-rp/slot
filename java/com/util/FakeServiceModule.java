package com.util;

import com.google.inject.AbstractModule;
import com.task.db.TaskDBHandler;
import com.task.db.FakeTaskDBHandler;

public class FakeServiceModule extends AbstractModule{

    @Override
    protected void configure() {
        bind(TaskDBHandler.class).to(FakeTaskDBHandler.class);
    }
}