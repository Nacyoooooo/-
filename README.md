##项目信息
项目人员：
|姓名|学号|
|---------|-------------|
|谢建豪|3122004793|
|陈志豪|3122004778|

| 这个作业属于哪个课程 |[软件工程2024-广东工业大学](https://edu.cnblogs.com/campus/gdgy/SoftwareEngineering2024)         |
| :---------- | :---------------------------------- |
| 这个作业要求在哪里  |[结对项目](https://edu.cnblogs.com/campus/gdgy/SoftwareEngineering2024/homework/13137)  |
| 这个作业的目标    |学习并应用两人合作项目的过程和方法|
| GitHub地址   | [GitHub地址](https://github.com/Nacyoooooo/jieduixiangmu) |

##开发环境
| 开发工具 | IntelliJ IDEA 2023.2.1                                |
| ---- | ----------------------------------------------------- |
| 编程语言 | java                                                  |
| 运行环境 | Java(TM) SE Runtime Environment Oracle GraalVM 21.0.2+13.1 (build 21.0.2+13-LTS-jvmci-23.1-b30) |
| 构建工具 | maven                                                 |
| 编译环境 | java version "21.0.2"                              |

##需求分析
设计一个支持小学范围内的四则运算题目生成器，并且可以生成答案和进行答案的校对，要求可以通过命令行或者图像界面进行交互。

##效能分析
![](https://img2024.cnblogs.com/blog/3397538/202403/3397538-20240326143909481-409260993.png)
![](https://img2024.cnblogs.com/blog/3397538/202403/3397538-20240326143927823-957392281.png)



##设计实现过程
设计了Number类来存放整数和分数并判断其规范性，设计Symbol类来进行四则运算使其能进行整数与分数的混合运算，设置FileUtilss来进行文件操作，设置NumberUtil用来对多项运算表达式使用后缀表达式进行处理

##代码说明
Number类
```
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
    public static final int FAKE_FRACTION=4;//假分数
    public static Number ERROR=new Number(null,UNEXIST);
    public static Number FAKE=new Number(null,FAKE_FRACTION);
    public static Map<String,Symbol> OPERATORS=new HashMap<>();
    static {
        OPERATORS.put("+",Symbol.ADD);
        OPERATORS.put("-",Symbol.SUB);
        OPERATORS.put("*",Symbol.MUL);
        OPERATORS.put("/",Symbol.DIV);
        OPERATORS.put("(",Symbol.LEFT_BRACKET);
        OPERATORS.put(")",Symbol.RIGHT_BRACKET);
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
            if(left>=right)return new Number(expression,FAKE_FRACTION);
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
```

Symbol类

```
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
    public static void p(Number...args){
//        for (Number arg : args) {
//            System.out.println(arg);
//        }
    }

    //加号+ 1'1/2
    public static Symbol ADD = new Symbol((args) -> {
        p(args);
        if (args == null || args.length != 2) return ERROR;
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
        p(args);
        if (args == null || args.length != 2) return ERROR;
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
        p(args);
        if (args == null || args.length != 2) return ERROR;
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
        p(args);
        if (args == null || args.length != 2) return ERROR;
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

```

NumberUtil类

```
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
                    Integer num=0;
                    boolean isWith=false;
                    for (j = i+1; j < charArray.length; j++) {
                        //如果不是数字则终止循环
                        char c1=charArray[j];
                        if("'".equals(String.valueOf(c1))){
                            isWith=true;
                        }
                        if(getType(c1,isWith,num)!=NUMBER) {
                            break;
                        }else {
                            if(c1=='/'){
                                num++;
                            }
                        }
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
    public static int getType(char c,boolean isWith,Integer num){
        if(isWith&&num==0){
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

    /**
     *
     * @param postFix
     * @return
     */
    public static Number calculate(List<Number> postFix){
        if(postFix==null||postFix.isEmpty()){
            return Number.ERROR;
        }
        MyList<Number>numbers=new MyList<>();
        postFix.forEach(number -> {
            switch (number.type){
                case Number.OPERATOR -> {
                    Number n1 = numbers.tail();
                    Number n2 = numbers.tail();
                    Symbol symbol = OPERATORS.get(number.expression);
                    Number compute = symbol.compute(n2, n1);
                    numbers.push(compute);
                }
                default -> {
                    numbers.push(number);
                }
            }
        });
        return numbers.tail();
    }
}

```

主函数

```
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

```

##测试运行
测试生成题目
![](https://img2024.cnblogs.com/blog/3397538/202403/3397538-20240326144334255-1234387532.png)
![](https://img2024.cnblogs.com/blog/3397538/202403/3397538-20240326144346642-1414713053.png)


##PSP表格
| PSP2.1                                  | Personal Software Process Stages | 预估耗时（分钟） | 实际耗时（分钟） |
| --------------------------------------- | -------------------------------- | :--------: | :--------: |
| Planning                                | 计划                               |          |          |
| · Estimate                              | · 估计这个任务需要多少时间                   | 10       | 30       |
| Development                             | 开发                               |          |          |
| · Analysis                              | · 需求分析 (包括学习新技术)                 | 300      | 120      |
| · Design Spec                           | · 生成设计文档                         | 240      | 300      |
| · Design Review                         | · 设计复审 (和同事审核设计文档)               | 120      | 180      |
| · Coding Standard                       | · 代码规范 (为目前的开发制定合适的规范)           | 60       | 60       |
| · Design                                | · 具体设计                           | 180      | 240      |
| · Coding                                | · 具体编码                           | 480      | 600      |
| · Code Review                           | · 代码复审                           | 60       | 60       |
| · Test                                  | · 测试（自我测试，修改代码，提交修改）             | 120      | 180       |
| Reporting                               | 报告                               |          |          |
| · Test Report                           | · 测试报告                           | 60       | 60       |
| · Size Measurement                      | · 计算工作量                          | 60       | 30       |
| · Postmortem & Process Improvement Plan | · 事后总结, 并提出过程改进计划                | 60       | 30       |
| 合计                                      |                                  | 1750     | 1890     |

##项目小结
两人合作项目会一起产生很多对解决问题和实现功能的想法，通过充分的沟通和一些工具的利用提升了合作完成项目的效率。
每个人都能从对方那里学习到一些新的想法与思路，在讨论后将更有效率的实现想法。
在这次结对项目中，我们互相学习、互相支持，共同成长。通过合作，我们更加深入地了解了彼此的工作方式和思维模式，建立了良好的合作关系。
