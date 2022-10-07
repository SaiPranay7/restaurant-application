package io.muserver.docs.samples;

import io.muserver.Method;
import io.muserver.MuServer;

import static io.muserver.MuServerBuilder.httpServer;

public class RequestExample {
    public static void main(String[] args) {
        MuServer server = httpServer()
            .addHandler(Method.POST, "/forms", new FormDataExampleHandler())
            .addHandler(Method.GET, "/search/{type}/{subtype}", new MatrixParameterExampleHandler())
            .start();
        System.out.println("Form data example (requires POST): " + server.uri().resolve("/forms"));
        System.out.println("Matrix parameter example: " + server.uri().resolve("/search/restaurent;date='26/10/2022'"));
    }
}
