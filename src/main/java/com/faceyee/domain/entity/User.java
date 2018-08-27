package com.faceyee.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.stereotype.Component;

//import org.springframework.data.annotation.Id;

import javax.persistence.*; // Entity.Id.GeneratedValue
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;

/**
 * Created by 97390 on 8/21/2018.
 */
@Data
@Entity
@Table(name = "tbl_user") //指定数据库中对应的表名,不加这个,只加entity也会做映射,默认用类名做表名
public class User implements Serializable {
    // 不修改这个变量值的序列化实体都可以相互进行串行化和反串行化.
    // 怎么修改类文件,不修改这个UID值,都可以互相串化
    // 如果实现了序列化接口,却没显式声明UID值,就会改变后不能相互串化,即不兼容
    // 选择默认的值1L较好，因为这样能保证兼容
    private static final long serialVersionUID = 1L; // 实现序列化接口,为了缓存或分布式环境等的序列化

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Spring Data JPA才会知道你想要拿到这个保存后的实体，再返回这个实体
    @Column(insertable = false, updatable = false, name = "id", nullable = false, columnDefinition = "BIGINT UNSIGNED") // 字段值为只读的，不允许插入和修改。通常用于主键和外键.bigint(20),在Java中是Long
    private Long id;

    @Column(insertable = false,length = 12, nullable = false, unique = true) // varchar(36)
    private String uuid;

    @Column(name="user_name",nullable = false, unique = true, length=512) // length 表示varchar字段,在Java中是String
    private String userName;

    @Max(value = 999999,message = "超过最大数值")
    @Min(value = 0,message = "密码设定不正确")
    @JsonIgnore // Rest接口返回用户信息时,屏蔽密码,但不是不序列化到数据库,同时接受Rest登录请求时传入的密码使用的是UserModel对象,在那里不是JsonIgnore的,可以接受到
    private String passWord;

    @Column(name="monthly_income", precision=12, scale=2) // 精度为12位(总长度)，小数点位数为2位,为double字段,相当于double(12,2),在Java中是BigDecimal
    private BigDecimal monthlyIncome;

    @Column(columnDefinition = "varchar(11) not null") // columnDefinition  自定义sql片段: 类型 + 条件
    private String mobile;

    @Column(nullable = false, unique = true)
    private String email;

    @Transient  // 不映射成列的字段得加@Transient 瞬态注解
    private String randomName; // 随机名,如脉脉匿名区

    @Column(insertable = false,nullable = false, columnDefinition="DATE") // 对于插入Date无法确定数据库中字段类型究竟是DATE,TIME 还是 TIMESTAMP,需要在这里直接指定
    private String regTime;

    @Lob
    @Column(columnDefinition="LONGTEXT")
    public String content;  // String 的默认映射类型为 VARCHAR, 将 String 类型映射到特定数据库的 TEXT 字段类型

    @Lob
    @Column(insertable = false,length=8, columnDefinition="BLOB")
    private Blob pic; // image对象

    /*Lob代表大对象数据，包括BLOB和CLOB两种类型数据，前者用于存储大块的二进制数据，如图片和视频数据等，
    而后者用于存储长文本数据，如论坛帖子内容，产品详细描述等。*/
    /*在不同的数据库中，大对象对应的字段类型往往不一样，如oracle对应的是BLOB/CLOB; Mysql对应的BLOB/LONGTEXT; SQLSERER对应IMAGE/TEXT,
    有些数据库对大对象类型可以像简单类型一样访问，如mysql的LONGTEXT的操作方式和VARCHAR类型一样，在一半情况下，LOB类型数据的访问
    不同于其他简单类型的数据，用户可能会以流的方式操作LOB类型的数据。*/
    /*LOB类型的数据访问并不是线程安全的，需要分配相应的数据库资源，并在操作后完成释放。*/
    /*org.springframework.jdbc.support.lob包中提供非jdbc标准的api操作lob数据*/
    /*LobCreator 本身执有Lob所对应的数据库资源，所以它不是线程安全，一个LobCreator只能操作一个Lob数据*/
    /*LobHandler 接口为操作大二进制字段和大文本字段提供统一的访问接口访问，不管底层数据库是以大对象的方式还是一般数据类型进行操作，
    LobHandler还充当了LobCreator的工厂类*/


    public User(){
        super();
    }
    public User(String email,String passWord, String userName) {
        super();
        this.email = email;
        this.passWord = passWord;
        this.userName = userName;
    }
}
