package user;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;

@RegisterBeanMapper(User.class)
public interface UserDao {
    @SqlUpdate(
            """
            CREATE TABLE user
            (
                id IDENTITY PRIMARY KEY,
                username VARCHAR NOT NULL,
                password VARCHAR NOT NULL,
                name VARCHAR NOT NULL,
                email VARCHAR NOT NULL,
                gender ENUM('FEMALE','MALE') NOT NULL,
                birthDate DATE NOT NULL,
                enabled BOOLEAN NOT NULL
            )
            """
    )
    void createTable();

    @SqlUpdate("INSERT INTO user (username, password, name, email, gender, birthDate, enabled) VALUES (:username, :password, :name, :email, :gender, :birthDate, :enabled)")
    @GetGeneratedKeys
    Long insert(@BindBean User user);

    @SqlQuery("SELECT * FROM user WHERE id = :id")
    Optional<User> findById(@Bind("id") long id);

    @SqlQuery("SELECT * FROM user WHERE username = :username")
    Optional<User> findByUserName(@Bind("username") String username);

    @SqlUpdate("DELETE FROM user WHERE username = :username")
    void delete(@BindBean User user);

    @SqlQuery("SELECT * FROM user")
    List<User> list();
}