package com.faceyee.domain.repository;

import com.faceyee.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by 97390 on 8/21/2018.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>{ // 两个泛型参数分别代表Java POJO类以及主键数据类型

    /*
    * Spring Data JPA提供了很多持久层接口
    * Repository为基类
    * CrudRepositoty提供了对增删改查操作的实现
    * PagingAndSortingRepository提供了分页查询方法的实现
    * JpaRepository继承自PagingAndSortingRepository接口
    * 创建自己的数据库操作接口时，只需继承上述JPA提供的某个接口，即可自动继承相关数据操作方法，而不需要再次实现。
    *如果这仍不能满足业务需求，也可以自定义SQL查询语句。如，采用@Query标签，语法为引用下面用@Param标识的变量
    * */
    /**
     * 命名参数
     * 描述：推荐使用这种方法，可以不用管参数的位置
     */
    @Query("select t from User t where t.userName = :userName") // ":变量"的格式来指定参数，同时在方法的参数前面使用 @Param
                                                                    // 将方法参数与 JP QL 中的命名参数对应。可以不用管参数的位置
    User findByUserName(@Param("userName") String userName);
    // 等同于下面一句JPA 自动构造的语句
    User findUserByUserName(String userName);
    User findUserByUserNameOrEmail(String userName, String email); // 自动构造的语句,参数名要和实体属性严格一致,如name在实体里是name,那么在这里也得是name


    /**
     * 索引参数
     * 描述：使用?占位符
     */
    @Query("select u from User u where u.email = ?1")// 1表示第一个参数
    User findUserByEmail(String email);

    /**
     * 描述：可以通过@Modifying和@Query来实现更新
     * 注意：Modifying queries的返回值只能为void或者是int/Integer
     */
    @Modifying(clearAutomatically = true)
    @Query("update User u set u.userName = :userName where u.id = :id")
    int updateUserById(@Param("userName") String userName, @Param("id") int id);
    /*它说的是可以清除底层持久化上下文，就是entityManager这个类，我们知道jpa底层实现会有二级缓存，也就是在更新完数据库后，如果后面去用这个对象，
    你再去查这个对象，这个对象是在一级缓存，但是并没有跟数据库同步，这个时候用clearAutomatically=true,就会刷新hibernate的一级缓存了，
    不然你在同一接口中，更新一个对象，接着查询这个对象，那么你查出来的这个对象还是之前的没有更新之前的状态*/

}
