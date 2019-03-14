package com.courscio.api.course;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Jiupeng Zhang
 * @since 03/14/2019
 */
@Mapper
@Repository
public interface CourseRepository {

    @Select({
            "select * from courscio_course where crn=#{crn}",
            "union (select * from courscio_course where cname like concat('%', #{keyword}, '%'))",
            "union (select * from courscio_course where title like concat('%', #{keyword}, '%'))",
            "union (select * from courscio_course where 'desc' like concat('%', #{keyword}, '%'))",
            "union (select a.id, school_id, semester, major, crn, cname, title, credit, score, 'desc', preq",
            "from courscio_course a, courscio_professor b, courscio_teaching c",
            "where a.id=c.course_id and b.id=c.professor_id and b.name like concat('%', #{keyword}, '%'))",
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "school_id", property = "schoolId", jdbcType = JdbcType.BIGINT),
            @Result(column = "semester", property = "semester", jdbcType = JdbcType.VARCHAR),
            @Result(column = "major", property = "major", jdbcType = JdbcType.VARCHAR),
            @Result(column = "crn", property = "crn", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cname", property = "cname", jdbcType = JdbcType.VARCHAR),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            @Result(column = "credit", property = "credit", jdbcType = JdbcType.TINYINT),
            @Result(column = "score", property = "courseId", jdbcType = JdbcType.TINYINT),
            @Result(column = "desc", property = "description", jdbcType = JdbcType.LONGVARCHAR),
            @Result(column = "preq", property = "prerequisite", jdbcType = JdbcType.LONGVARCHAR),
    })
    @Deprecated // fixme: query performance
    List<Course> listByCombinedKeyword(String keyword);
}
