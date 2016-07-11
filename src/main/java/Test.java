import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
/**
 * Created by shilu on 16-6-12.
 */
public class Test {
    public static void main(String[] args) throws Exception {

        // gets the value of the specified environment variable "PATH"
        // System.out.println("System.getenv("PATH")");
        System.out.println(System.getenv("PATH"));

        // gets the value of the specified environment variable "TEMP"
        // System.out.print("System.getenv("TEMP") = ");
        System.out.println(System.getenv("TEMP"));

        // gets the value of the specified environment variable "USERNAME"
        // System.out.print("System.getenv(" USERNAME") = ");
        System.out.println(System.getenv("USERNAME"));
        // test1();
    }
    private static void test1() throws IOException {
        File file = new File("/home/shilu/myfile/jiaoben/maven_appStore.sh");
        FileInputStream rf = new FileInputStream(file);
        byte buffer[] = new byte[1024];
        while ((rf.read(buffer, 0, 1024) != -1)) {
            System.out.println(new String(buffer, "utf-8"));
        }
        System.out.println();
        rf.close();
    }

}
