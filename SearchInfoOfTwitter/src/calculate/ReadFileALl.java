package calculate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ReadFileALl {

    public static void main(String[] args) throws Exception {
        Sum sum = new Sum();
        Thread t1 = new Thread(sum);
        Thread t2 = new Thread(sum);
        Thread t3 = new Thread(sum);
        t1.start();
        t2.start();
        t3.start();
        // t1.run();
        // t2.run();
        // t3.run();
        // System.out.println(sum.getSum()+"==");
    }
}

class Sum implements Runnable {
    private Integer i = 0;
    private Integer sum = 0;
    static long time;

    public void run() {
        File file = null;
        InputStream is = null;
        StringBuffer sb = null;
        while (true) {
            if (i == 0) {
                time = System.currentTimeMillis();
            }
            if (i == 10000) {
                break;
            }
            synchronized (this) {
                file = new File("c:/file/file" + i + ".txt");
                // System.out.println(i + "currentThread==" +
                // Thread.currentThread().getName()); 

              i++;


            }
           
            try {
                is = new FileInputStream(file);
            } catch (FileNotFoundException e) {
            }
            byte[] data = new byte[2048];
            int len = 0;
            sb = new StringBuffer();
            try {
                while ((len = is.read(data)) != -1) {
                    sb.append(new String(data, 0, len));
                }
            } catch (IOException e) {
            }
            String result = sb.toString();
            String[] arr = result.split("\\D+");
            synchronized (this) {
                for (String s : arr) {
                    if (s != null && s.trim().length() > 0) {
                        sum += Integer.parseInt(s);
                    }
                }
            }
        }
        file = null;
        sb = null;
        is = null;
        System.out.println(this.sum);
        System.out.println(System.currentTimeMillis() - time);
    }

    public Integer getI() {
        return i;
    }

    public void setI(Integer i) {
        this.i = i;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

}

