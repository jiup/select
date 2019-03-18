package com.courscio.api.teaching;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

/**
 * @author Jiupeng Zhang
 * @since 03/14/2019
 */
public class Teaching implements Serializable {
	private Long id;
	private Long professor_id;
	private Long course_id;
	private String term;
	private Integer capacity;
	private String location;
	
	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProfessor_id() {
		return professor_id;
	}
	public void setProfessor_id(Long professor_id) {
		this.professor_id = professor_id;
	}
	public Long getCourse_id() {
		return course_id;
	}
	public void setCourse_id(Long course_id) {
		this.course_id = course_id;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public Integer getCapacity() {
		return capacity;
	}
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	
	
}
