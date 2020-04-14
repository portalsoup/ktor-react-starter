package ktortest;

import ktortest.example.dao.UserDao;
import ktortest.example.entity.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        User user1 = new User("John");
        User user2 = new User("James");

        userDao.saveUser(user1);
        userDao.saveUser(user2);

        List<User> allUsers = userDao.getUsers();
        allUsers.stream().map(User::getName).forEach(System.out::println);

        List<User> allUsersNamedJohn = userDao.getUsersNamed("John");
        allUsersNamedJohn.stream().map(User::getName).forEach(System.out::println);
    }
}
