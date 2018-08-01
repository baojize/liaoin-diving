package com.zhangquanli.utils;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.NullCacheStorage;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 模板工具类
 */
@Component
public class FreeMarkerTemplateUtils {
    @Value("${templatePath}")
    private String templatePath;

    public Template getTemplate(String templateName) throws IOException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
        // 这里比较重要，用来指定加载模板所在的路径
        configuration.setTemplateLoader(new ClassTemplateLoader(FreeMarkerTemplateUtils.class, templatePath));
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setCacheStorage(NullCacheStorage.INSTANCE);
        return configuration.getTemplate(templateName);
    }
}