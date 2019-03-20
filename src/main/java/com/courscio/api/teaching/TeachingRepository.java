package com.courscio.api.teaching;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TeachingRepository {
	@Select("select * from courscio_teaching where id = #{id,jdbcType=BIGINT}")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "professor_id", property = "professor_id", jdbcType = JdbcType.BIGINT),
            @Result(column = "course_id", property = "course_id", jdbcType = JdbcType.BIGINT),
            @Result(column = "term", property = "term", jdbcType = JdbcType.VARCHAR),
            @Result(column = "capaticy", property = "capacity", jdbcType = JdbcType.INTEGER),
            @Result(column = "location", property = "location", jdbcType = JdbcType.VARCHAR),

    })
    Teaching findById(long id);
}
