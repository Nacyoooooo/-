package com.jieduixiangmu;

import java.util.Objects;

/**
 * 表示数字的类
 */
public class Number {
    public static final int UNEXIST=-1;//不存在
    public static final int INT=0;//整形
    public static final int PROPER_FRACTION=1;//真分数
    public static final int WITH_FRACTION =2;//带分数
    public static Number ERROR=new Number(null,UNEXIST);
    int type;//表示这个数字是整数，还是真分数，假分数
    String expression;//表示这个数的元数据，如1（整数）    1/2（真分数）  1'1/2（带分数）
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
            case INT -> {
                try {
                    Integer.valueOf(expression);
                } catch (Exception e) {
                    legal=false;
                }
            }
            case PROPER_FRACTION -> {
                String[] split = expression.split("/");
                if(split.length!=2)legal=false;
                try {
                    if(Integer.valueOf(split[0])>=Integer.valueOf(split[1]))legal=false;
                }catch (Exception e){
                    legal=false;
                }
            }
            case WITH_FRACTION -> {
                String[] split = expression.split("/");
                if(split.length!=2)legal=false;
                try {
                    if(Integer.valueOf(split[01])>=Integer.valueOf(split[0]))legal=false;
                }catch (Exception e){
                    legal=false;
                }
            }
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
        if(expression==null||expression.isEmpty())return ERROR;
        try {
            Integer i = Integer.valueOf(expression);
            return new Number(expression,INT);
        }catch (Exception e){

        }
        String[] split = expression.split("/");
        if(split==null||split.length!=2)return ERROR;
        Integer left = 0;
        Integer right = 0;
        try {
            left = Integer.valueOf(split[0]);
            right = Integer.valueOf(split[1]);
        }catch (Exception e){
            return ERROR;
        }
        if(left==right)return ERROR;
        else if (left>right)return new Number(expression, WITH_FRACTION);
        else return new Number(expression,PROPER_FRACTION);
    }

}
