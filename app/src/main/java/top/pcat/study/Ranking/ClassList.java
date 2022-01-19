package top.pcat.study.Ranking;

import android.graphics.Bitmap;

public class ClassList {
    private String name;
    private Bitmap imageId;
    private int ii;
    private int size;
    public ClassList(int i, String name, Bitmap imageId, int size ){
        this.name = name;
        this.imageId = imageId;
        this.ii = i;
        this.size = size;
    }
    public String getName() {
        return name;
    }
    public Bitmap getImageId() {
        return imageId;
    }
    public int getI(){ return ii; }
    public int getSize(){return size;}
}
