package user;

import com.github.javafaker.Faker;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {

        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test");
        jdbi.installPlugin(new SqlObjectPlugin());


        try (Handle handle = jdbi.open()) {
            UserDao dao = handle.attach(UserDao.class);
            var faker = new FakeUserGenerator(new Locale("hu"));
            dao.createTable();

            for(int i = 0; i < 100; i++){
                User newUser = faker.generateFakeUser();
                dao.insert(newUser);
            }

            dao.findById(55).ifPresent(System.out::println);

            dao.findByUserName("ika.fekete").ifPresent(System.out::println);

            Optional<User> optionalUser = dao.findById(55);
            User user;
            user = optionalUser.get();
            dao.delete(user);

            dao.list().forEach(System.out::println);
        }
    }

}
