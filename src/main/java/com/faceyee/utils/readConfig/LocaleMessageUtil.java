package com.faceyee.utils.readConfig;

import com.faceyee.utils.restful.RestServiceError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Created by 97390 on 8/24/2018.
 */
@Component
public class LocaleMessageUtil {

    @Autowired
    private MessageSource messageSource;

    public RestServiceError getLocalErrorMessage(RestServiceError.Type errorCode) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage(String.valueOf(errorCode.getCode()), null, locale);
        return RestServiceError.build(errorCode, errorMessage);
    }
}