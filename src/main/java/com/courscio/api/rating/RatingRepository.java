package com.courscio.api.rating;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RatingRepository {
	@Select("select * from courscio_rating where id = #{Id}")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "user_id", property = "user_id", jdbcType = JdbcType.BIGINT),
            @Result(column = "teaching_id", property = "teaching_id", jdbcType = JdbcType.BIGINT),
            @Result(column = "score", property = "score", jdbcType = JdbcType.INTEGER),
            @Result(column = "comment", property = "comment", jdbcType = JdbcType.VARCHAR),
    })
    List<Rating> findById(long Id);
	
	@Insert({
        "insert into courscio_rating (id, user_id, teaching_id, score, comment)",
        "values (#{id,jdbcType=BIGINT}, #{user_id,jdbcType=BIGINT}, ",
        "#{teaching_id,jdbcType=BIGINT}, #{score,jdbcType=INTEGER}, ",
        "#{comment,jdbcType=VARCHAR})"
	})
	@SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = long.class)
	int insert(Rating rating);
	
	@Update({
        "update courscio_rating",
        "set score = #{score}",
        "where user_id = #{user_id,jdbcType=BIGINT} and teaching_id = #{teaching_id,jdbcType=BIGINT}"
	})
	int updateScore(@Param("user_id") long user_id, @Param("teaching_id") long teaching_id, @Param("score") Integer score);
	
	@Update({
        "update courscio_rating",
        "set comment = #{comment}",
        "where user_id = #{user_id,jdbcType=BIGINT} and teaching_id = #{teaching_id,jdbcType=BIGINT}"
	})
	int updateComment(@Param("user_id") long user_id, @Param("teaching_id") long teaching_id, @Param("comment") String comment);
}
