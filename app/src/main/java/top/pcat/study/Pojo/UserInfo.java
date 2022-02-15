package top.pcat.study.Pojo;



public class UserInfo {

  private String id ="null";
  private String name="null";
  private String phone="null";
  private long sex;
  private java.sql.Timestamp birthday;
  private String city="null";
  private String school="null";
  private String college="null";
  private String major="null";
  private long grade=1000;
  private String position="null";
  private long delFlag;
  private String pic="null";
  private java.sql.Timestamp registrationTime;
  private String text="null";

  public UserInfo(){}

  public UserInfo(String uuid, String phone, String name) {
    this.id = uuid;
    this.phone = phone;
    this.name = name;
  }


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }


  public long getSex() {
    return sex;
  }

  public void setSex(long sex) {
    this.sex = sex;
  }


  public java.sql.Timestamp getBirthday() {
    return birthday;
  }

  public void setBirthday(java.sql.Timestamp birthday) {
    this.birthday = birthday;
  }


  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }


  public String getSchool() {
    return school;
  }

  public void setSchool(String school) {
    this.school = school;
  }


  public String getCollege() {
    return college;
  }

  public void setCollege(String college) {
    this.college = college;
  }


  public String getMajor() {
    return major;
  }

  public void setMajor(String major) {
    this.major = major;
  }


  public long getGrade() {
    return grade;
  }

  public void setGrade(long grade) {
    this.grade = grade;
  }


  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }


  public long getDelFlag() {
    return delFlag;
  }

  public void setDelFlag(long delFlag) {
    this.delFlag = delFlag;
  }


  public String getPic() {
    return pic;
  }

  public void setPic(String pic) {
    this.pic = pic;
  }


  public java.sql.Timestamp getRegistrationTime() {
    return registrationTime;
  }

  public void setRegistrationTime(java.sql.Timestamp registrationTime) {
    this.registrationTime = registrationTime;
  }


  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

}
