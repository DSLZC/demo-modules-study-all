package com.dslcode.spider.webmagic.jd.util;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Created by dongsilin on 2017/6/7.
 */
public class ScriptResolveUtil {

    public static ScriptEngine parse(String script) throws ScriptException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("JavaScript");
        scriptEngine.eval(script);
        return scriptEngine;
    }

}
