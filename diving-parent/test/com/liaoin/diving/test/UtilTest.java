package com.liaoin.diving.test;

import org.junit.jupiter.api.Test;
import com.liaoin.*;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
public class UtilTest {
    @Resource
    private ServletContext servletContext;
    @Test
    public void test(){
        BigDecimal bg1 = new BigDecimal(10);
        BigDecimal bg2 = new BigDecimal(0.5);

        ArrayList<Object> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        int i = list.indexOf("1");
        System.out.println(i);
        System.out.println(bg1.multiply(bg2));
    }

    @Test
    public void test1(){
        ArrayList<Object> list = new ArrayList<>();
        String str = "/upload/2018/07/13/3103d8c6-ae83-49f2-8712-d643ef711e9d.jpg";
        int i = str.lastIndexOf("/");
        String substring1 = str.substring(7);

        String substring = str.substring(i+1);
        System.out.println("sub1:" + substring1);
        System.out.println(substring);
        System.out.println(i);
    }
    @Test
    public void test2(){
        String str = "D:/MRHU/diving/diving-parent/diving-controller/target/classes";
        int i = str.indexOf("/", 2);
        System.out.println(i);
    }
    @Test
    public void test3(){
        BigDecimal bigDecimal = new BigDecimal("0");
        BigDecimal bigDecima2 = new BigDecimal("10");
        BigDecimal bigDecima3 = new BigDecimal("0.8");

        bigDecimal = bigDecima2.add(bigDecima3);
        System.out.println(bigDecimal);
    }
    @Test
    public void test4(){
        List list =new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");

        System.out.println(list.indexOf("4"));
    }
    @Test
    public void test5(){
        List list =new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");

        Math.random();
        for (int i = 0; i < 10; i++){
            double random = Math.random();
            Math.random() ;
            System.out.println(random );
        }
        //RandomUtils

        System.out.println(list.indexOf("1"));
    }
}
