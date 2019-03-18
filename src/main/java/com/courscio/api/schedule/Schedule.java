package com.courscio.api.schedule;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.StringJoiner;

import org.apache.ibatis.type.EnumTypeHandler;
import org.springframework.data.annotation.Id;

import com.courscio.api.user.User;
import com.courscio.api.user.User.Type;

/**
 * @author Jiupeng Zhang
 * @since 03/14/2019
 */
public class Schedule implements Serializable {
	private Long id;
	private Long teachingId;
	private WeekDay weekDay;
	private LocalTime start_t;
	private LocalTime end_t;
	private String building;
	private String room;
	
	
    public enum WeekDay {
        MON("MON"), TUE("TUE"), WEN("WEN"), THU("THU"), FRI("FRI"), SAT("SAT"), SUN("SUN") ;

        String displayName;

        WeekDay(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }

        public static class Handler extends EnumTypeHandler<Type> {
            public Handler(Class<Type> type) {
                super(type);
            }
        }
    }

    
    @Id
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getTeachingId() {
		return teachingId;
	}


	public void setTeachingId(Long teachingId) {
		this.teachingId = teachingId;
	}


	public WeekDay getWeekDay() {
		return weekDay;
	}


	public void setWeekDay(WeekDay weekDay) {
		this.weekDay = weekDay;
	}


	public LocalTime getStart_t() {
		return start_t;
	}


	public void setStart_t(LocalTime start_t) {
		this.start_t = start_t;
	}


	public LocalTime getEnd_t() {
		return end_t;
	}


	public void setEnd_t(LocalTime end_t) {
		this.end_t = end_t;
	}


	public String getBuilding() {
		return building;
	}


	public void setBuilding(String building) {
		this.building = building;
	}


	public String getRoom() {
		return room;
	}


	public void setRoom(String room) {
		this.room = room;
	}
	
	@Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("teachingId=" + teachingId)
                .add("weekDay='" + weekDay + "'")
                .add("start_t='" + start_t + "'")
                .add("end_t='" + end_t + "'")
                .add("building='" + building + "'")
                .add("room='" + room + "'")
                .toString();
    }

    
	
}
