package com.courscio.api.teaching;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

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

	@Select({
            "<script>",
            "select courscio_teaching.id, cname, weekday, start_t, end_t from courscio_teaching",
            "left join courscio_schedule on courscio_teaching.id = courscio_schedule.teaching_id",
            "left join courscio_course on courscio_teaching.course_id = courscio_course.id",
            "where courscio_teaching.id in",
            "<foreach item='it' index='index' collection='ids' open='(' separator=',' close=')'>#{it}</foreach>",
            "</script>",
    })
	List<TeachingResult> findByIds(@Param("ids") Long[] ids);
}
