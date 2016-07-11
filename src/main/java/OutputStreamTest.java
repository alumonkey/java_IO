import java.io.FileOutputStream;
/**
 * Created by shilu on 16-6-13.
 */
public class OutputStreamTest {
    public static void main(String[] args) throws Exception {
        int count = 1024;
        int n = 1024;
        byte[] buffer = new byte[n];
        count = System.in.read(buffer);
        FileOutputStream wf = new FileOutputStream("/home/shilu/output_text.txt");
        wf.write(buffer, 0, count);
        wf.close(); // 当流写操作结束时，调用close方法关闭流。
        System.out.println("Save to the write.txt");
    }
}
