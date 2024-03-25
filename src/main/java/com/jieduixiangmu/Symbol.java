package com.jieduixiangmu;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Symbol {
    public Compute operation;
    public Symbol(Compute operation){
        this.operation=operation;
    }
    public static Symbol ADD=new Symbol((args)->{
        if(args==null||args.length!=2)return null;
        Integer i1 = Integer.valueOf(args[0].toString());
        Integer i2 = Integer.valueOf(args[1].toString());
        int i = i1 + i2;
        return Number.forExpression(String.valueOf(i));
    });
    public static Symbol SUB=new Symbol((args)->{
        if(args==null||args.length!=2)return null;
        Integer i1 = Integer.valueOf(args[0].toString());
        Integer i2 = Integer.valueOf(args[1].toString());
        int i = i1 - i2;
        return Number.forExpression(String.valueOf(i));
    });
    public static Symbol MUL=new Symbol((args)->{
        if(args==null||args.length!=2)return null;
        Integer i1 = Integer.valueOf(args[0].toString());
        Integer i2 = Integer.valueOf(args[1].toString());
        int i = i1 * i2;
        return Number.forExpression(String.valueOf(i));
    });
    public Number compute(Number...args){
        return this.operation.compute(args);
    }

}
@FunctionalInterface
interface Compute{
    public Number compute(Number...args);
}
