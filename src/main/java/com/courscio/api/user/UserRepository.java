package com.courscio.api.user;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

/**
 * @author Jiupeng Zhang
 * @since 03/07/2019
 */
@Mapper
@Repository
public interface UserRepository {

    @Select("select id, type, email, password, nickname, avatar, first_name, last_name, intro, " +
            "sex, dob, zip, school_id, school_name, degree, grade, class_year, failure_cnt, " +
            "login_cnt, create_t, update_t from courscio_user where id = #{id}")
    @Results({
            @Result(property = "id", column = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(property = "type", column = "type", javaType = User.Type.class, typeHandler = User.Type.Handler.class),
            @Result(property = "email", column = "email", jdbcType = JdbcType.VARCHAR),
            @Result(property = "password", column = "password", jdbcType = JdbcType.VARCHAR),
            @Result(property = "nickname", column = "nickname", jdbcType = JdbcType.VARCHAR),
            @Result(property = "avatar", column = "avatar", jdbcType = JdbcType.VARCHAR),
            @Result(property = "firstName", column = "first_name", jdbcType = JdbcType.VARCHAR),
            @Result(property = "lastName", column = "last_name", jdbcType = JdbcType.VARCHAR),
            @Result(property = "intro", column = "intro", jdbcType = JdbcType.VARCHAR),
            @Result(property = "sex", column = "sex", jdbcType = JdbcType.TINYINT),
            @Result(property = "dateOfBirth", column = "dob", jdbcType = JdbcType.TIMESTAMP),
            @Result(property = "zip", column = "zip", jdbcType = JdbcType.VARCHAR),
            @Result(property = "schoolId", column = "school_id", jdbcType = JdbcType.BIGINT),
            @Result(property = "schoolName", column = "school_name", jdbcType = JdbcType.VARCHAR),
            @Result(property = "degree", column = "degree", javaType = User.Degree.class, typeHandler = User.Degree.Handler.class),
            @Result(property = "grade", column = "grade", javaType = User.Grade.class, typeHandler = User.Grade.Handler.class),
            @Result(property = "classYear", column = "class_year", jdbcType = JdbcType.SMALLINT),
            @Result(property = "failureCount", column = "failure_cnt", jdbcType = JdbcType.INTEGER),
            @Result(property = "loginCount", column = "login_cnt", jdbcType = JdbcType.INTEGER),
            @Result(property = "createTime", column = "create_t", jdbcType = JdbcType.TIMESTAMP),
            @Result(property = "updateTime", column = "update_t", jdbcType = JdbcType.TIMESTAMP),
    })
    User findById(long id);

    @Select("select id, type, email, password, nickname, avatar, first_name, last_name, intro, " +
            "sex, dob, zip, school_id, school_name, degree, grade, class_year, failure_cnt, " +
            "login_cnt, create_t, update_t from courscio_user where email = #{email}")
    @Results({
            @Result(property = "id", column = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(property = "type", column = "type", javaType = User.Type.class, typeHandler = User.Type.Handler.class),
            @Result(property = "email", column = "email", jdbcType = JdbcType.VARCHAR),
            @Result(property = "password", column = "password", jdbcType = JdbcType.VARCHAR),
            @Result(property = "nickname", column = "nickname", jdbcType = JdbcType.VARCHAR),
            @Result(property = "avatar", column = "avatar", jdbcType = JdbcType.VARCHAR),
            @Result(property = "firstName", column = "first_name", jdbcType = JdbcType.VARCHAR),
            @Result(property = "lastName", column = "last_name", jdbcType = JdbcType.VARCHAR),
            @Result(property = "intro", column = "intro", jdbcType = JdbcType.VARCHAR),
            @Result(property = "sex", column = "sex", jdbcType = JdbcType.TINYINT),
            @Result(property = "dateOfBirth", column = "dob", jdbcType = JdbcType.TIMESTAMP),
            @Result(property = "zip", column = "zip", jdbcType = JdbcType.VARCHAR),
            @Result(property = "schoolId", column = "school_id", jdbcType = JdbcType.BIGINT),
            @Result(property = "schoolName", column = "school_name", jdbcType = JdbcType.VARCHAR),
            @Result(property = "degree", column = "degree", javaType = User.Degree.class, typeHandler = User.Degree.Handler.class),
            @Result(property = "grade", column = "grade", javaType = User.Grade.class, typeHandler = User.Grade.Handler.class),
            @Result(property = "classYear", column = "class_year", jdbcType = JdbcType.SMALLINT),
            @Result(property = "failureCount", column = "failure_cnt", jdbcType = JdbcType.INTEGER),
            @Result(property = "loginCount", column = "login_cnt", jdbcType = JdbcType.INTEGER),
            @Result(property = "createTime", column = "create_t", jdbcType = JdbcType.TIMESTAMP),
            @Result(property = "updateTime", column = "update_t", jdbcType = JdbcType.TIMESTAMP),
    })
    User findByEmail(@Param("email") String email);

    @Insert({
            "insert into courscio_user (id, type, email, password, nickname, avatar, first_name, " +
                    "last_name, intro, sex, dob, zip, school_id, school_name, degree, " +
                    "grade, class_year, failure_cnt, login_cnt, create_t, update_t)",
            "values (#{id,jdbcType=BIGINT}, #{type}, #{email}, #{password}, #{nickname}, #{avatar}, " +
                    "#{firstName}, #{lastName}, #{intro}, #{sex}, #{dateOfBirth}, #{zip}, " +
                    "#{schoolId}, #{schoolName}, #{degree}, #{grade}, #{classYear}, #{failureCount}, " +
                    "#{loginCount}, #{createTime}, #{updateTime})"
    })
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = long.class)
    int insert(User user);

    @Update({
            "update courscio_user",
            "set password = #{password}",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int updateTokenById(long id, String password);

    @Update({
            "update courscio_user",
            "set password = #{password}, nickname = #{nickname}, avatar = #{avatar}, first_name = #{firstName},",
            "last_name = #{lastName}, intro = #{intro}, zip = #{zip}, failure_cnt = #{failureCount},",
            "login_cnt = #{loginCount}, update_t = #{updateTime}",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int updateById(User user);
}
