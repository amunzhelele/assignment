# TwitterFeed

The objective is to display a simulated twitter feed for each user to the console. 
The program to simulate a twitter-like feed. 
The program read text two files. The first file contains a list of users and their followers. 
The second file contains tweets. Given the users, followers and tweets,
 

*******************This is the user file format ***************************

Ward follows Alan
Alan follows Martin
Ward follows Martin, Alan

******************* This is the tweet file format ***********************

Alan> If you have a procedure with 10 parameters, you probably missed some.
Ward> There are only two hard things in Computer Science: cache invalidation, naming things and off-by-1 errors.
Alan> Random numbers should not be generated with a method chosen at random.



******************* Invoking the program with user.txt and tweet.txt as arguments output the following :********


Alan
@Alan: If you have a procedure with 10 parameters, you probably missed some.
@Alan: Random numbers should not be generated with a method chosen at random.
Martin
Ward
@Alan: If you have a procedure with 10 parameters, you probably missed some.
@Ward: There are only two hard things in Computer Science: cache invalidation, naming things and off-by-1 errors.
@Alan: Random numbers should not be generated with a method chosen at random.


*******************Installing and packaging the application ***********************


1) cd assignmentTwitterFeed
2) mvn package -DskipTests;

go to the target folder
3) cd target

java -jar java -jar assignmentTwitterFeed-1.0-SNAPSHOT-jar-with-dependencies.jar  -upath_to user_file -t path_to_tweet_file
