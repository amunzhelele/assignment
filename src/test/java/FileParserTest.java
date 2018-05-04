
import com.assignment.exception.MessageParserException;
import com.assignment.parser.TwitterFileParser;
import com.assignment.dto.UserDTO;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;

public class FileParserTest {


    @Test
    public void testReadFile() throws IOException, MessageParserException {

        String filePath = "/Users/Downloads/assignmentTwitterFeed/src/test/resources/user.txt";
        Path userFilePath = Paths.get(filePath);
        List<String> userDTOs = TwitterFileParser.INSTANCE.readFiles(userFilePath);
        Assert.assertEquals(3, userDTOs.size());
    }


      @Test
    public void testLoadingUsers () throws IOException, MessageParserException{
          LinkedHashMap<String, UserDTO> tweeterUsers = new LinkedHashMap<String, UserDTO>();

        String pathToUserFile = "/Users/assignmentTwitterFeed/src/test/resources/user.txt";
        String pathToTweetFile = "/Users/Downloads/assignmentTwitterFeed/src/test/resources/tweet.txt";

        TwitterFileParser.INSTANCE.processUserAndTweetFiles( pathToUserFile, pathToTweetFile);
      //  Assert.assertTrue();
    }





}
