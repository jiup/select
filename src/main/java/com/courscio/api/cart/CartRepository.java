package com.courscio.api.cart;

import com.courscio.api.schedule.Schedule;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Jiupeng Zhang
 * @since 03/14/2019
 */
@Mapper
@Repository
public interface CartRepository {

    @Select("select * from courscio_cart where id = #{id}")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.BIGINT),
            @Result(column = "course_id", property = "courseId", jdbcType = JdbcType.BIGINT),
            @Result(column = "type", property = "type", javaType = CartItem.Type.class, typeHandler = CartItem.Type.Handler.class),
    })
    CartItem getItem(Long id);

    @Select("select * from courscio_cart where user_id = #{userId}")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.BIGINT),
            @Result(column = "course_id", property = "courseId", jdbcType = JdbcType.BIGINT),
            @Result(column = "type", property = "type", javaType = CartItem.Type.class, typeHandler = CartItem.Type.Handler.class),
    })
    List<CartItem> listByUserId(long userId);

    @Insert({
            "insert into courscio_cart (id, user_id, course_id, type)",
            "values (#{id,jdbcType=BIGINT}, #{userId,jdbcType=BIGINT}, ",
            "#{courseId,jdbcType=BIGINT}, #{type})"
    })
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = long.class)
    int insert(CartItem cartItem);

    @Update({
            "update courscio_cart",
            "set type = #{type}",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int updateTypeById(@Param("id") long id, @Param("type") CartItem.Type type);

    @Delete({
            "delete from courscio_cart where id = #{id}"
    })
    int deleteById(long id);

    @Select({"select * from courscio_course c inner join courscio_schedule s on c.id = s.teaching_id",
            "inner join courscio_cart a on a.course_id = s.teaching_id",
             "where a.id = #{id}",
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "teaching_id", property = "teachingId", jdbcType = JdbcType.BIGINT),
            @Result(column = "weekday", property = "weekDay", javaType = Schedule.WeekDay.class, typeHandler = Schedule.WeekDay.Handler.class),
            @Result(column = "start_t", property = "start_t", jdbcType = JdbcType.VARCHAR),
            @Result(column = "end_t", property = "end_t", jdbcType = JdbcType.VARCHAR),
            @Result(column = "building", property = "building", jdbcType = JdbcType.VARCHAR),
            @Result(column = "room", property = "room", jdbcType = JdbcType.VARCHAR)
    })
    List<Schedule> getSchedule(Long id);

    @Select({"select * from courscio_schedule",
            "where courscio_schedule.teaching_id = #{teaching_id}",
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "teaching_id", property = "teachingId", jdbcType = JdbcType.BIGINT),
            @Result(column = "weekday", property = "weekDay", javaType = Schedule.WeekDay.class, typeHandler = Schedule.WeekDay.Handler.class),
            @Result(column = "start_t", property = "start_t", jdbcType = JdbcType.VARCHAR),
            @Result(column = "end_t", property = "end_t", jdbcType = JdbcType.VARCHAR),
            @Result(column = "building", property = "building", jdbcType = JdbcType.VARCHAR),
            @Result(column = "room", property = "room", jdbcType = JdbcType.VARCHAR)
    })
    List<Schedule> getNewSchedule(Long teaching_id);


}
