package top.pcat.study.View;

public class ItemFragment2 {
    private String name;
    private int imageId;
    private String subject_id;
    private String flag;
    public ItemFragment2(String subject_id,String name, int imageId, String num) {
        this.subject_id = subject_id;
        this.name = name;
        this.imageId = imageId;
        this.flag = num;
    }
    public String getSubject_id()

    {
        return subject_id;
    }
    public String getName() {
        return name;
    }
    public String getFlag(){return flag;}
    public int getImageId() {
        return imageId;
    }
}