package top.pcat.study.View;

public class Item {

    private String name;
    private int imageId;
    private boolean set;

    public Item(String name,boolean set){
        this.name = name;
        this.set = set;
       // this.imageId = imageId;

    }

    public String getName() {
        return name;
    }

    public boolean getSet() {
        return set;
    }

}
