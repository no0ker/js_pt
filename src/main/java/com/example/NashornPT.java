package com.example;

import jdk.nashorn.api.scripting.NashornScriptEngineFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.InputStreamReader;

public class NashornPT {
    private ScriptEngine nashorn;

    // many libraries / many init variables / etc
    public void init() throws ScriptException {
        NashornScriptEngineFactory nashornFactory = new NashornScriptEngineFactory();
        nashorn = nashornFactory.getScriptEngine();
        nashorn.eval(new InputStreamReader(this.getClass().getResourceAsStream("/underscore.js")));
    }

    // should by fast
    public int processRequest() throws ScriptException {
        return (int) nashorn.eval("_.random(0, 100)");
    }
}
