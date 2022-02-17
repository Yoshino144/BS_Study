package top.pcat.study.TabCommunity;

public class Dynamic {
    private final String name;
    private final String nameTwo;

    public String getNameTwo() {
        return nameTwo;
    }

    public String getText() {
        return text;
    }

    private final String text;
    private final int imageId;

    public Dynamic(String name, int imageId,String nameTwo,String text){
        this.name = name;
        this.imageId = imageId;
this.nameTwo=nameTwo;
this.text=text;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}
