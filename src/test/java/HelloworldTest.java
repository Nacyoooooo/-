import com.jieduixiangmu.Number;
import org.junit.Test;

/**
 * 样板测试
 */
public class HelloworldTest {
    @Test
    public void test(){
        System.out.println("hello World Test");
    }
    @Test
    public void t(){
        Number number = Number.forExpression("+");
        System.out.println(number.isLegal());
    }
}
