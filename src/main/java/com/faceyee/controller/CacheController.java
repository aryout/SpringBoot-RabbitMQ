package com.faceyee.controller;

/**
 * Created by 97390 on 8/26/2018.
 */

import com.faceyee.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 使用自带的cache，在项目关闭后cache 清空
 */
@RestController
@RequestMapping(value = "/concurrenmapcache/cache")
public class CacheController {

    @Autowired
    private CacheService cacheService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    public String getByCache() {
        long startTme = System.currentTimeMillis();
        long timeTme = this.cacheService.getByCache();
        long endTme = System.currentTimeMillis();
        System.out.println("耗时:" + (endTme - startTme));
        return timeTme + "";
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public void save(){
        this.cacheService.save();
    }

    @RequestMapping(value = "",method = RequestMethod.DELETE)
    public void delete(){
        this.cacheService.delete();
    }
}