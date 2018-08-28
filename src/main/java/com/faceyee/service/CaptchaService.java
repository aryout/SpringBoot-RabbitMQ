package com.faceyee.service;

import com.faceyee.domain.entity.CaptchaEntity;

import java.util.Date;
import java.util.Map;

/**
 * Created by 97390 on 8/28/2018.
 */
public interface CaptchaService {

    public CaptchaEntity queryByCaptcha(String captcha);

    public CaptchaEntity queryByToken(String token);

    public void save(CaptchaEntity token);

    public void update(CaptchaEntity token);

    public boolean isExpired(Date expireTime);

    public Map<String, Object> createToken(String captcha);

    public void deleteByToken(String token);
}
