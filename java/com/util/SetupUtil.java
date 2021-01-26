package com.util;

import java.security.Provider.Service;
import java.util.Optional;
import com.google.common.collect.ImmutableMap;

/**
 * 
 * Util to handle configuration and fetching of setup information for microservices. Servers: Call
 * to get port number for server instantiation Client: Call to get target address for said server
 */

public class SetupUtil {

    public static int DEFAULT_SERVICE_PORT = 80;

    // All availabe services
    public enum AvailableServices {
        TASK
    }

    public static String getTarget(AvailableServices service) {
        switch (service) {
            case TASK:
                return "task";
        }
        return "someurl";
    }
}
