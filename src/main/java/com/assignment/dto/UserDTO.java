package com.assignment.dto;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by abigailmunzhelele on 5/2/18.
 */
public class UserDTO {

    private String username;
    private List<UserDTO> followers;
    private List<UserTweetDTO> tweets;

    public UserDTO(String username) {
        this.username = username;
    }

    public UserDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<UserTweetDTO> getTweets() {
        return tweets;
    }

    public void setTweets(List<UserTweetDTO> tweets) {
        this.tweets = tweets;
    }

    public List<UserDTO> getFollowers() {
        return followers;
    }

    public void setFollowers(List<UserDTO> followers) {
        this.followers = followers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDTO userDTO = (UserDTO) o;

        if (username != null ? !username.equals(userDTO.username) : userDTO.username != null) return false;
        if (followers != null ? !followers.equals(userDTO.followers) : userDTO.followers != null) return false;
        return tweets != null ? tweets.equals(userDTO.tweets) : userDTO.tweets == null;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (followers != null ? followers.hashCode() : 0);
        result = 31 * result + (tweets != null ? tweets.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", followers=" + followers +
                ", tweets=" + tweets +
                '}';
    }


    public void addFollower(UserDTO userDTO) {
        if (getFollowers() == null)
            setFollowers(new TwitterMessengerList<UserDTO>());
        getFollowers().add(userDTO);
    }


    public void copyUserFollowers(UserDTO userDTO) {
        if (userDTO.getFollowers() != null) {
            for (UserDTO follower : userDTO.getFollowers()) {
                if (!this.getFollowers().contains(follower))
                    this.addFollower(follower);
            }
        }
    }

    public void addTweets(UserTweetDTO message) {
        if (getTweets() == null)
            setTweets(new LinkedList<UserTweetDTO>());
        getTweets().add(message);
    }
}
