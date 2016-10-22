package cn.ucai.fulicenter.bean;

/**
 * Created by mac-yk on 2016/10/22.
 */

public class Result2 {

    /**
     * success : true
     * msg : 收藏成功
     */

    private boolean success;
    private String msg;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Result2{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                '}';
    }
}
