package vladdossik.jdbc.jpa.service;

import lombok.NoArgsConstructor;
import vladdossik.jdbc.jpa.dao.UserDao;
import vladdossik.jdbc.jpa.dao.UserDaoHibernateImpl;
import vladdossik.jdbc.jpa.model.User;

import java.util.List;

@NoArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao = new UserDaoHibernateImpl();

    public void createUsersTable() {
        userDao.createUsersTable();
    }

    public void dropUsersTable() {
        userDao.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        userDao.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        userDao.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public void cleanUsersTable() {
        userDao.cleanUsersTable();
    }
}
