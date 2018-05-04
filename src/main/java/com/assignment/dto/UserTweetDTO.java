package com.assignment.dto;

/**
 * Created by abigailmunzhelele on 5/2/18.
 */

public class UserTweetDTO {

    private String userName;
    private String tweetMessage;


    public UserTweetDTO(String userName, String tweetMessage) {
        this.userName = userName;
        this.tweetMessage = tweetMessage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTweetMessage() {
        return tweetMessage;
    }

    public void setTweetMessage(String tweetMessage) {
        this.tweetMessage = tweetMessage;
    }

    @Override
    public String toString() {
        return "UserTweetDTO{" +
                "userName='" + userName + '\'' +
                ", tweetMessage='" + tweetMessage + '\'' +
                '}';
    }
}
