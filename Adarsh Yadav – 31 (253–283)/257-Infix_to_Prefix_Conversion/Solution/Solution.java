import java.io.*;

public class Solution {

    static int prec(char c) {
        switch (c) {
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

    static String toPrefix(String s) {
        int n = s.length();

        // Step 1: reverse the expression and swap the parentheses
        char[] rev = new char[n];
        for (int i = 0; i < n; i++) {
            char c = s.charAt(n - 1 - i);
            if (c == '(') {
                c = ')';
            } else if (c == ')') {
                c = '(';
            }
            rev[i] = c;
        }

        // Step 2: convert to postfix. After the reversal, left-associative
        // operators must pop only strictly higher precedence.
        StringBuilder out = new StringBuilder();
        char[] stack = new char[n];
        int top = 0;

        for (int i = 0; i < n; i++) {
            char c = rev[i];

            if (Character.isLetter(c)) {
                out.append(c);
            } else if (c == '(') {
                stack[top++] = c;
            } else if (c == ')') {
                while (top > 0 && stack[top - 1] != '(') {
                    out.append(stack[--top]);
                }
                top--; // discard '('
            } else {
                while (top > 0 && stack[top - 1] != '(' && prec(stack[top - 1]) > prec(c)) {
                    out.append(stack[--top]);
                }
                stack[top++] = c;
            }
        }

        while (top > 0) {
            out.append(stack[--top]);
        }

        // Step 3: reverse the postfix result to get the prefix expression
        return out.reverse().toString();
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
