package roomescape.integration;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import roomescape.helper.DatabaseInitializer;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public abstract class IntegrationTest {
    @LocalServerPort
    int port;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    protected DatabaseInitializer databaseInitializer;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        databaseInitializer.execute();
    }
}
