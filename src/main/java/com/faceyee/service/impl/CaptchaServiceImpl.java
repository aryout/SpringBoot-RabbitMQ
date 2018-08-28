package com.faceyee.service.impl;

import com.faceyee.domain.entity.CaptchaEntity;
import com.faceyee.domain.repository.CaptchaRepository;
import com.faceyee.service.CaptchaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by 97390 on 8/28/2018.
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {
    @Resource
    private CaptchaRepository captchaRepository;

    //1小时后过期
    private final static int EXPIRE = 3600 * 1;

    @Override
    public CaptchaEntity queryByCaptcha(String captcha) {
        return captchaRepository.findCaptchaEntityByCaptcha(captcha);
    }

    @Override
    public CaptchaEntity queryByToken(String token) {
        return captchaRepository.findCaptchaEntityByToken(token);
    }

    @Override
    public void save(CaptchaEntity token){
        captchaRepository.save(token);
    }

    @Override
    public void update(CaptchaEntity token){
        captchaRepository.saveAndFlush(token);
    }

    @Override
    public boolean isExpired(Date expireTime){
        Date d=new Date();
        return d.getTime()>expireTime.getTime()?true:false;
    }

    @Override
    public Map<String, Object> createToken(String captcha) {
        //生成一个token
        String token = UUID.randomUUID().toString();
        //当前时间
        Date now = new Date();

        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

        //判断是否生成过token
        CaptchaEntity tokenEntity = queryByCaptcha(captcha);
        if(tokenEntity == null){
            tokenEntity = new CaptchaEntity();
            tokenEntity.setCaptcha(captcha);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //保存token
            save(tokenEntity);
        }else{
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //更新token
            update(tokenEntity);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("expire", EXPIRE);

        return map;
    }

    @Override
    public void deleteByToken(String token) {
        captchaRepository.deleteByToken(token);
    }
}