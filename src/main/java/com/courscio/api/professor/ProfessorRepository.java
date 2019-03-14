package com.courscio.api.professor;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

/**
 * @author Jiupeng Zhang
 * @since 03/14/2019
 */
@Mapper
@Repository
public interface ProfessorRepository {

    @Select("select * from courscio_professor where id = #{id,jdbcType=BIGINT}")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "email", property = "email", jdbcType = JdbcType.VARCHAR),
            @Result(column = "website", property = "website", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cv_url", property = "cvUrl", jdbcType = JdbcType.VARCHAR),
            @Result(column = "avatar", property = "avatar", jdbcType = JdbcType.VARCHAR),
            @Result(column = "intro", property = "intro", jdbcType = JdbcType.LONGVARCHAR),
            @Result(column = "note", property = "note", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_t", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_t", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
    })
    Professor findById(long id);
}
