package com.jieduixiangmu;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Symbol {
    public Compute operation;
    public Symbol(Compute operation){
        this.operation=operation;
    }
    //加号+
    public static Symbol ADD=new Symbol((args)->{
        if(args==null||args.length!=2)return null;
        Integer i1 = Integer.valueOf(args[0].toString());
        Integer i2 = Integer.valueOf(args[1].toString());
        int i = i1 + i2;
        return Number.forExpression(String.valueOf(i));
    });
    //减号-
    public static Symbol SUB=new Symbol((args)->{
        if(args==null||args.length!=2)return null;
        Integer i1 = Integer.valueOf(args[0].toString());
        Integer i2 = Integer.valueOf(args[1].toString());
        int i = i1 - i2;
        return Number.forExpression(String.valueOf(i));
    });
    //乘号*
    public static Symbol MUL=new Symbol((args)->{
        if(args==null||args.length!=2)return null;
        Integer i1 = Integer.valueOf(args[0].toString());
        Integer i2 = Integer.valueOf(args[1].toString());
        int i = i1 * i2;
        return Number.forExpression(String.valueOf(i));
    });
    //除号/
    public static Symbol DIV=new Symbol(args -> {
        if(args==null||args.length!=2)return null;
        Integer i1 = Integer.valueOf(args[0].toString());
        Integer i2 = Integer.valueOf(args[1].toString());
        //禁止除零
        if(i2.equals(0))return Number.ERROR;
        int i = i1 / i2;
        return Number.forExpression(String.valueOf(i));
    });
    //左括号
    public static Symbol LEFT_BRACKET=new Symbol(args -> {
        return Number.forExpression("(");
    });
    //右括号
    public static Symbol RIGHT_BRACKET=new Symbol(args -> {
        return Number.forExpression(")");
    });
    //撇号'
    public static Symbol APOSTROPHE=new Symbol(args -> {
        return Number.forExpression("‘");
    });
    public Number compute(Number...args){
        return this.operation.compute(args);
    }

}
@FunctionalInterface
interface Compute{
    public Number compute(Number...args);
}
