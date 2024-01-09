package com.myaem65training.core.models;

public class UserDetail {
    private String userID;
    private String userName;
    private String userPath;
    private String userMail;
    private String userPic;

    public UserDetail(String userId, String userProfilePath, String userEmail, String userProfilePic) {
        this.userID = userId;
        this.userMail = userEmail;
        this.userPic = userProfilePic;
        this.userPath = userProfilePath;
    }

    public String getUserPic() {
        return userPic;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPath() {
        return userPath;
    }

    public String getUserMail() {
        return userMail;
    }


    @Override
    public String toString() {
        return "UserDetail{" +
                "userID='" + userID + '\'' +
                ", userName='" + userName + '\'' +
                ", userPath='" + userPath + '\'' +
                ", userMail='" + userMail + '\'' +
                '}';
    }
}
