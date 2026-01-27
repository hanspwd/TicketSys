package service.users;

import dao.UserDao;
import model.User;
import model.enums.Role;

import java.sql.SQLException;
import java.util.List;

public class UserManagementService {
    
    private final UserDao userDao;
    
    public UserManagementService() {
        this.userDao = new UserDao();
    }
    
    public List<User> getAllUsers() throws SQLException {
        return userDao.readAll();
    }
    
    public User searchUserById(int id) throws SQLException {
        return userDao.readById(id);
    }
    
    public User searchUserByEmail(String email) throws SQLException {
        return userDao.findByEmail(email);
    }
    
    public User searchUserByName(String name) throws SQLException {
        return userDao.findByName(name);
    }
    
    public List<User> searchUsersByNamePattern(String name) throws SQLException {
        String query = "SELECT * FROM person WHERE name LIKE ? ORDER BY name";
        return userDao.findByNamePattern(query, "%" + name + "%");
    }
    
    public void updateUser(User user) throws SQLException {
        userDao.update(user);
    }
    
    public void deleteUser(int id) throws SQLException {
        userDao.delete(id);
    }
    
    public void updateUserRole(int userId, Role newRole) throws SQLException {
        userDao.updateRole(userId, newRole.getRoleId());
    }
    
    public User searchUser(String searchTerm, SearchType searchType) throws SQLException {
        switch (searchType) {
            case ID:
                try {
                    return searchUserById(Integer.parseInt(searchTerm));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid ID format");
                }
            case EMAIL:
                return searchUserByEmail(searchTerm);
            case NAME:
                return searchUserByName(searchTerm);
            default:
                throw new IllegalArgumentException("Invalid search type");
        }
    }
    
    public SearchResult searchUsersByName(String name) throws SQLException {
        List<User> users = searchUsersByNamePattern(name);
        
        if (users.isEmpty()) {
            return SearchResult.noResults();
        } else if (users.size() == 1) {
            return SearchResult.singleResult(users.get(0));
        } else {
            return SearchResult.multipleResults(users);
        }
    }
    
    public static class SearchResult {
        private final boolean hasResults;
        private final boolean isSingle;
        private final User singleUser;
        private final List<User> multipleUsers;
        
        private SearchResult(boolean hasResults, boolean isSingle, User singleUser, List<User> multipleUsers) {
            this.hasResults = hasResults;
            this.isSingle = isSingle;
            this.singleUser = singleUser;
            this.multipleUsers = multipleUsers;
        }
        
        public static SearchResult noResults() {
            return new SearchResult(false, false, null, null);
        }
        
        public static SearchResult singleResult(User user) {
            return new SearchResult(true, true, user, null);
        }
        
        public static SearchResult multipleResults(List<User> users) {
            return new SearchResult(true, false, null, users);
        }
        
        public boolean hasResults() {
            return hasResults;
        }
        
        public boolean isSingle() {
            return isSingle;
        }
        
        public User getSingleUser() {
            return singleUser;
        }
        
        public List<User> getMultipleUsers() {
            return multipleUsers;
        }
    }
    
    public enum SearchType {
        ID, EMAIL, NAME
    }
}