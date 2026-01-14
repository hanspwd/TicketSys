package service.auth;

import dao.UserDao;
import model.User;

public class LoginService {

    public static LoginResult login(String email, String password) throws Exception {
        UserDao userDao = new UserDao();
        User user = userDao.findByEmail(email);
        LoginResult result = new LoginResult();
        String storedHashPasswordFromDB = "";

        if (user != null) {
            result.setUser(user);
            storedHashPasswordFromDB = user.getPassword();
        } else {
            throw new Exception("The email address entered could not be found. " + "\n" + "Please try again.");
        }

        if (HashPassword.checkPassword(password, storedHashPasswordFromDB)) {
            result.setAuthStatus(AuthStatus.SUCCESS);
        } else {
            throw new Exception("Password incorrect. Please try again");
        }
        return result;
    }
}


