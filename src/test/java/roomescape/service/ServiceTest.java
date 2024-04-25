package roomescape.service;

import org.springframework.boot.test.context.SpringBootTest;
import roomescape.config.TestConfig;
import roomescape.environment.WebConfig;

@SpringBootTest(
        classes = {WebConfig.class, TestConfig.class},
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = "spring.main.allow-bean-definition-overriding=true"
)
class ServiceTest {
}
