package top.pcat.study.Pojo;


import java.util.Date;


public class Clasp {

    private String classId;
    private String className;
    private String classAdminId;
    private Date createTime;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassAdminId() {
        return classAdminId;
    }

    public void setClassAdminId(String classAdminId) {
        this.classAdminId = classAdminId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
