package com.bazelgrpc.demo.poll;

import com.bazelgrpc.demo.poll.PollProto.Poll;
import com.bazelgrpc.demo.poll.PollProto.PollQuestion;
import com.bazelgrpc.demo.poll.PollProto.RadioSelection;
import com.bazelgrpc.demo.poll.PollProto.RadioOption;

import java.io.IOException;

public class PollUtil {

        private static final RadioOption YES_RADIO_OPTION =
                        RadioOption.newBuilder().setAnswerId(1).setText("yes").build();
        private static final RadioOption NO_RADIO_OPTION =
                        RadioOption.newBuilder().setAnswerId(2).setText("no").build();

        public static Poll createTestingPoll() {
                return Poll.newBuilder().setPollId(1001).addQuestions(PollQuestion.newBuilder()
                                .setPromptText("This is the first question, ok??? ")
                                .setRadio(RadioSelection.newBuilder().addOptions(YES_RADIO_OPTION)
                                                .addOptions(NO_RADIO_OPTION).build())
                                .build()).build();
        }
}
