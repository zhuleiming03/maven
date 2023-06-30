package demo;

import cn.hutool.core.convert.Convert;

public class Index {

    public static void main(String[] args) {
        double money = 12345678.199995;
        System.out.printf("%s : %s \n", money, Convert.digitToChinese(money));

        money = -199.01;
        System.out.printf("%s : %s \n", money, Convert.digitToChinese(money));

        money = -0.219;
        System.out.printf("%s : %s \n", money, Convert.digitToChinese(money));

        money = 0.00;
        System.out.printf("%s : %s \n", money, Convert.digitToChinese(money));

        money = 199.00;
        System.out.printf("%s : %s \n", money, Convert.digitToChinese(money));
    }
}
