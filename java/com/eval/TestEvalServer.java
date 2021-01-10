package com.eval;

/*  Eval Micro Service */
public class TestEvalServer {

    public static void main (String[] args){

        ScheduleHandler handler = new ScheduleHandler();
        System.out.println(handler.generateSchedule(101,111));

    }

}

