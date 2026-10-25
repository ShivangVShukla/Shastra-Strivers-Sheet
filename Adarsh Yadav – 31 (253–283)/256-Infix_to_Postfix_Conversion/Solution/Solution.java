import java.io.*;

public class Solution {

    static int prec(char c) {
        switch (c) {
            case '^':
                return 3;
            case '*':
            case '/':
                return 2;
            case '+':
            case '-':
                return 1;
            default:
                return 0;
        }
    }

    static String toPostfix(String s) {
        StringBuilder out = new StringBuilder();
        char[] stack = new char[s.length()];
        int top = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (Character.isLetterOrDigit(c)) {
                out.append(c);
            } else if (c == '(') {
                stack[top++] = c;
            } else if (c == ')') {
                while (top > 0 && stack[top - 1] != '(') {
                    out.append(stack[--top]);
                }
                top--; // discard '('
            } else {
                // '^' is right-associative: pop only strictly higher precedence.
                // Other operators are left-associative: pop higher or equal.
                while (top > 0 && stack[top - 1] != '('
                        && (prec(stack[top - 1]) > prec(c)
                        || (prec(stack[top - 1]) == prec(c) && c != '^'))) {
                    out.append(stack[--top]);
                }
                stack[top++] = c;
            }
        }

        while (top > 0) {
            out.append(stack[--top]);
        }

        return out.toString();
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
