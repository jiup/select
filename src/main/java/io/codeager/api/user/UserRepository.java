package io.codeager.api.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

/**
 * @author Jiupeng Zhang
 * @since 03/07/2019
 */
@Mapper
@Repository
public interface UserRepository {

    @Select("select * from courscio_user where id = #{id}")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "type", property = "type", javaType = User.Type.class, typeHandler = User.Type.Handler.class),
            @Result(column = "email", property = "email", jdbcType = JdbcType.VARCHAR),
            @Result(column = "password", property = "password", jdbcType = JdbcType.VARCHAR),
            @Result(column = "nickname", property = "nickname", jdbcType = JdbcType.VARCHAR),
            @Result(column = "avatar", property = "avatar", jdbcType = JdbcType.VARCHAR),
            @Result(column = "firstName", property = "first_name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "lastName", property = "last_name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "intro", property = "intro", jdbcType = JdbcType.VARCHAR),
            @Result(column = "sex", property = "sex", jdbcType = JdbcType.TINYINT),
            @Result(column = "dateOfBirth", property = "date_of_birth", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "zip", property = "zip", jdbcType = JdbcType.VARCHAR),
            @Result(column = "schoolId", property = "school_id", jdbcType = JdbcType.BIGINT),
            @Result(column = "schoolName", property = "school_name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "degree", property = "degree", javaType = User.Degree.class, typeHandler = User.Degree.Handler.class),
            @Result(column = "grade", property = "grade", javaType = User.Grade.class, typeHandler = User.Grade.Handler.class),
            @Result(column = "classYear", property = "class_year", jdbcType = JdbcType.SMALLINT),
            @Result(column = "failureCount", property = "failure_count", jdbcType = JdbcType.INTEGER),
            @Result(column = "loginCount", property = "login_count", jdbcType = JdbcType.INTEGER),
            @Result(column = "createTime", property = "create_time", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "updateTime", property = "update_time", jdbcType = JdbcType.TIMESTAMP),
    })
    User findById(long id);
}
