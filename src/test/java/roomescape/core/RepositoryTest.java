package roomescape.core;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Transactional
@SpringBootTest(classes = TestConfig.class)
@Retention(RUNTIME)
@Target(ElementType.TYPE)
public @interface RepositoryTest {
}
