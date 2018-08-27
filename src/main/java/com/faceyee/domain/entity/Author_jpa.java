package com.faceyee.domain.entity;

import javax.persistence.*;

/**
 * Created by 97390 on 8/26/2018.
 */
@Entity
@Table(name = "tp_author")
public class Author_jpa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "real_name")
    private String realname;

    @Column(name = "nick_name")
    private String nickName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}