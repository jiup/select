package com.courscio.api.rating;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

/**
 * @author Jiupeng Zhang
 * @since 03/14/2019
 */
public class Rating implements Serializable {
	private Long id;
	private Long user_id;
	private Long teaching_id;
	private Integer score;
	private String comment;
	
	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public Long getTeaching_id() {
		return teaching_id;
	}
	public void setTeaching_id(Long course_id) {
		this.teaching_id = course_id;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
