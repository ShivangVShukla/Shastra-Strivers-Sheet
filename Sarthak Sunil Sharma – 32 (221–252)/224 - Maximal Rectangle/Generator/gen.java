import java.io.*;
import java.util.*;

public class gen {
    static class TestCase {
        int r, c; char[][] a;
        TestCase(int r, int c) { this.r=r; this.c=c; a=new char[r][c]; }
    }
    static TestCase make(int r,int c,Random rnd,int type){
        TestCase t=new TestCase(r,c);
        for(char[] row:t.a) Arrays.fill(row,'0');
        if(type==0) return t;
        if(type==1){for(char[] row:t.a)Arrays.fill(row,'1');return t;}
        if(type==2){for(int j=0;j<c;j++)t.a[rnd.nextInt(r)][j]='1';return t;}
        if(type==3){for(int i=0;i<r;i++)t.a[i][rnd.nextInt(c)]='1';return t;}
        if(type==4){for(int i=0;i<r;i++)for(int j=0;j<c;j++)t.a[i][j]=(i+j)%2==0?'1':'0';return t;}
        if(type==5){for(int i=0;i<r;i++)for(int j=0;j<c;j++)t.a[i][j]=(j%3==0||i%4==0)?'1':'0';return t;}
        if(type==6){int top=rnd.nextInt(r),left=rnd.nextInt(c),bottom=top+rnd.nextInt(r-top),right=left+rnd.nextInt(c-left);for(int i=top;i<=bottom;i++)for(int j=left;j<=right;j++)t.a[i][j]='1';return t;}
        if(type==7){for(int i=0;i<r;i++)for(int j=0;j<c;j++)t.a[i][j]=rnd.nextDouble()<0.2?'1':'0';return t;}
        if(type==8){for(int i=0;i<r;i++)for(int j=0;j<c;j++)t.a[i][j]=rnd.nextDouble()<0.8?'1':'0';return t;}
        for(int i=0;i<r;i++)for(int j=0;j<c;j++)t.a[i][j]=rnd.nextBoolean()?'1':'0';return t;
    }
    static void write(TestCase t,String path)throws Exception{try(PrintWriter out=new PrintWriter(new BufferedWriter(new FileWriter(path)))){out.println(t.r+" "+t.c);for(char[] row:t.a)out.println(new String(row));}}
    public static void main(String[] args)throws Exception{
        File dir=new File(args.length>0?args[0]:"../Testcases/Input");dir.mkdirs();Random rnd=new Random(2242026L);
        for(int k=0;k<100;k++){
            int r,c,type;
            if(k==0){r=1;c=1;type=0;}else if(k==1){r=1;c=1;type=1;}else if(k==2){r=1;c=200;type=1;}else if(k==3){r=200;c=1;type=1;}else if(k==4){r=200;c=200;type=1;}else if(k==5){r=4;c=5;type=9;}else if(k<15){r=1+rnd.nextInt(8);c=1+rnd.nextInt(8);type=k%9;}else if(k<35){r=5+rnd.nextInt(30);c=5+rnd.nextInt(30);type=k%10;}else if(k<70){r=30+rnd.nextInt(71);c=30+rnd.nextInt(71);type=k%10;}else{r=120+rnd.nextInt(81);c=120+rnd.nextInt(81);type=k%10;}
            write(make(r,c,rnd,type),new File(dir,String.format("input%02d.txt",k)).getPath());
        }
    }
}
