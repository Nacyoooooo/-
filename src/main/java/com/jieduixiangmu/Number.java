package com.jieduixiangmu;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 表示数字的类
 */
public class Number {
    public static final int UNEXIST=-1;//不存在
    public static final int INT=0;//整形
    public static final int PROPER_FRACTION=1;//真分数
    public static final int WITH_FRACTION =2;//带分数
    public static final int OPERATOR=3;//符号
    public static Number ERROR=new Number(null,UNEXIST);
    public static Map<String,Symbol> OPERATORS=new HashMap<>();
    static {
        OPERATORS.put("+",Symbol.ADD);
        OPERATORS.put("-",Symbol.SUB);
        OPERATORS.put("*",Symbol.MUL);
        OPERATORS.put("/",Symbol.DIV);
        OPERATORS.put("(",Symbol.LEFT_BRACKET);
        OPERATORS.put(")",Symbol.RIGHT_BRACKET);
        OPERATORS.put("'",Symbol.APOSTROPHE);
    }
    int type;//表示这个数字是整数，还是真分数，假分数
    String expression;//表示这个数的元数据，如1（整数）    1/2（真分数）  1 1/2（带分数）
    public Number(String expression,int type){
        this.expression=expression;
        this.type=type;
    }

    @Override
    public String toString() {
        return expression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Number number = (Number) o;
        return type == number.type && Objects.equals(expression, number.expression);
    }

    /**
     * 判断这个类是否合法
     * @return
     */
    public boolean isLegal(){
        if(expression==null||expression.isEmpty())return false;
        boolean legal=true;
        switch (type) {
            //操作符
            case OPERATOR -> {
                //如果是在定义的操作符里，则说明该ex是合法的操作符
                if(Number.OPERATORS.get(expression)!=null){
                    return true;
                }
            }
            //整数
            case INT -> {
                try {//使用Integer类的字符串强转数字，如果报错，说明该字符串本身就不代表数字
                    Integer.valueOf(expression);
                } catch (Exception e) {
                    legal=false;
                }
            }
            //真分数
            case PROPER_FRACTION -> {
                String[] split = expression.split("/");
                if(split.length!=2)legal=false;
                try {
                    //使用Integer类的字符串强转数字，如果报错，说明该字符串本身就不代表数字
                    if(Integer.valueOf(split[0])>=Integer.valueOf(split[1]))legal=false;
                }catch (Exception e){
                    legal=false;
                }
            }
            //带分数
            //TODO 带分数的处理逻辑仍需修改
            case WITH_FRACTION -> {
                String regex = "(\\d+)\\s(\\d+)/(\\d+)";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(expression);
                String[] split = new String[3];
                if (matcher.matches()) {
                    split[0] = matcher.group(1);
                    split[1] = matcher.group(2);
                    split[2] = matcher.group(3);
                }
                if(split.length!=3)legal=false;
                try {
                    //TODO 改成处理带分数是否合法的逻辑
                    Integer.valueOf(split[0]);
                    if(Integer.valueOf(split[1])>=Integer.valueOf(split[2]))legal=false;
                }catch (Exception e){
                    legal=false;
                }
            }
            //默认，如果都没匹配上，说明type不合法！
            default -> {
                legal=false;
            }
        }
        return legal;
    }

    /**
     * 工厂模式创建Number实例，以输入的表达式为元数据
     * @param expression
     * @return
     */
    public static final Number forExpression(String expression){
        //若为空，则返回错误
        if(expression==null||expression.isEmpty())return ERROR;
        //若操作符集中有该符号，则直接创建对象
        if(Number.OPERATORS.get(expression)!=null){
            return new Number(expression,OPERATOR);
        }
        //若能转化为整数，则说明该表达式本身就是整数
        try {
            Integer i = Integer.valueOf(expression);
            return new Number(expression,INT);
        }catch (Exception e){

        }
        //若能转化为整数，则说明该表达式本身就是真分数
        try {
            //由/分割，且只能分割为两个，否则错误
            String[] split = expression.split("/");
            if(split==null||split.length!=2)return ERROR;
            Integer left = 0;
            Integer right = 0;
            left = Integer.valueOf(split[0]);
            right = Integer.valueOf(split[1]);
            //左右两个数不能相等,左边必须小于右边
            if(left>=right)return ERROR;
            else return new Number(expression,PROPER_FRACTION);
        }catch (Exception e){

        }
        //TODO 此处处理带分数的逻辑
        //若能转化为整数，则说明该表达式本身就是带分数
        try {
            //通过'或者/分割表达式
            String[] split = expression.split("'|/");
            if(split.length!=3)return ERROR;
            //TODO 改成处理带分数是否合法的逻辑
            Integer.valueOf(split[0]);
            if(Integer.valueOf(split[1])>=Integer.valueOf(split[2]))return ERROR;
            return new Number(expression,WITH_FRACTION);
        }catch (Exception e){

        }
        return ERROR;
    }

}
