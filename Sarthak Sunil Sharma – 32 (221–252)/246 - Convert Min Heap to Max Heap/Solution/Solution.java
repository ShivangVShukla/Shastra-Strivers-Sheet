import java.io.*;
import java.util.*;
public class Solution {
 static void maxHeapify(long[] a,int n,int i){while(true){int x=i,l=2*i+1,r=2*i+2;if(l<n&&a[l]>a[x])x=l;if(r<n&&a[r]>a[x])x=r;if(x==i)break;long t=a[i];a[i]=a[x];a[x]=t;i=x;}}
 public static void main(String[] z)throws Exception{BufferedReader b=new BufferedReader(new InputStreamReader(System.in));int n=Integer.parseInt(b.readLine().trim());long[] a=new long[n];StringTokenizer s=new StringTokenizer(b.readLine());for(int i=0;i<n;i++)a[i]=Long.parseLong(s.nextToken());for(int i=n/2-1;i>=0;i--)maxHeapify(a,n,i);StringBuilder o=new StringBuilder();for(int i=0;i<n;i++){if(i>0)o.append(' ');o.append(a[i]);}System.out.println(o);}
}
