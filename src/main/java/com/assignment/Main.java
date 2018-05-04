package com.assignment;

import com.assignment.parser.TwitterFileParser;

public class Main {


    public static void main(String[] args) {
        {
            if (args.length < 1) {
                System.err.println("Invalid arguments count:" + args.length + "\n The command should consist of two arguments " +
                        "e.g.  userfile_path  tweetfile_path ");
                System.exit(1);
            }
            try {
                TwitterFileParser fileParser = new TwitterFileParser();
                fileParser.processUserAndTweetFiles(args[0].toString(), args[1].toString());

            } catch (Exception e) {
                System.out.println("Error processing program :\n");
                throw new RuntimeException(e);
            }
        }
    }
}
