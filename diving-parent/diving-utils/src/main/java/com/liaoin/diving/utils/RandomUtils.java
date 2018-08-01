package com.liaoin.diving.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
public class RandomUtils {

    public static List<Integer> get(Integer max, Integer size){
        List<Integer> randomNum = new ArrayList<>();
        int random ;
        for (int i = 0; i < size; i++){
            random = (int)(Math.random() * max );
            if (randomNum.indexOf(random) == 0){
                get(max,size);
            }
            randomNum.add(random);
        }
        return randomNum;
    }
}
