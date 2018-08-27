package com.faceyee.utils.exception.method2;

import com.faceyee.utils.readConfig.LocaleMessageUtil;
import com.faceyee.utils.restful.RestServiceError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 97390 on 8/24/2018.
 */
@ControllerAdvice
class GlobalExceptionHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    LocaleMessageUtil localeMessageUtil;


//    // 根据特定的异常返回指定的 HTTP 状态码
//    @ResponseStatus(value= HttpStatus.BAD_REQUEST)  // 400
//    @ExceptionHandler(ConstraintViolationException.class)
//    @ResponseBody
//    public RestServiceError handleValidationException(ConstraintViolationException ex) {
//        Set<ConstraintViolation<?>> errors = ex.getConstraintViolations();
//        StringBuilder strBuilder = new StringBuilder();
//        for (ConstraintViolation<?> violation : errors) {
//            strBuilder.append(violation.getMessage() + "\n");
//        }
//        return localeMessageUtil.getLocalErrorMessage(RestServiceError.Type.VALIDATION_ERROR);
//    }

//    // 未知错误，返回500 ,已经设置返回页面形态了
//    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)  // 500
//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public RestServiceError handleException(Exception ex) {
//        return localeMessageUtil.getLocalErrorMessage(RestServiceError.Type.INTERNAL_SERVER_ERROR);
//    }

    /**
     * 系统错误，记录在log中
     * @param ex
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public RestServiceError exceptionGet(Exception ex) throws Exception {
        LOGGER.error("【运行异常】{}",ex);
        return new RestServiceError("-1", "系统错误");
    }

    /**
     * 已知错误，记录在log中
     * @param de
     * @return
     */
    @ExceptionHandler(value = DescribeException.class)
    @ResponseBody
    public RestServiceError exceptionGet(DescribeException de) throws Exception {
        // 如果碰到了某个自定义异常加上了@ResponseStatus，就继续抛出，这样就不会让自定义异常失去加上@ResponseStatus的初衷。
//        if (AnnotationUtils.findAnnotation(de.getClass(), ResponseStatus.class) != null){
//            throw de;
//        }
        LOGGER.error("【业务异常】{}",de);
        if (de instanceof BusinessException){
            LOGGER.error("【交易异常】{}",de);
            return localeMessageUtil.getLocalErrorMessage(RestServiceError.Type.BUSSINESSERROR);
        }else { // 用case将多个已知异常分别做处理
            return new RestServiceError(String.valueOf(de.getCode()),de.getMessage());
        }
    }

    /**
     * 处理所有接口数据验证异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    RestServiceError handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        LOGGER.error("【校验异常】{}",e);
        // e.getBindingResult().getAllErrors().get(0).getDefaultMessage()
        // return new RestServiceError("444","数据校验错误");
        return localeMessageUtil.getLocalErrorMessage(RestServiceError.Type.VALIDATION_ERROR);
    }
}