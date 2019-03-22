package com.courscio.api.course;

import com.courscio.api.schedule.Schedule;
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
    List<Course> listByCombinedKeyword(String keyword);



    @Select({
            "select * from courscio_course inner join courscio_teaching on courscio_course.id = courscio_teaching.course_id",
            "inner join courscio_schedule on teaching_id",
            "where semester = #{semester} and major = #{major} and credit = #{credit} and weekday in (#{weekdays})"
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
            @Result(column = "weekday", property = "weekday", javaType = Schedule.WeekDay.class, typeHandler = Schedule.WeekDay.Handler.class),
            @Result(column = "start_t", property = "start_t", jdbcType = JdbcType.VARCHAR),
            @Result(column = "end_t", property = "end_t", jdbcType = JdbcType.VARCHAR),
    })
    List<Course> listByFilters(String semester, String major, Short credit,  List<String> weekdays);
}
