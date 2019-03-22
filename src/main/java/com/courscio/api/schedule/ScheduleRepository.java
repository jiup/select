package com.courscio.api.schedule;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ScheduleRepository {
	@Select("select * from courscio_schedule where id = #{Id}")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "teaching_id", property = "teaching_id", jdbcType = JdbcType.BIGINT),
            @Result(column = "weekday", property = "weekday", javaType = Schedule.WeekDay.class, typeHandler = Schedule.WeekDay.Handler.class),
            @Result(column = "start_t", property = "start_t", jdbcType = JdbcType.VARCHAR),
            @Result(column = "end_t", property = "end_t", jdbcType = JdbcType.VARCHAR),
            @Result(column = "building", property = "building", jdbcType = JdbcType.VARCHAR),
            @Result(column = "room", property = "room", jdbcType = JdbcType.VARCHAR)
    })
    Schedule findById(long Id);
}
