package top.pcat.study.Ranking;

public class GuanList {
    private String name;
    private int imageId;
    private int ii;
    private String mes_val;
    private String time_val;
    public GuanList(int i, String name, int imageId, String mes_val, String time_val) {
        this.name = name;
        this.imageId = imageId;
        this.ii = i;
        this.mes_val = mes_val;
        this.time_val = time_val;
    }
    public String getName() {
        return name;
    }
    public int getImageId() {
        return imageId;
    }
    public int getI(){ return ii; }
    public String getMes(){ return mes_val;}
    public String getTime(){return time_val;}

}
