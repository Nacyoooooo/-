package com.jieduixiangmu;

import org.apache.commons.lang3.math.Fraction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.jieduixiangmu.Number.*;

public class Symbol {
    public Compute operation;

    public Symbol(Compute operation) {
        this.operation = operation;
    }

    //加号+ 1'1/2
    public static Symbol ADD = new Symbol((args) -> {
        if (args == null || args.length != 2) return null;
        String arr1 = args[0].toString();
        String arr2 = args[1].toString();
        arr1 = arr1.replaceAll("'", " ");
        arr2 = arr2.replaceAll("'", " ");
        Fraction n1 = Fraction.getFraction(arr1);
        Fraction n2 = Fraction.getFraction(arr2);
        Fraction sum = n1.add(n2);
        int i = 0;
        String out;
        if (sum.getDenominator() == 1) {
            i = sum.getProperWhole();
            return Number.forExpression(String.valueOf(i));
        } else {
            out = sum.toProperString();
            out = out.replaceAll(" ", "'");
            return Number.forExpression(out);
        }
    });
    //减号-
    public static Symbol SUB = new Symbol((args) -> {
        if (args == null || args.length != 2) return null;
        String arr1 = args[0].toString();
        String arr2 = args[1].toString();
        arr1 = arr1.replaceAll("'", " ");
        arr2 = arr2.replaceAll("'", " ");
        Fraction n1 = Fraction.getFraction(arr1);
        Fraction n2 = Fraction.getFraction(arr2);
        Fraction sum = n1.subtract(n2);
        int i = 0;
        String out;
        if (sum.getDenominator() == 1) {
            i = sum.getProperWhole();
            return Number.forExpression(String.valueOf(i));
        } else {
            out = sum.toProperString();
            out = out.replaceAll(" ", "'");
            return Number.forExpression(out);
        }
    });
    //乘号*
    public static Symbol MUL = new Symbol((args) -> {
        if (args == null || args.length != 2) return null;
        String arr1 = args[0].toString();
        String arr2 = args[1].toString();
        arr1 = arr1.replaceAll("'", " ");
        arr2 = arr2.replaceAll("'", " ");
        Fraction n1 = Fraction.getFraction(arr1);
        Fraction n2 = Fraction.getFraction(arr2);
        Fraction sum = n1.multiplyBy(n2);
        int i = 0;
        String out;
        if (sum.getDenominator() == 1) {
            i = sum.getProperWhole();
            return Number.forExpression(String.valueOf(i));
        } else {
            out = sum.toProperString();
            out = out.replaceAll(" ", "'");
            return Number.forExpression(out);
        }
    });
    //除号/
    public static Symbol DIV = new Symbol(args -> {
        if (args == null || args.length != 2) return null;
        String arr1 = args[0].toString();
        String arr2 = args[1].toString();
        arr1 = arr1.replaceAll("'", " ");
        arr2 = arr2.replaceAll("'", " ");
        Fraction n1 = Fraction.getFraction(arr1);
        Fraction n2 = Fraction.getFraction(arr2);
        if (n2.equals(Fraction.ZERO))return ERROR;
        Fraction sum = n1.divideBy(n2);
        int i =0;
        String out ;
        if(sum.getDenominator()==1){
            i = sum.getProperWhole();
            return Number.forExpression(String.valueOf(i));
        }
        else {
            out= sum.toProperString();
            out = out.replaceAll(" ", "'");
            return Number.forExpression(out);
        }
    });
    //左括号
    public static Symbol LEFT_BRACKET = new Symbol(args -> {
        return Number.forExpression("(");
    });
    //右括号
    public static Symbol RIGHT_BRACKET = new Symbol(args -> {
        return Number.forExpression(")");
    });
    //撇号'
    public static Symbol APOSTROPHE = new Symbol(args -> {
        return Number.forExpression("‘");
    });

    public Number compute(Number... args) {
        return this.operation.compute(args);
    }

}

@FunctionalInterface
interface Compute {
    public Number compute(Number... args);
}
