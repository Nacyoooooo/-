package com.jieduixiangmu;

public class Symbol {
    public Compute operation;
    public Symbol(Compute operation){
        this.operation=operation;
    }
    public static Symbol ADD=new Symbol((args)->{
        if(args==null||args.length==0)return null;

        return Number.ERROR;
    });
    public Number compute(Number...args){
        return this.operation.compute(args);
    }

}
@FunctionalInterface
interface Compute{
    public Number compute(Number...args);
}
