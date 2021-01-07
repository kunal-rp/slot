package com.bazelgrpc.demo.annotate;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Before;
import com.bazelgrpc.demo.annotate.AnnotateProto.AnnotatePost;
import com.bazelgrpc.demo.annotate.AnnotatePostUtil;

/**
 * Annotate Post Util Testing
 */
public class AnnotateUtilTest {

    @Test
    public void createTestingAnnotatePost() throws Exception {
        AnnotatePost annotatePost = AnnotatePostUtil.createTestingAnnotatePost();
        assertEquals(annotatePost.getAnnotatePostId(), 2001);
    }

}
