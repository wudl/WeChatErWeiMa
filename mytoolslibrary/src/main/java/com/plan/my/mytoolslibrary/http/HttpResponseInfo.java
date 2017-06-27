package com.plan.my.mytoolslibrary.http;

/**
 * 请求返回数据类
 */
public class HttpResponseInfo {
    // 返回状态
    private int returnCode = -1;
    private String returnCodeStr = "";
    // 返回json串
    private String responseBodyString = "";
    // 返回消息
    private String returnMsg = "";
    private String result = "";
    private String blackWords = "";

    public HttpResponseInfo(int returnCode) {
        this.returnCode = returnCode;
    }

    public HttpResponseInfo(int returnCode, String returnMsg) {
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
    }

    public HttpResponseInfo(int returnCode, String returnMsg, String respBodyString) {
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
        this.responseBodyString = respBodyString;
    }

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnCodeStr() {
        return returnCodeStr;
    }

    public void setReturnCodeStr(String returnCodeStr) {
        this.returnCodeStr = returnCodeStr;
    }

    public String getResponseBodyString() {
        return responseBodyString;
    }

    public void setResponseBodyString(String responseBodyString) {
        this.responseBodyString = responseBodyString;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public void setBlackwords(String blackWords) {
        this.blackWords = blackWords;
    }

    public String getBlackwords() {
        // TODO Auto-generated method stub
        return blackWords;
    }

    public String getResult() {
        // TODO Auto-generated method stub
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
