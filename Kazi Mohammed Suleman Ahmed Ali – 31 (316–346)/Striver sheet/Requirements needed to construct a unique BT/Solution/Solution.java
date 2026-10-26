import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

public class Solution {

    /** Custom byte-buffer reader for fast input. */
    static class FastScanner {
        private static final int BUFFER_SIZE = 1 << 16;
        private final DataInputStream in;
        private final byte[] buffer = new byte[BUFFER_SIZE];
        private int ptr = 0;
        private int len = 0;

        FastScanner(InputStream is) {
            in = new DataInputStream(is);
        }

        private int read() throws IOException {
            if (ptr == len) {
                len = in.read(buffer, 0, BUFFER_SIZE);
                ptr = 0;
                if (len <= 0) {
                    len = 0;
                    return -1;
                }
            }
            return buffer[ptr++];
        }

        int nextInt() throws IOException {
            int c = read();
            while (c != -1 && c != '-' && (c < '0' || c > '9')) {
                c = read();
            }
            boolean neg = false;
            if (c == '-') {
                neg = true;
                c = read();
            }
            int res = 0;
            while (c >= '0' && c <= '9') {
                res = res * 10 + (c - '0');
                c = read();
            }
            return neg ? -res : res;
        }
    }

    /**
     * 1 = Preorder, 2 = Inorder, 3 = Postorder.
     * A unique binary tree is determined iff Inorder is one of the pair
     * and the other is Preorder or Postorder.
     */
    static boolean uniqueBinaryTree(int a, int b) {
        return (a == 2 && b != 2) || (b == 2 && a != 2);
    }

    public static void main(String[] args) throws IOException {
        FastScanner fs = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int a = fs.nextInt();
        int b = fs.nextInt();
        out.println(uniqueBinaryTree(a, b) ? "true" : "false");
        out.flush();
        out.close();
    }
}
