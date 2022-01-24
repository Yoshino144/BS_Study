package top.pcat.study.View;

public class ItemF2BangItem {
    private String bang_name;
    private int flag;

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    private int pos;
    public ItemF2BangItem(int pos,String bang_name, int num) {
        this.bang_name = bang_name;
        this.flag = num;
        this.pos = pos;
    }
    public String getName() {
        return bang_name;
    }
    public int getFlag(){return flag;}
}