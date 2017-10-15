/**
 * Created by yangjianzhou on 17-7-22.
 */
public class Test {

    public static void main(String[] args) {

        String str1 = "str1";
        String str2 = "str2";
        String str3 = new String("str1str2");
        String str4 = str1 + str2;
        String str5 = "str1str2";
        System.out.println(str3 == str4);
        System.out.println(str5 == str3);
        // method1();
    }

    public synchronized static void method1() {
        System.out.print("method1");
    }


    public synchronized static void method2() {
        System.out.print("method2");
    }


}
