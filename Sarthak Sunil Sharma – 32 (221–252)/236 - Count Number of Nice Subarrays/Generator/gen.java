import java.util.*;
public class gen {
    static class TestCase { int[] a; int k; TestCase(int[] a,int k){this.a=a;this.k=k;} }
    public static void main(String[] args) { Random r=new Random(236); for(int t=0;t<100;t++){ int n=1+r.nextInt(100); int k=1+r.nextInt(n); int[] a=new int[n]; for(int i=0;i<n;i++) a[i]=1+r.nextInt(100); System.out.println(n+" "+k); for(int x:a) System.out.print(x+" "); System.out.println(); } }
}
