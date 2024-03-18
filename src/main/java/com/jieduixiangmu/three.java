package com.jieduixiangmu;

import java.util.Scanner;

public class three {
    public static void main(String[] args) {
        /*
        Scanner scanner = new Scanner(System.in);
        System.out.println("计算ax^2+bx+c的根");
        System.out.println("请分别输入三个实数：");
        double a=scanner.nextDouble();
        double b=scanner.nextDouble();
        double c=scanner.nextDouble();
        double judgment = b*b-4*a*c;
        if (judgment>0) {
            double x1 = (-b + Math.sqrt(judgment)) / (2 * a);
            double x2 = (-b-Math.sqrt(judgment))/(2*a);
            System.out.println("方程的两根为"+x1+"与"+x2);
        } else if (judgment==0) {
            double x=-b/(2*a);
            System.out.println("方程有两个相同的实根："+x);
        }
        else
            System.out.println("方程无实数根");
        scanner.close();
        */
        /*
        System.out.println("计算n至少多大时，以下不等式成立。\n" + " 1+1/2+1/3+……+1/n>6");
        double sum=0;
        int n=1;
        while (sum<=6){
            sum+=1.0/n;
            n++;
        }
        System.out.println("n至少为"+(n-1)+"时，不等式成立");

         */
        /*
        System.out.println("有N个人参加100米短跑比赛。跑道为8条。程序的任务是按照尽量使每组的人数相差最少的原则分组。" +
                        "例如：N=8时，分成1组即可。N=9时，分成2组：一组5人，一组4人。" +
                        "要求从键盘输入一个正整数N。输出每个分组的人数。\n");

        Scanner scanner=new Scanner(System.in);
        System.out.println("请输入参加比赛的总人数：");
        int N=scanner.nextInt();
        int track=8;
        int baseCount = N / track;
        int extraCount = N % track;

        System.out.println("按照尽量使每组的人数相差最少的原则分组：");

        for (int i = 0; i < track; i++) {
            if (extraCount > 0) {
                System.out.println("第" + (i + 1) + "组：" + (baseCount + 1) + "人");
                extraCount--;
            } else {
                System.out.println("第" + (i + 1) + "组：" + baseCount + "人");
            }
        }

        scanner.close();

         */
        System.out.println("求s=a+aa+aaa+a...a的值，其中a是一个数字。\n" +
                "例如：2+22+222+2222（此时共有4个数相加），a 的值、加数个数n均从键盘获取。");

        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入一个数字 a：");
        int a = scanner.nextInt();
        System.out.print("请输入加数个数 n：");
        int n = scanner.nextInt();
        int current = a;
        int s = 0;
        for (int i = 0; i < n; i++) {
            s += current;
            current = current * 10 + a;
        }
        System.out.println("s 的值为：" + s);
        scanner.close();

    }
}
