package com.example.test_kao.bean.login;

public class TokenBean {

    /**
     * errno : 0
     * errmsg :
     * data : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiZjA0NDVkM2UtNDRhZi00N2ZmLTkzYzItMzMzZTEzNTVmODk1IiwicmFuZG9tIjoiN3N3MndjZmZxZiIsImlhdCI6MTYwOTY3MTEwNn0.nv7KeqS1_NcpeDtjC7zqHF31PpAipY3OgMKV1dqJDWQ
     */

    private int errno;
    private String errmsg;
    private String data;

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
