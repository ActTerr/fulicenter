package cn.ucai.fulicenter.bean;

import java.io.Serializable;

/**
 * Created by mac-yk on 2016/10/21.
 */

public class UserBean implements Serializable{
    /**
     * muserName : a13214151
     * muserNick : fafafa
     * mavatarId : 232
     * mavatarPath : user_avatar
     * mavatarSuffix : null
     * mavatarType : 0
     * mavatarLastUpdateTime : 1477036661917
     */

    private String muserName;
    private String muserNick;
    private int mavatarId;
    private String mavatarPath;
    private String mavatarSuffix;
    private int mavatarType;
    private String mavatarLastUpdateTime;

    public String getMuserName() {
        return muserName;
    }

    public void setMuserName(String muserName) {
        this.muserName = muserName;
    }

    public String getMuserNick() {
        return muserNick;
    }

    public void setMuserNick(String muserNick) {
        this.muserNick = muserNick;
    }

    public int getMavatarId() {
        return mavatarId;
    }

    public void setMavatarId(int mavatarId) {
        this.mavatarId = mavatarId;
    }

    public String getMavatarPath() {
        return mavatarPath;
    }

    public void setMavatarPath(String mavatarPath) {
        this.mavatarPath = mavatarPath;
    }

    public String getMavatarSuffix() {
        return mavatarSuffix;
    }

    public void setMavatarSuffix(String mavatarSuffix) {
        this.mavatarSuffix = mavatarSuffix;
    }

    public int getMavatarType() {
        return mavatarType;
    }

    public void setMavatarType(int mavatarType) {
        this.mavatarType = mavatarType;
    }

    public String getMavatarLastUpdateTime() {
        return mavatarLastUpdateTime;
    }

    public void setMavatarLastUpdateTime(String mavatarLastUpdateTime) {
        this.mavatarLastUpdateTime = mavatarLastUpdateTime;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "muserName='" + muserName + '\'' +
                ", muserNick='" + muserNick + '\'' +
                ", mavatarId=" + mavatarId +
                ", mavatarPath='" + mavatarPath + '\'' +
                ", mavatarSuffix=" + mavatarSuffix +
                ", mavatarType=" + mavatarType +
                ", mavatarLastUpdateTime='" + mavatarLastUpdateTime + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserBean userBean = (UserBean) o;

        if (muserName != null ? !muserName.equals(userBean.muserName) : userBean.muserName != null)
            return false;
        return mavatarLastUpdateTime != null ? mavatarLastUpdateTime.equals(userBean.mavatarLastUpdateTime) : userBean.mavatarLastUpdateTime == null;

    }

    @Override
    public int hashCode() {
        int result = muserName != null ? muserName.hashCode() : 0;
        result = 31 * result + (mavatarLastUpdateTime != null ? mavatarLastUpdateTime.hashCode() : 0);
        return result;
    }
}
