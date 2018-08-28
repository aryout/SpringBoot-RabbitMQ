package com.faceyee.domain.repository;

import com.faceyee.domain.entity.CaptchaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by 97390 on 8/28/2018.
 */
@Repository
public interface CaptchaRepository extends JpaRepository<CaptchaEntity, Long> {

    CaptchaEntity findCaptchaEntityByCaptcha(String captcha);
    CaptchaEntity findCaptchaEntityByToken(String token);
    CaptchaEntity saveAndFlush(CaptchaEntity captchaEntity); // update 的两种方法，modify注解和saveandflush

    void deleteByToken(String token);

}
