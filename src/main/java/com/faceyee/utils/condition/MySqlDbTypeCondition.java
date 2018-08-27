package com.faceyee.utils.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Created by 97390 on 8/24/2018.
 */
public class MySqlDbTypeCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        final String dbType = System.getProperty("dbType");
        return "mysql".equalsIgnoreCase(dbType);
    }
}