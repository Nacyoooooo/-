package com.jieduixiangmu;

import java.util.*;

import static com.jieduixiangmu.Number.*;

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
                        if(getType(c1,true)!=NUMBER) break;
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
     * 将中缀表达式转化为后缀表达式
     * @param infix 中缀表达式
     * @return postfix 后缀表达式
     */
    public static List<Number> infix2Postfix(List<Number>infix){
        if(infix==null||infix.isEmpty())return new ArrayList<>();
        //要保存到的后缀表达式队列
        MyList<Number> postfix=new MyList<>();
        //操作符栈
        MyList<Number> operatorStack=new MyList<>();
        try {
            infix.forEach(number -> {
                switch (number.type){
                    //如果是数字，则入数字栈
                    case INT,WITH_FRACTION,PROPER_FRACTION->{
                        postfix.add(number);
                    }
                    //如果是运算符，则入运算符栈
                    case Number.OPERATOR -> {
                        Number last = operatorStack.tail();
                        if(last!=null){
                            operatorStack.push(last);
                        }
                        //如果栈顶为空，则直接入栈
                        if(last==null){
                            operatorStack.push(number);
                        }
                        //如果该元素为右括号，则直接全部出栈，直到遇到左括号
                        else if (number.expression.equals(")")) {
                            //只要非空就一致循环下去
                            while (!operatorStack.isEmpty()){
                                Number o = operatorStack.pop();
                                //如果遇到左括号，则停止循环
                                if(o.expression.equals("("))break;
                                //如果后缀表达式为空，则取两个
                                postfix.add(o);
                            }
                        }
                        //比较栈顶优先级
                        else if(getPriority(last)<getPriority(number)){
                            operatorStack.push(number);
                        }
                        //
                        else {
                            MyList<Number>queue=new MyList<>();
                            while (true){
                                Number head = operatorStack.tail();
                                if(head!=null)operatorStack.push(head);
                                //如果栈顶小于要入栈的元素，则直接入栈
                                if(getPriority(head)<getPriority(number)){
                                    operatorStack.push(number);
                                    while (!queue.isEmpty()){
                                        Number tail = queue.tail();
                                        postfix.add(tail);
                                    }
                                    break;
                                }
                                //
                                else {
                                    queue.add(operatorStack.tail());
                                }
                            }
                        }
                    }
                    //如果不存在，则报错
                    default -> {
                        return;
                    }
                }
            });
            operatorStack.forEach(number -> {
                postfix.add(number);
            });
        }catch (Exception e){

        }
        return postfix;
    }

    /**
     * 根据表达式获取后缀表达式
     * @param expression 算式表达式
     * @return 后缀表达式
     * @throws Exception 异常
     */
    public static List<Number> getPostfix(String expression) throws Exception {
        return infix2Postfix(splitExpression(expression));
    }

    /**
     *
     * @param c 单个字符
     * @return int
     */
    public static int getType(char c){
        if((c<='9'&&c>='0')||c=="'".toCharArray()[0])return NUMBER;
        else if(Number.OPERATORS.get(String.valueOf(c))!=null){
            return OPERATOR;
        }
        return ERROR;
    }
    public static int getType(char c,boolean isWith){
        if(isWith){
            if(c=='/')return NUMBER;
        }
        if((c<='9'&&c>='0')||c=="'".toCharArray()[0])return NUMBER;
        else if(Number.OPERATORS.get(String.valueOf(c))!=null){
            return OPERATOR;
        }
        return ERROR;
    }

    /**
     * 判断对应的操作符是否存在
     * @param data 原始数据
     * @param offset 初始偏移量
     * @param count 偏移量
     * @return boolean 是否为运算符
     */
    public static boolean isOperator(char[] data, int offset, int count){
        System.out.println(String.valueOf(data,offset,count));
        return Number.OPERATORS.get(String.valueOf(data,offset,count))!=null;
    }
    public static int getPriority(Number o1){
        if(o1==null)return -1;
        switch (o1.expression){
            case "+","-"-> {
                return 1;
            }
            case "*","/"-> {
                return 2;
            }
            case "(",")"->{
                return 100;
            }
        }
        return -1;
    }
    public static class MyList<T> extends LinkedList<T> implements List<T>{
        public T tail(){
            try {
                return  pop();
            }catch (Exception e){
                return null;
            }
        }
    }
}
