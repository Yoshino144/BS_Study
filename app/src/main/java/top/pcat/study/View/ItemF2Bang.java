package top.pcat.study.View;

public class ItemF2Bang {
    private String bang_name;
    private int flag;
    public ItemF2Bang( String bang_name, int num) {
        this.bang_name = bang_name;
        this.flag = num;
    }
    public String getName() {
        return bang_name;
    }
    public int getFlag(){return flag;}
}