import com.jieduixiangmu.FileUtilss;
import org.junit.Test;

public class FileUtilssTest {
    @Test
    public void testAppend(){
        System.out.println(FileUtilss.append("./test.txt", "w"));
    }
    @Test
    public void testRead(){
        System.out.println(FileUtilss.read("D:\\code\\java\\jieduixiangmu\\src\\main\\resources\\test.txt"));
    }
}
