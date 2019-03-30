package com.courscio.api.course;

import com.courscio.api.schedule.Schedule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
//            "union (select a.id, school_id, semester, major, crn, cname, title, credit, score, 'desc', preq, weekday, start_t, end_t, a.id, name, location",
            "union (select *",
            "from courscio_course a, courscio_professor b, courscio_teaching c, courscio_schedule d",
            "where a.id=c.course_id and b.id=c.professor_id and d.teaching_id = c.id and b.name like concat('%', #{keyword}, '%'))",
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "school_id", property = "schoolId", jdbcType = JdbcType.BIGINT),
            @Result(column = "semester", property = "semester", jdbcType = JdbcType.VARCHAR),
            @Result(column = "major", property = "major", jdbcType = JdbcType.VARCHAR),
            @Result(column = "crn", property = "crn", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cname", property = "cname", jdbcType = JdbcType.VARCHAR),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            @Result(column = "credit", property = "credit", jdbcType = JdbcType.TINYINT),
            @Result(column = "score", property = "score", jdbcType = JdbcType.TINYINT),
            @Result(column = "desc", property = "description", jdbcType = JdbcType.LONGVARCHAR),
            @Result(column = "preq", property = "prerequisite", jdbcType = JdbcType.LONGVARCHAR),
            @Result(column = "weekday", property = "weekday", javaType = Schedule.WeekDay.class, typeHandler = Schedule.WeekDay.Handler.class),
            @Result(column = "start_t", property = "start_t", jdbcType = JdbcType.VARCHAR),
            @Result(column = "end_t", property = "end_t", jdbcType = JdbcType.VARCHAR),
            @Result(column = "id", property = "key", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "location", property = "location", jdbcType = JdbcType.VARCHAR),
    })
    List<CourseResult> listByCombinedKeyword(String keyword);



    @Select({
    	"<script>"+
                "<choose>"+
                "<when test = \"credit != null\">"+
    			"select * from courscio_course c inner join courscio_teaching t on c.id = t.course_id",
    			"inner join courscio_schedule s on s.teaching_id = t.id",
    			"where semester = #{semester} and major = #{major} and credit = #{credit} and weekday in "+
    					"<foreach item='item' index='index' collection='scanWeekday' open='(' separator=',' close=')'>" +
    					"#{item}"+
    					"</foreach>"+
                "</when>"+
                "<otherwise>"+
                "select * from courscio_course c inner join courscio_teaching t on c.id = t.course_id",
                "inner join courscio_schedule s on s.teaching_id = t.id",
                "where semester = #{semester} and major = #{major} and weekday in "+
                        "<foreach item='item' index='index' collection='scanWeekday' open='(' separator=',' close=')'>" +
                        "#{item}"+
                        "</foreach>"+
                "</otherwise>"+
                "</choose>"+
        "</script>"

//        "<script>"+
//    			"select * from courscio_course c inner join courscio_teaching t on c.id = t.course_id",
//    			"inner join courscio_schedule s on s.teaching_id = t.id",
//                "inner join courscio_professor p on p.id = t.professor_id",
//    			"where semester = #{semester} and major = #{major} and credit = #{credit} and weekday in "+
//    					"<foreach item='item' index='index' collection='scanWeekday' open='(' separator=',' close=')'>" +
//    					"#{item}"+
//    					"</foreach>"+
//    	"</script>"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "school_id", property = "schoolId", jdbcType = JdbcType.BIGINT),
            @Result(column = "semester", property = "semester", jdbcType = JdbcType.VARCHAR),
            @Result(column = "major", property = "major", jdbcType = JdbcType.VARCHAR),
            @Result(column = "crn", property = "crn", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cname", property = "cname", jdbcType = JdbcType.VARCHAR),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            @Result(column = "credit", property = "credit", jdbcType = JdbcType.TINYINT),
            @Result(column = "score", property = "score", jdbcType = JdbcType.TINYINT),
            @Result(column = "desc", property = "description", jdbcType = JdbcType.LONGVARCHAR),
            @Result(column = "preq", property = "prerequisite", jdbcType = JdbcType.LONGVARCHAR),
            @Result(column = "weekday", property = "weekday", javaType = Schedule.WeekDay.class, typeHandler = Schedule.WeekDay.Handler.class),
            @Result(column = "start_t", property = "start_t", jdbcType = JdbcType.VARCHAR),
            @Result(column = "end_t", property = "end_t", jdbcType = JdbcType.VARCHAR),
            @Result(column = "id", property = "key", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "location", property = "location", jdbcType = JdbcType.VARCHAR),
    })
    List<CourseResult> listByFilters(String semester, String major, Short credit,  @Param("scanWeekday") List<String> weekdays);
}
