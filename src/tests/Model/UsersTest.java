package Model;

import org.junit.Test;

import java.security.NoSuchAlgorithmException;

public class UsersTest {

    @Test
    public void testPassword() throws NoSuchAlgorithmException {

        Users user = new Users();
        user.encryptPassword("12345");
    }
}