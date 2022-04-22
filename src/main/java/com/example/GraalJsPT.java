package com.example;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.Source;

import javax.script.ScriptException;
import java.io.IOException;
import java.io.InputStreamReader;

public class GraalJsPT {
    private static Engine SHARED_ENGINE;
    private static Source SOURCE;

    // many libraries / many init variables / etc
    public void init() throws IOException {
        SHARED_ENGINE = Engine.newBuilder()
                .option("engine.WarnInterpreterOnly", "false")
                .build();
        SOURCE = Source.newBuilder(
                        "js",
                        new InputStreamReader(this.getClass().getResourceAsStream("/underscore.js")),
                        "underscore.js")
                .build();
    }

    // should by fast
    public int processRequest() throws ScriptException {
        try (Context js = Context.newBuilder("js")
                .engine(SHARED_ENGINE)
                .build()) {
            js.eval(SOURCE);
            return js.eval("js", "_.random(0, 100)").asInt();
        }
    }
}
