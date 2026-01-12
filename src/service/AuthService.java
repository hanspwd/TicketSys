package service;

import dao.UserDao;
import model.User;

public class AuthService {

    public static boolean register(String name, String email, String password) throws Exception {
        UserDao userDao = new UserDao();
        User user = new User(name, email, password);

        User userExist = userDao.findByEmail(email);

        if (userExist != null) {
            return false;
        }

        String hashPassword = HashPassword.hashPassword(user.getPassword());
        user.setPassword(hashPassword);

        userDao.create(user);

        return true;
    }

}
