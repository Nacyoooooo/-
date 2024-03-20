package com.jieduixiangmu;

import java.util.Objects;

/**
 * 表示数字的类
 */
public class Number {
    public static final int INT=0;
    public static final int PROPER_FRACTION=1;
    public static final int FACK_FRACTION=2;
    int type;//表示这个数字是整数，还是真分数，假分数
    String expression;//表示这个数的元数据，如1（整数）    1/2（真分数）  3/2（假分数）
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
                String[] split = expression.split("\\\\");
                if(split.length!=2)legal=false;
                try {
                    if(Integer.valueOf(split[0])>=Integer.valueOf(split[1]))legal=false;
                }catch (Exception e){
                    legal=false;
                }
            }
            case FACK_FRACTION -> {
                String[] split = expression.split("\\\\");
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

}
