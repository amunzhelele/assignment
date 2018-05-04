package com.assignment.parser;

import com.assignment.dto.TwitterMessengerList;
import com.assignment.dto.UserDTO;
import com.assignment.dto.UserTweetDTO;
import com.assignment.exception.MessageParserException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;


/**
 * Created by abigailmunzhelele on 5/2/18.
 */
public class TwitterFileParser {


    public TwitterFileParser() {
    }

    public static TwitterFileParser INSTANCE;

    static {
        INSTANCE = getINSTANCE();
    }

    private static TwitterFileParser getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new TwitterFileParser();
        }
        return INSTANCE;
    }

    public LinkedHashMap<String, UserDTO> tweeterUsers = new LinkedHashMap<String, UserDTO>();

    public void processUserAndTweetFiles(String userfile, String tweetFile) throws MessageParserException {

        try {
            Path userFilePath = Paths.get(userfile);
            Path tweetsFilePath = Paths.get(tweetFile);

            List<String> userList = readFiles(userFilePath);
            List<String> userTweetsList = readFiles(tweetsFilePath);

            if ((!userList.isEmpty()) && (!userTweetsList.isEmpty())) {
                List<UserDTO> userDTOS = readUserList(userList);
                List<UserTweetDTO> tweetDTOS = readUserTweets(userTweetsList);
                groupUserTweetsMessage(userDTOS, tweetDTOS);
                outPutMessage();
            } else {
                throw new MessageParserException("user and tweet files are empty");
            }

        } catch (Exception e) {
            if (e instanceof MessageParserException)
                throw (MessageParserException) e;
            throw new RuntimeException(e);
        }
    }


    public List<String> readFiles(Path userFilePath) throws MessageParserException, IOException {

        List<String> userFilelines = new ArrayList<String>();
        try {
            Stream<String> stream = Files.lines(userFilePath);
            try {
                userFilelines = (List) stream.sorted().collect(toList());
            } finally {
                stream.close();
            }

        } catch (IOException e) {
            throw new MessageParserException(e.getMessage());
        }
        return userFilelines;
    }


    public List<UserDTO> readUserList(List<String> userList) {

        List<UserDTO> allUserDTOs = new TwitterMessengerList<UserDTO>();
        List<UserDTO> userDTOList = new ArrayList<UserDTO>();

        for (String line : userList) {
            try {
                if (hasFollowText(line)) {
                    String[] lineArray = line.split("follows");
                    String userNames = lineArray[1].trim();
                    String followerName = lineArray[0].trim();

                    if (!userNames.trim().contains(",")) {
                        UserDTO user = new UserDTO(userNames.trim());
                        user.addFollower(new UserDTO(followerName.trim()));
                        userDTOList.add(user);
                        userDTOList.add(new UserDTO(followerName));

                    } else {
                        addFollowerUsers(userNames, followerName, userDTOList);
                    }
                }
                allUserDTOs.addAll(userDTOList);
                Collections.sort(allUserDTOs, new Comparator<UserDTO>() {
                    public int compare(UserDTO o1, UserDTO o2) {
                        return o1.getUsername().compareTo(o2.getUsername());
                    }
                });


            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return allUserDTOs;
    }

    private void addFollowerUsers(String userNames, String followerName, List<UserDTO> userDTOList) {

        String[] usersNamesArray = userNames.split(",");
        userDTOList.add(new UserDTO(followerName));
        for (String userName : usersNamesArray) {
            if (userName.trim().length() != 0) {
                UserDTO currentUser = new UserDTO(userName.trim());
                currentUser.addFollower(new UserDTO(followerName.trim()));
                userDTOList.add(currentUser);
            }
        }
    }

    public List<UserTweetDTO> readUserTweets(List<String> userTweetsList) throws MessageParserException {

        List<UserTweetDTO> dtoList = new LinkedList<UserTweetDTO>();
        for (String line : userTweetsList) {
            try {
                int indexOfDeliminator = line.indexOf(">");
                if (indexOfDeliminator < 2) {
                    throw new MessageParserException("\nError reading line : " + line + "  Message should have delimiter of >");
                }
                String userName = line.substring(0, indexOfDeliminator).trim();
                String message = line.substring(indexOfDeliminator + 1, line.length());
                UserTweetDTO dto = new UserTweetDTO(userName, message);
                dtoList.add(dto);

            } catch (Exception e) {
                if (e instanceof MessageParserException)
                    throw (MessageParserException) e;
                throw new RuntimeException(e);
            }
        }
        return dtoList;
    }


    private boolean hasFollowText(String line) {

        String[] parts = line.split("\\s+");
        String follower = parts[2];

        if (follower.indexOf(",") > 0) {
            return true;
        } else return false;
    }


    public void outPutMessage() {

        for (String userName : tweeterUsers.keySet()) {
            System.out.println(userName);
            Set<UserTweetDTO> uniqueValues = new HashSet<UserTweetDTO>();
            if (tweeterUsers.get(userName).getTweets() != null) {
                for (UserTweetDTO tweetDTO : tweeterUsers.get(userName).getTweets()) {
                    if (uniqueValues.add(tweetDTO)) {
                        String s = "@" +
                                tweetDTO.getUserName() + " " + tweetDTO.getTweetMessage();
                        System.out.println(s);
                    }
                }
            }
        }
    }

    /**
     * @param userDTOs
     * @param tweetDTOs
     */
    private void groupUserTweetsMessage(List<UserDTO> userDTOs, List<UserTweetDTO> tweetDTOs) {

        for (UserDTO userDTO : userDTOs) {
            tweeterUsers.put(userDTO.getUsername(), userDTO);

        }
        for (UserDTO userDTO : tweeterUsers.values()) {
            if (userDTO.getFollowers() != null) {
                int bound = userDTO.getFollowers().size();
                for (int index = 0; index < bound; index++) {
                    UserDTO follower = userDTO.getFollowers().get(index);

                    if (tweeterUsers.containsValue(follower)) {
                        userDTO.getFollowers().remove(index);
                        userDTO.getFollowers().add(index, tweeterUsers.get(follower.getUsername()));
                    }
                }
            }
        }
        for (UserTweetDTO dto : tweetDTOs) {
            if (dto != null) {
                UserDTO currentUser = tweeterUsers.get(dto.getUserName());
                if (currentUser != null) {
                    currentUser.addTweets(dto);
                    if (currentUser.getFollowers() != null)
                        for (UserDTO follower : currentUser.getFollowers()) {
                            follower.addTweets(dto);
                        }
                }
            }
        }
    }
}


