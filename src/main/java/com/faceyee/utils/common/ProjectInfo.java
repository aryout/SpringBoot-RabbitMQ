package com.faceyee.utils.common;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by 97390 on 8/21/2018.
 */
@Configuration
@Data
public class ProjectInfo {
    /** 支持SpEL表达式。例如这里，如果属性不存在则设为default_title */
    @Value("${com.faceyee.title:default_title}")
    private String title;
    @Value("${com.faceyee.description}")
    private String description;
}
