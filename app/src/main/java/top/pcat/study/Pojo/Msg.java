package top.pcat.study.Pojo;

public class Msg {


    // 状态码
    private int status;
    // 提示信息
    private String message;

    // 封装有效数据
    private Object data ;

    public static Msg success() {
        Msg result = new Msg();
        result.setStatus(200);
        return result;
    }

    public static Msg fail() {
        Msg result = new Msg();
        result.setStatus(400);
        return result;
    }

    public static Msg noPermission() {
        Msg result = new Msg();
        result.setStatus(401);
        return result;
    }

    public static Msg error() {
        Msg result = new Msg();
        result.setStatus(500);
        result.setMessage("error");
        return result;
    }

    public static Msg code(int code){
        Msg result = new Msg();
        result.setStatus(code);
        result.setMessage("exception");
        return result;
    }

    public Msg mes(String mes) {
        this.message = mes;
        return this;
    }//

    public Msg data(Object data) {
        this.data = data;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
