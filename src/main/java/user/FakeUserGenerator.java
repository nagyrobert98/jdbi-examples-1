package user;

import com.github.javafaker.Faker;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.ZoneId;
import java.util.Locale;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

public class FakeUserGenerator {
    private final Faker faker;

    public FakeUserGenerator(Locale locale){
        this.faker = new Faker(locale);
    }

    public User generateFakeUser(){
        return User.builder().username(faker.name().username())
                .password(DigestUtils.md5Hex((faker.internet().password())))
                .name(faker.name().fullName())
                .email(faker.internet().safeEmailAddress())
                .gender(faker.options().option(User.Gender.FEMALE, User.Gender.MALE))
                .birthDate(faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .enabled(faker.bool().bool())
                .build();
    }
}
