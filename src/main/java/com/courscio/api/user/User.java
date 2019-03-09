package com.courscio.api.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.ibatis.type.EnumTypeHandler;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.StringJoiner;

/**
 * @author Jiupeng Zhang
 * @since 03/07/2019
 */
public class User implements Serializable {
    private Long id;

    private Type type;

    private String email;

    private String password;

    private String nickname;

    private String avatar;

    private String firstName;

    private String lastName;

    private String intro;

    private Boolean sex;

    private ZonedDateTime dateOfBirth;

    private String zip;

    private Long schoolId;

    private String schoolName;

    private Degree degree;

    private Grade grade;

    private Short classYear;

    private Integer failureCount;

    private Integer loginCount;

    private ZonedDateTime createTime;

    private ZonedDateTime updateTime;

    public enum Type {
        student("Student"), professor("Professor"), others("Others");

        String displayName;

        Type(String displayName) {
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

    public enum Degree {
        bachelor("Bachelor"), masters("Masters"), phd("PHD"), others("Others");

        String displayName;

        Degree(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }

        public static class Handler extends EnumTypeHandler<Degree> {
            public Handler(Class<Degree> type) {
                super(type);
            }
        }
    }

    public enum Grade {
        freshman("Freshman"), sophomore("Sophomore"), junior("Junior"), senior("Senior");

        String displayName;

        Grade(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }

        public static class Handler extends EnumTypeHandler<Grade> {
            public Handler(Class<Grade> type) {
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public ZonedDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(ZonedDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Short getClassYear() {
        return classYear;
    }

    public void setClassYear(Short classYear) {
        this.classYear = classYear;
    }

    public Integer getFailureCount() {
        return failureCount;
    }

    public void setFailureCount(Integer failureCount) {
        this.failureCount = failureCount;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("type=" + type)
                .add("email='" + email + "'")
                .add("nickname='" + nickname + "'")
                .add("avatar='" + avatar + "'")
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("intro='" + intro + "'")
                .add("sex=" + sex)
                .add("dateOfBirth=" + dateOfBirth)
                .add("zip='" + zip + "'")
                .add("schoolId=" + schoolId)
                .add("schoolName='" + schoolName + "'")
                .add("degree=" + degree)
                .add("grade=" + grade)
                .add("classYear=" + classYear)
                .add("failureCount=" + failureCount)
                .add("loginCount=" + loginCount)
                .add("createTime=" + createTime)
                .add("updateTime=" + updateTime)
                .toString();
    }
}
