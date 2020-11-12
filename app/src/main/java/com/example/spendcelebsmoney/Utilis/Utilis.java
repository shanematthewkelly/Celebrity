package com.example.spendcelebsmoney.Utilis;

import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Locale;

public class Utilis {


    public static String bigIntegerFormatter(){
        BigInteger integer = BigInteger.valueOf(60000);

        String result = NumberFormat.getNumberInstance(Locale.US).format(
                integer);
//        System.out.println(result);

        return result;
    }
}
