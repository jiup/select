package com.courscio.api.teaching;

/**
 * @author Jiupeng Zhang
 * @since 04/13/2019
 */
public class TeachingResult {
    private Long id;
    private String cname;
    private String weekday;
    private String start_t;
    private String end_t;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
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
}
