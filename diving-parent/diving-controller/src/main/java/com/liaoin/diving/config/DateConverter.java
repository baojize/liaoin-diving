package com.liaoin.diving.config;


import org.springframework.core.convert.converter.Converter;

import java.lang.annotation.Annotation;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */

public class DateConverter implements Converter<String, Date>{

    @Override
    public Date convert(String source) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
        try {
            Date converterDate = sdf.parse(source);
            return converterDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
