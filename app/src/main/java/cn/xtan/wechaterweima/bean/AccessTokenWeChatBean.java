package cn.xtan.wechaterweima.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * wechat小程序生成二维码的逻辑bean
 *
 * Created by wudl on 2017/6/27 09:53
 * <p>
 * 邮箱 770616344@qq.com
 */

public class AccessTokenWeChatBean implements Parcelable {

    private String access_token;
    private int expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(access_token);
        dest.writeInt(expires_in);

    }

    public static final Creator<AccessTokenWeChatBean> CREATOR = new Creator<AccessTokenWeChatBean>() {
        public AccessTokenWeChatBean createFromParcel(Parcel in) {
            AccessTokenWeChatBean account = new AccessTokenWeChatBean();
            account.access_token = in.readString();
            account.expires_in = in.readInt();
            return account;
        }

        public AccessTokenWeChatBean[] newArray(int size) {
            return new AccessTokenWeChatBean[size];
        }
    };

}