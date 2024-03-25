package com.jieduixiangmu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 本类用于实现基于Number类的后缀表达式遍历
 */
public class NumberUtil {
    public static final int NUMBER=1;
    public static final int OPERATOR=2;
    public static final int ERROR=3;
    /**
     * 根据规律切割字符串并构建以Number为基础的中缀表达式，用list存储
     * @param expression
     */
    public static List<Number> splitExpression(String expression) throws Exception {
        if(expression==null||expression.isEmpty()){
            return new ArrayList<>();
        }
        List<Number>inFix=new ArrayList<>();
        //获取字符
        char[] charArray = expression.toCharArray();
        //遍历
        for (int i = 0; i < charArray.length; i++) {
            char c=charArray[i];
            //判断该字符是什么类型
            switch (getType(c)){
                //数字
                case NUMBER -> {
                    //判断是否是连续的数字字符，用于判断两位数以上的数字，如22
                    int j=0;
                    for (j = i+1; j < charArray.length; j++) {
                        //如果不是数字则终止循环
                        char c1=charArray[j];
                        if(getType(c1)!=NUMBER) break;
                        //下一个是数字则继续
                    }
                    inFix.add(Number.forExpression(
                            String.valueOf(charArray,//原数据
                                    i,//起始索引
                                    j-i//索引的范围
                            ))
                    );
                    i=j-1;
                }
                //操作符
                case OPERATOR -> {
                    //判断是否是连续的数字字符，用于判断两位数以上的数字，如22
                    int j=0;
                    for (j = i+1; j < charArray.length; j++) {
                        //如果不是操作符则终止循环
                        char c1=charArray[j];
                        if(getType(c1)!=OPERATOR) break;
                        //下一个是数字则继续
                    }
                    inFix.add(Number.forExpression(
                            String.valueOf(charArray,//原数据
                                    i,//起始索引
                                    j-i//索引的范围
                            ))
                    );
                    i=j-1;
                }
                //两不
                case ERROR -> throw new Exception("既不是数字也不是操作符！");
            }
        }
        return inFix;
    }

    /**
     *
     * @param c 单个字符
     * @return int
     */
    public static int getType(char c){
        if(c<='9'&&c>='0')return NUMBER;
        else if(Number.OPERATORS.get(String.valueOf(c))!=null){
            return OPERATOR;
        }
        return ERROR;
    }

    /**
     * 判断对应的操作符是否存在
     * @param data
     * @param offset
     * @param count
     * @return
     */
    public static boolean isOperator(char[] data, int offset, int count){
        System.out.println(String.valueOf(data,offset,count));
        return Number.OPERATORS.get(String.valueOf(data,offset,count))!=null;
    }
}
