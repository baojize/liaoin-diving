package com.zhangquanli;

import com.zhangquanli.utils.CodeGenerateUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {

    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        CodeGenerateUtils codeGenerateUtils = (CodeGenerateUtils) applicationContext.getBean("codeGenerateUtils");
        codeGenerateUtils.generate();
    }
}
