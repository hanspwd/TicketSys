package service.auth;

import dao.UserDao;
import model.User;

public class AuthService {

    public static boolean register(String name, String email, String password) throws Exception {
        UserDao userDao = new UserDao();
        User user = new User(name, email, password);

        User userExist = userDao.findByEmail(email);

        if (userExist != null) {
            throw new Exception("User already exist, try with other email.");
        }

        String hashPassword = HashPassword.hashPassword(user.getPassword());
        user.setPassword(hashPassword);

        userDao.create(user);

        return true;
    }

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
