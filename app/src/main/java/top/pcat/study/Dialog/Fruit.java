package top.pcat.study.Dialog;

public class Fruit {
    private String name;
    private int imageId;
    private String flag;
    public Fruit(String name, int imageId,String num) {
        this.name = name;
        this.imageId = imageId;
        this.flag = num;
    }
    public String getName() {
        return name;
    }
    public String getFlag(){return flag;}
    public int getImageId() {
        return imageId;
    }
}