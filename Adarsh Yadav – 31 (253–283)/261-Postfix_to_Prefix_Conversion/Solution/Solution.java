import java.io.*;

public class Solution {

    static String toPrefix(String s) {
        int n = s.length();
        String[] stack = new String[n];
        int top = 0;

        // Scan from left to right
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);

            if (Character.isLetter(c)) {
                stack[top++] = String.valueOf(c);
            } else {
                String right = stack[--top];  // first popped is the RIGHT operand
                String left = stack[--top];   // second popped is the LEFT operand
                stack[top++] = c + left + right;
            }
        }

        return stack[0];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());

        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            String s = br.readLine().trim();
            sb.append(toPrefix(s)).append("\n");
        }

        System.out.print(sb);
    }
}
