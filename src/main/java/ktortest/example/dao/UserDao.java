package ktortest.example.dao;

import ktortest.dao.AbstractDao;
import ktortest.example.entity.User;

import java.util.List;

public class UserDao extends AbstractDao<User> {

    public void saveUser(User user) {
        withTransaction(session -> {
            session.save(user);
        });
    }

    public List<User> getUsers() {
        return asCriteria((criteriaBuilder, userCriteriaQuery, userRoot) -> null);
    }

    public List<User> getUsersNamed(String name) {
        return asCriteria((criteriaBuilder, userCriteriaQuery, userRoot) ->
                userCriteriaQuery.where(criteriaBuilder.equal(userRoot.get("name"), name)));
    }

}
