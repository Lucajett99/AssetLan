package utils;

public class TypeError {
    private String id;
    private String msg;

    public TypeError(String msg) {
        this.id = "";
        this.msg = msg;
    }

    public String getMsg() {
        return msg + " [" + this.id + "]";
    }
}
