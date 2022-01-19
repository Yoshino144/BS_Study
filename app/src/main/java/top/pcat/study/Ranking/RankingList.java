package top.pcat.study.Ranking;

public class RankingList {
    private String name;
    private int imageId;
    private int ii;
    public RankingList(int i,String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
        this.ii = i;
    }
    public String getName() {
        return name;
    }
    public int getImageId() {
        return imageId;
    }
    public int getI(){ return ii; }

}
