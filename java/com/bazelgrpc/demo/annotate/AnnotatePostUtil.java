package com.bazelgrpc.demo.annotate;

import com.bazelgrpc.demo.annotate.AnnotateProto.AnnotatePost;
import java.io.IOException;

public class AnnotatePostUtil {

    public static AnnotatePost createTestingAnnotatePost() {
        return AnnotatePost.newBuilder().setAnnotatePostId(2001).setRegex("Google")
                .setText("This is an annoted post.").build();
    }
}
