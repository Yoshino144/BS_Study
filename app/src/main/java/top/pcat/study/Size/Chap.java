package top.pcat.study.Size;

public class Chap {
    private String name;
    private String chapter_id;
    private int imageId;
    public Chap(String chapter_id,String name, int imageId) {
        this.chapter_id = chapter_id;
        this.name = name;
        this.imageId = imageId;
    }
    public String getChapter_id(){return chapter_id;}
    public String getName() {
        return name;
    }
    public int getImageId() {
        return imageId;
    }
}
