package com.faceyee.utils.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * Created by 97390 on 8/26/2018.
 */
@Component
public class CusStatusHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        int errCode = check();
        if(errCode != 0){
            return Health.down()
                         .withDetail("status",errCode)
                         .withDetail("message","http error")
                         .build();
        }
        return Health.up().build();
    }

    private int check(){
        return HttpStatus.NOT_FOUND.value();
    }
}