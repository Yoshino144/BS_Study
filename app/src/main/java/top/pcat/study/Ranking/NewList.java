package top.pcat.study.Ranking;

public class NewList {
    private String name;
    private int imageId;
    private int ii;
    private String reason;
    public NewList(int i, String name, int imageId, String size ){
        this.name = name;
        this.imageId = imageId;
        this.ii = i;
        this.reason = size;
    }
    public String getName() {
        return name;
    }
    public int getImageId() {
        return imageId;
    }
    public int getI(){ return ii; }
    public String getReason(){return reason;}
}
