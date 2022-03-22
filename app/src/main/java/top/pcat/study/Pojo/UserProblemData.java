package top.pcat.study.Pojo;


public class UserProblemData {

  private long id;
  private String userId;
  private long subjectId;
  private long chapterId;
  private long problemId;
  private String answer;
  private long trueFlag;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }


  public long getSubjectId() {
    return subjectId;
  }

  public void setSubjectId(long subjectId) {
    this.subjectId = subjectId;
  }


  public long getChapterId() {
    return chapterId;
  }

  public void setChapterId(long chapterId) {
    this.chapterId = chapterId;
  }


  public long getProblemId() {
    return problemId;
  }

  public void setProblemId(long problemId) {
    this.problemId = problemId;
  }


  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }


  public long getTrueFlag() {
    return trueFlag;
  }

  public void setTrueFlag(long trueFlag) {
    this.trueFlag = trueFlag;
  }

}
