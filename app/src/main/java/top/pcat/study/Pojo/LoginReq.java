package top.pcat.study.Pojo;


/**
 * @program: study
 * @description:
 * @author: PCat
 * @create: 2022-02-14 23:23
 **/
public class LoginReq {
    private String token;

    @Override
    public String toString() {
        return "{ \"token\":\"" + token +"\",\"uuid\":\""  + uuid +"\"}";
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    private String uuid;

    public LoginReq(String uuid, String token) {
        this.token = token;
        this.uuid = uuid;
    }
}
