package com.jieduixiangmu;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static cn.hutool.core.util.RandomUtil.randomInt;


public class MainActivity {
    public static final int INT=0;
    public static final int PROPER_FRACTION=1;
    public static final int WITH_FRACTION=2;
    public static final int ADD=0;
    public static final int SUB=1;
    public static final int MUL=2;
    public static final int DIV=3;
    public static final int MAX=10;
    public static final int MIN=1;
    public static String getNumber(int max,int type){
        max=max<=0?MAX:max;
        type=type<0?0:(Math.min(type, 1));
        int i =  randomInt(0, 3);
        if(type==0||type==-1){
            i=INT;
        }
        switch (i){
            case INT -> {
                return String.valueOf(randomInt(MIN,MAX));
            }
            case PROPER_FRACTION -> {
                int left=randomInt(MIN,MAX);
                int right=randomInt(left,MAX);
                if(left>=right)return getNumber(max,type);
                else return String.format("%d/%d",left,right);
            }
            case WITH_FRACTION -> {
                int real=randomInt(MIN,MAX);
                int left=randomInt(MIN,MAX);
                int right=randomInt(left,MAX);
                if(left>=right)return getNumber(max,type);
                else return String.format("%d'%d/%d",real,left,right);
            }
        }
        return "1";
    }
    public static String getOperator(){
        int i =  randomInt(0, 4);
        switch (i){
            case ADD -> {
                return  "+";
            }
            case SUB -> {
                return  "-";
            }
            case MUL -> {
                return  "*";
            }
            case DIV -> {
                return  "/";
            }
        }
        return "+";
    }
    public static String getExpression(int max){
        max=max<=0?MAX:max;
        int i = randomInt(1, 4);
//        System.out.println(i);
        String expression="";
        int op=0;//0为添加数字，1为添加符号
        int sum=0;//记录添加了几个数字，sum必须永远比i多1
        int opera=-1;//保证第一个数一定是整数！
        while (sum!=i+1){
            if(op==0){
                expression+=getNumber(max,opera);
                sum++;
                op=1;
            }else {
                String operator = getOperator();
                switch (operator){
                    case "+","-"->{
                        opera=1;
                    }
                    case "*","/"->{
                        opera=0;
                    }
                }
                expression+=operator;
                op=0;
            }
        }
        return expression;
    }
    public static void run(String[]args) throws Exception {
        String Exercises="Exercises.txt";
        String Answers="Answers.txt";
        String exerciseFile="";
        String answerFile="";
        boolean isEx=false;
        boolean isAn=false;
        int sum=5;
        int max=10;
        for (int i = 0; i < args.length; i++) {
            if(args[i].equals("-e")){
                exerciseFile=args[i+1];
                isEx=true;
            }
            if(args[i].equals("-a")){
                answerFile=args[i+1];
                isAn=true;
            }
            if(args[i].equals("-n")){
                sum=Integer.valueOf(args[i+1]);
            }
            if(args[i].equals("-r")){
                max=Integer.valueOf(args[i+1]);
            }
        }
        List<String>questions=new ArrayList<>();
        List<String>answers=new ArrayList<>();
        for (int i = 0; i < sum; i++) {
            String expression = getExpression(max);
            List<Number> postfix = NumberUtil.getPostfix(expression);
//            System.out.println(expression);
            Number calculate = NumberUtil.calculate(postfix);
            questions.add(expression);
            answers.add(calculate.expression);
        }
        for (int i = 0; i < questions.size(); i++) {
            String expression = questions.get(i);
            String answer = answers.get(i);
            String text=String.format("%d.%s=\n",i+1,expression);
            String answerText = String.format("%d.%s\n", i + 1, answer);
            FileUtilss.append(Exercises,text);
            FileUtilss.append(Answers,answerText);
        }
        if(isEx&&isAn){
            String read = FileUtilss.read(exerciseFile);
            String answersssss = FileUtilss.read(answerFile);
            String[] answr = answersssss.split("\n");
            String[] split = read.split("=\r\n");
            String Correct="(";
            String Wrong="(";
            List<String>cor=new ArrayList<>();
            List<String>wro=new ArrayList<>();
            for (int i = 0; i < split.length; i++) {
                if(split[i].equals(""))continue;
                String s = split[i];
                String[] split1 = s.split("\\.");
                Number calculate = NumberUtil.calculate(NumberUtil.getPostfix(split1[1]));
                if(answr[i].split("\\.")[1].equals(calculate.toString())){
                    cor.add(split1[0]);
                }else {
                    wro.add(split1[0]);
                }
            }
            for (int i = 0; i < cor.size(); i++) {
                Correct+=cor.get(i);
                if(i!=cor.size()-1){
                    Correct+=",";
                }else {
                    Correct+=")";
                }
            }Correct=cor.size()+Correct;
            if(cor.isEmpty()){
                Correct="0()";
            }

            for (int i = 0; i < wro.size(); i++) {
                Wrong+=wro.get(i);
                if(i!=wro.size()-1){
                    Wrong+=",";
                }else {
                    Wrong+=")";
                }
            }Wrong=wro.size()+Wrong;
            if(wro.isEmpty()){
                Wrong="0()";
            }

            FileUtilss.append("Grade.txt",String.format("Correct:%s\nWrong:%s\n",Correct,Wrong));
        }
    }
    public static void main(String[] args) {
        try {
            run(args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

