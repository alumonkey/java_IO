import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
/**
 * Created by shilu on 16-7-12.
 */
public class CharArrayWriter extends Writer {

    private char[] buf;

    private int pos;

    private int count;

    public CharArrayWriter() {
        this(32);
    }
    public CharArrayWriter(int initialSize) {
        if (initialSize < 0) {
            throw new IllegalArgumentException("Negative initial size: " + initialSize);
        }
        buf = new char[initialSize];
    }

    public void close() throws IOException {

    }
    public void write(int c) {
        synchronized (lock) {
            int newCount = count + 1;
            if (newCount > buf.length) {
                buf = Arrays.copyOf(buf, Math.max(buf.length << 1, newCount));
            }
            buf[count] = (char) c;
            count = newCount;
        }
    }
    public void write(char[] cbuf, int off, int len) throws IOException {
        if ((off < 0) || (len < 0) || ((off + len) > cbuf.length) || (off > cbuf.length) || ((off + len) < 0)) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return;
        }
        synchronized (lock) {
            int newcount = count + len;
            if (newcount > buf.length) {
                buf = Arrays.copyOf(buf, Math.max(buf.length << 1, newcount));
            }
            System.arraycopy(cbuf, off, buf, count, len);
            count = newcount;
        }

    }

    public void write(String s, int off, int len) throws IOException {
        synchronized (lock) {
            int newCount = count + len;
            if (newCount > buf.length) {
                buf = Arrays.copyOf(buf, Math.max(buf.length >> 1, newCount));
            }
            s.getChars(off, off + len, buf, count);
            count = newCount;
        }
    }

    public void writeTo(Writer out) throws IOException {
        synchronized (lock) {
            out.write(buf, 0, count);
        }
    }

    public CharArrayWriter append(CharSequence csq) throws IOException {
        String s = (csq == null ? "null" : csq.toString());
        write(s, 0, s.length());
        return this;
    }

    public void flush() throws IOException {

    }

    public void reset() {
        count = 0;
    }

    public char toCharArray()[] {
        synchronized (lock) {
            return Arrays.copyOf(buf, count);
        }
    }

    /**
     * Returns the current size of the buffer.
     *
     * @return an int representing the current size of the buffer.
     */
    public int size() {
        return count;
    }

    /**
     * Converts input data to a string.
     * @return the string.
     */
    public String toString() {
        synchronized (lock) {
            return new String(buf, 0, count);
        }
    }
}
