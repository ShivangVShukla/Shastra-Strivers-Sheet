import java.io.*;

public class Solution {

    static String toPostfix(String s) {
        int n = s.length();
        String[] stack = new String[n];
        int top = 0;

        // Scan from right to left
        for (int i = n - 1; i >= 0; i--) {
            char c = s.charAt(i);

            if (Character.isLetterOrDigit(c)) {
                stack[top++] = String.valueOf(c);
            } else {
                String left = stack[--top];   // first popped is the LEFT operand
                String right = stack[--top];  // second popped is the RIGHT operand
                stack[top++] = left + right + c;
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
            sb.append(toPostfix(s)).append("\n");
        }

        System.out.print(sb);
    }
}
