import java.io.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String num = br.readLine().trim();
        int k = Integer.parseInt(br.readLine().trim());
        System.out.println(removeKdigits(num, k));
    }

    static String removeKdigits(String num, int k) {
        char[] stack = new char[num.length()];
        int top = 0;
        for (int i = 0; i < num.length(); i++) {
            char c = num.charAt(i);
            while (top > 0 && k > 0 && stack[top - 1] > c) {
                top--;
                k--;
            }
            stack[top++] = c;
        }
        top -= k;
        int start = 0;
        while (start < top && stack[start] == '0') start++;
        return start == top ? "0" : new String(stack, start, top - start);
    }
}
