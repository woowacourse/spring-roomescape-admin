package roomescape.repository;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import roomescape.domain.time.Time;
import roomescape.domain.time.repository.H2TimeRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class TimeRepositoryTest {

    @LocalServerPort
    private int port;

    @Autowired
    private H2TimeRepository timeRepository;

    private final Map<String, String> params = Map.of(
            "startAt", "17:00"
    );

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("등록된 시간의 id를 통해 단건 조회할 수 있다.")
    void findTimeById() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(201)
                .body("id", is(1))
                .header("Location", "/times/1");

        Time time = timeRepository.findTimeById(1L);
        Assertions.assertThat(time.getId()).isEqualTo(1L);
    }
}
