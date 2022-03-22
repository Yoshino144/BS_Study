package top.pcat.study.WrongQuestion.Pojo;

public class Wrong {

    private int subjectId;
    private String subjectName;
    private int problemSize;

    public int getSubjectId() {
        return subjectId;
    }

    public Wrong(String subjectName, int problemSize) {
        this.subjectName = subjectName;
        this.problemSize = problemSize;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getProblemSize() {
        return problemSize;
    }

    public void setProblemSize(int problemSize) {
        this.problemSize = problemSize;
    }
}
