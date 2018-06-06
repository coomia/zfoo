package expression.js;

import org.testng.annotations.Test;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Mozilla Rhino是一个完全使用Java语言编写的开源JavaScript引擎实现,主要用于在java环境中执行xxx.js或者js程序。
 * Rhino通常用于在Java程序中，为最终用户提供脚本化能力。
 * Rhino（犀牛）这个名字来源于O'Reilly出版的著名的“犀牛书”JavaScript:The Definitive Guide（中文译名：JavaScript权威指南）。
 * Rhino 项目可以追溯到 1997 年，当时Netscape计划开发一个纯Java实现的Navigator，为此需要一个Java实现的JavaScript —— Javagator。
 * 它也就是Rhino的前身。起初Rhino将JavaScript编译成Java的二进制代码执行，这样它会有最好的性能。
 * 后来由于编译执行的方式存在垃圾收集的问题并且编译和装载过程的开销过大，不能满足一些项目的需求，Rhino提供了解释执行的方式。
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-12-21 11:55
 */
public class ScriptFunTest {

    @Test
    public void testFun() throws ScriptException, NoSuchMethodException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        String scriptText = "function sum(a,b) { return a+b; } ";
        engine.eval(scriptText);
        Invocable invocable = (Invocable) engine;
        Object result = invocable.invokeFunction("sum", 99, 99);
        System.out.println(result);
    }

    @Test
    public void testFun1() throws ScriptException, NoSuchMethodException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        String scriptText = "function sum(a,b) { return a+b; } ";
        engine.eval(scriptText);
        Invocable invocable = (Invocable) engine;
        Object result = invocable.invokeFunction("sum", "hello ", "world!");
        System.out.println(result);
    }

}
