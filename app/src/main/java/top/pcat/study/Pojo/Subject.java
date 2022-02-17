package top.pcat.study.Pojo;


import java.util.Date;

public class Subject {

  private long subjectId;
  private String subjectName;
  private String subjectFounder;
  private Date subjectTime;
  private String subjectPrivate;
  private String subjectDelete;
  private long subjectSize;
  private int subjectOfficial;
  private String subjectAdmin;

  public String getFounderName() {
    return founderName;
  }

  public void setFounderName(String founderName) {
    this.founderName = founderName;
  }

  public boolean isChooseFlag() {
    return chooseFlag;
  }

  public void setChooseFlag(boolean chooseFlag) {
    this.chooseFlag = chooseFlag;
  }

  private String founderName;
  private boolean chooseFlag;


  public long getSubjectId() {
    return subjectId;
  }

  public void setSubjectId(long subjectId) {
    this.subjectId = subjectId;
  }


  public String getSubjectName() {
    return subjectName;
  }

  public void setSubjectName(String subjectName) {
    this.subjectName = subjectName;
  }


  public String getSubjectFounder() {
    return subjectFounder;
  }

  public void setSubjectFounder(String subjectFounder) {
    this.subjectFounder = subjectFounder;
  }


  public Date getSubjectTime() {
    return subjectTime;
  }

  public void setSubjectTime(Date subjectTime) {
    this.subjectTime = subjectTime;
  }


  public String getSubjectPrivate() {
    return subjectPrivate;
  }

  public void setSubjectPrivate(String subjectPrivate) {
    this.subjectPrivate = subjectPrivate;
  }


  public String getSubjectDelete() {
    return subjectDelete;
  }

  public void setSubjectDelete(String subjectDelete) {
    this.subjectDelete = subjectDelete;
  }


  public long getSubjectSize() {
    return subjectSize;
  }

  public void setSubjectSize(long subjectSize) {
    this.subjectSize = subjectSize;
  }


  public int getSubjectOfficial() {
    return subjectOfficial;
  }

  public void setSubjectOfficial(int subjectOfficial) {
    this.subjectOfficial = subjectOfficial;
  }


  public String getSubjectAdmin() {
    return subjectAdmin;
  }

  public void setSubjectAdmin(String subjectAdmin) {
    this.subjectAdmin = subjectAdmin;
  }

}
