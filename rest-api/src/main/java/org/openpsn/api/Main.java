package org.openpsn.api;

import io.jooby.Jooby;

public class Main {
    public static void main(String[] args) {
        Jooby.runApp(args, ApiApplication.class);
    }
}
