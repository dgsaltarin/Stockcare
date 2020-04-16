package DB;

import Model.Users;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserDAOTest implements UserDAO {

    @Test
    public void getUserByName() {
        Users user  = getUserByName("daniel guillermo saltarin");
        Assert.assertEquals("daniel guillermo saltarin", user.getName());
    }
}