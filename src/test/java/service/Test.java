package service;

import com.google.common.base.Splitter;

import java.util.List;

/**
 * Created by yangjianzhou on 16-5-21.
 */
public class Test {

    public static void main(String[] args) {
        String str = "1111-2323-23423";
        List<String> list = Splitter.on('-').trimResults().omitEmptyStrings().splitToList(str);
        System.out.println(list.get(0));
        System.out.println(list.get(1));
        System.out.println(list.get(2));
        String[] ls = str.split("-");
        System.out.println(list.get(2));
        fibonacci(100);
    }

    public static long fibonacci(long n) {
        long tmpN_1 = 1;
        long tmpN_2 = 0;

        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        long counter = 1;
        long result = 0;
        while (counter < n) {
            result = tmpN_1 + tmpN_2;
            tmpN_2 = tmpN_1;
            tmpN_1 = result;
            counter++;
            System.out.print(result + " ");
        }
        return result;
    }

    public synchronized static void method1(){

    }
}
