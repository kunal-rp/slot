package com.util;

import com.google.inject.AbstractModule;
import com.task.db.TaskDBHandler;
import com.task.db.MainTaskDBHandler;

public class ServiceModule extends AbstractModule{

    @Override
    protected void configure() {
        bind(TaskDBHandler.class).to(MainTaskDBHandler.class);
    }
}