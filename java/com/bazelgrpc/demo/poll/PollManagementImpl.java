package com.bazelgrpc.demo.poll;

import io.grpc.stub.StreamObserver;
import com.bazelgrpc.demo.poll.PollManagementGrpc;
import com.bazelgrpc.demo.poll.PollServiceProto.GetPollRequest;
import com.bazelgrpc.demo.poll.PollServiceProto.GetPollResponse;

public class PollManagementImpl extends PollManagementGrpc.PollManagementImplBase {

  @Override
  public void getPolls(GetPollRequest req, StreamObserver<GetPollResponse> responseObserver) {
    System.out.println("getPolls");
    responseObserver
        .onNext(GetPollResponse.newBuilder().addPolls(PollUtil.createTestingPoll()).build());
    responseObserver.onCompleted();
  }

}

