package com.example.test_kao.bean.login;

public class LoginBean {

    /**
     * errno : 0
     * errmsg :
     * data : {"code":200,"token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiZjA0NDVkM2UtNDRhZi00N2ZmLTkzYzItMzMzZTEzNTVmODk1IiwicmFuZG9tIjoiMGw2cXR0NHlucSIsImlhdCI6MTYwOTM3NDk3OH0.6Hr2oNXhNVlfQst4huY-kvXsRFntWg_-Z4vm-snuMAo","userInfo":{"uid":"f0445d3e-44af-47ff-93c2-333e1355f895","username":"lczzz","nickname":"qwe","gender":0,"avatar":"http://2002aaa.oss-cn-beijing.aliyuncs.com/1609212170221.jpg","birthday":1234}}
     */

    private int errno;
    private String errmsg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * code : 200
         * token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiZjA0NDVkM2UtNDRhZi00N2ZmLTkzYzItMzMzZTEzNTVmODk1IiwicmFuZG9tIjoiMGw2cXR0NHlucSIsImlhdCI6MTYwOTM3NDk3OH0.6Hr2oNXhNVlfQst4huY-kvXsRFntWg_-Z4vm-snuMAo
         * userInfo : {"uid":"f0445d3e-44af-47ff-93c2-333e1355f895","username":"lczzz","nickname":"qwe","gender":0,"avatar":"http://2002aaa.oss-cn-beijing.aliyuncs.com/1609212170221.jpg","birthday":1234}
         */

        private int code;
        private String token;
        private UserInfoBean userInfo;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public UserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoBean userInfo) {
            this.userInfo = userInfo;
        }

        public static class UserInfoBean {
            /**
             * uid : f0445d3e-44af-47ff-93c2-333e1355f895
             * username : lczzz
             * nickname : qwe
             * gender : 0
             * avatar : http://2002aaa.oss-cn-beijing.aliyuncs.com/1609212170221.jpg
             * birthday : 1234
             */

            private String uid;
            private String username;
            private String nickname;
            private int gender;
            private String avatar;
            private int birthday;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public int getBirthday() {
                return birthday;
            }

            public void setBirthday(int birthday) {
                this.birthday = birthday;
            }
        }
    }
}
