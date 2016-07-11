import java.io.IOException;
import java.io.Reader;
/**
 * Created by shilu on 16-7-11.
 */
public class CharArrayReader extends Reader {

    protected char buf[];

    protected int pos;

    protected int markedPos = 0;

    protected int count;

    @Override
    public void close() throws IOException {

    }
    public CharArrayReader(char[] buf) {
        this.buf = buf;
        this.pos = 0;
        this.count = buf.length;
    }
    public CharArrayReader(char[] buf, int offset, int length) {
        // 边界校验
        if ((offset < 0) || (length < 0) || (offset > buf.length) || ((offset + length) < 0))
            throw new IllegalArgumentException();
        this.buf = buf;
        this.pos = offset;
        this.count = Math.min(offset + length, buf.length);
        this.markedPos = offset;
    }

    /** Checks to make sure that the stream has not been closed */
    private void ensureOpen() throws IOException {
        if (buf == null)
            throw new IOException("Stream closed");
    }

    public int read() throws IOException {
        synchronized (lock) {
            ensureOpen();
            if (pos >= count)
                return -1;
            else
                return buf[pos++];
        }
    }

    public int read(char[] b, int off, int len) throws IOException {
        synchronized (lock) {
            ensureOpen();
            if ((off < 0) || (off > b.length) || (len < 0) || ((off + len) > b.length) || ((off + len) < 0)) {
                throw new IndexOutOfBoundsException();
            } else if (len == 0) {
                return 0;
            }

            if (pos >= count) {
                return -1;
            }
            if (pos + len > count) {
                len = count - pos;
            }
            if (len <= 0) {
                return 0;
            }
            System.arraycopy(buf, pos, b, off, len);
            pos += len;
            return len;
        }
    }

    public long skip(long n) throws IOException {
        synchronized (lock) {
            ensureOpen();
            if (pos + n > count)
                n = count - pos;
            if (n < 0)
                return 0;
            pos += n;// pos=pos+n error display
            return n;
        }
    }
    /**
     * Tells whether this stream is ready to be read. Character-array readers
     * are always ready to be read.
     *
     * @exception IOException
     *                If an I/O error occurs
     */
    public boolean ready() throws IOException {
        synchronized (lock) {
            ensureOpen();
            return (count - pos) > 0;
        }
    }

    /**
     * Tells whether this stream supports the mark() operation, which it does.
     */
    public boolean markSupported() {
        return true;
    }

    /**
     * Marks the present position in the stream. Subsequent calls to reset()
     * will reposition the stream to this point.
     *
     * @param readAheadLimit
     *            Limit on the number of characters that may be read while still
     *            preserving the mark. Because the stream's input comes from a
     *            character array, there is no actual limit; hence this argument
     *            is ignored.
     *
     * @exception IOException
     *                If an I/O error occurs
     */
    public void mark(int readAheadLimit) throws IOException {
        synchronized (lock) {
            ensureOpen();
            markedPos = pos;
        }
    }

    /**
     * Resets the stream to the most recent mark, or to the beginning if it has
     * never been marked.
     *
     * @exception IOException
     *                If an I/O error occurs
     */
    public void reset() throws IOException {
        synchronized (lock) {
            ensureOpen();
            pos = markedPos;
        }
    }

    /**
     * Closes the stream and releases any system resources associated with it.
     * Once the stream has been closed, further read(), ready(), mark(),
     * reset(), or skip() invocations will throw an IOException. Closing a
     * previously closed stream has no effect.
     */
}
