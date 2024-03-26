import com.jieduixiangmu.MainActivity;
import com.jieduixiangmu.Number;
import com.jieduixiangmu.NumberUtil;
import org.junit.Test;

import java.util.List;

public class NumberUtilTest {
    @Test
    public void testSplitExpression() throws Exception {
        List<Number> numbers = NumberUtil.splitExpression("1+1*2-5");
        numbers.forEach(number -> {
            System.out.println(number);
        });
    }
    @Test
    public void testIsOperator() throws Exception {
        System.out.println(NumberUtil.isOperator(new char[]{'1', '+', '1'}, 1, 1));
    }
    @Test
    public void testForExpression() throws Exception {
        System.out.println(Number.forExpression("1'1/2"));
    }
    @Test
    public void testGetPostfix() throws Exception {
        List<Number> postfix = NumberUtil.getPostfix("1+1/2*4-6");
        postfix.forEach(p->{
            System.out.println(p);
        });
    }
    @Test
    public void testGetPostfix2() throws Exception {//8 + 3'4/9 * 5/8 / 1/6
        List<Number> numbers = NumberUtil.splitExpression("0*7+1/8-0/3");// 0 7 * 1 8 / + 0 / 3 -
//        numbers.forEach(number -> System.out.println(number));
        List<Number> postfix = NumberUtil.infix2Postfix(numbers);
        postfix.forEach(number -> System.out.println(number));
        Number calculate = NumberUtil.calculate(postfix);
//        List<Number> postfix = NumberUtil.getPostfix("8+3'4/9*5/8/1/6");
//        postfix.forEach(p->{//8 3'4/9 5/8 * 1/6 / +
//            System.out.println(p);
//        });
//        Number calculate = NumberUtil.calculate(postfix);
//        System.out.println(calculate);
    }
    @Test
    public  void testtt(){
    }
}
