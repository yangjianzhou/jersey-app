package test.java.service;

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
    }
}
