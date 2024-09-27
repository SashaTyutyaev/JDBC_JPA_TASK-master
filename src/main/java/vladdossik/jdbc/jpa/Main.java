package vladdossik.jdbc.jpa;

import vladdossik.jdbc.jpa.dao.UserDao;
import vladdossik.jdbc.jpa.dao.UserDaoHibernateImpl;
import vladdossik.jdbc.jpa.model.User;
import vladdossik.jdbc.jpa.service.UserService;
import vladdossik.jdbc.jpa.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {

        final UserService userService = new UserServiceImpl();
        userService.dropUsersTable();

        // создание таблицы
        userService.createUsersTable();

        // добавление юзеров в таблицу
        for (int i = 0; i < 4; i++) {
            User user = User.builder()
                    .name("name" + i)
                    .lastName("lastName" + i)
                    .age((byte) 20)
                    .build();
            userService.saveUser(user.getName(), user.getLastName(), user.getAge());
        }

        // вывод юзеров из бд
        for (User user : userService.getAllUsers()) {
            System.out.println(user);
        }

        // очистка таблицы users
        userService.cleanUsersTable();

        // удаление таблицы users
        userService.dropUsersTable();

    }
}
