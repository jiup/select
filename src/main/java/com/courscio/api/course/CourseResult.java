package com.courscio.api.course;

import com.courscio.api.schedule.Schedule;

public class CourseResult {
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getCrn() {
		return crn;
	}
	public void setCrn(String crn) {
		this.crn = crn;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Short getCredit() {
		return credit;
	}
	public void setCredit(Short credit) {
		this.credit = credit;
	}
	public Short getScore() {
		return score;
	}
	public void setScore(Short score) {
		this.score = score;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPrerequisite() {
		return prerequisite;
	}
	public void setPrerequisite(String prerequisite) {
		this.prerequisite = prerequisite;
	}
	public String getWeekday() {
		return weekday;
	}
	public void setWeekday(Schedule.WeekDay weekday) {
		this.weekday = weekday.getDisplayName();
	}
	public String getStart_t() {
		return start_t;
	}
	public void setStart_t(String start_t) {
		this.start_t = start_t;
	}
	public String getEnd_t() {
		return end_t;
	}
	public void setEnd_t(String end_t) {
		this.end_t = end_t;
	}
	private Long id;
    private Long schoolId;
    private String semester;
    private String major;
    private String crn;
    private String cname;
    private String title;
    private Short credit;
    private Short score;
    private String description;
    private String prerequisite;
    private String weekday;
    private String start_t;
    private String end_t;
    private String key;
    
	public String getKey() {
		return key;
	}
	public void setKey(Long key) {
		this.key = key.toString()+this.weekday;
	}

	private String location;
	private String name;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
