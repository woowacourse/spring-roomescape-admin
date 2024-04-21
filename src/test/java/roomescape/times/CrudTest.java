package roomescape.times;


import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.Times;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) // TODO 테스트에서 너무 많은 Context를 만드는건 아닐까?
public class CrudTest {

    @Autowired
    private TimesRepository timesRepository;

    @LocalServerPort
    int port;

    @BeforeEach
    void beforeEach() {
        RestAssured.port = port;
    }

    @Test
    void 시간을_추가한다() {
        //given
        Times times = new Times(1L, "12:00");

        //when
        timesRepository.add(times);

        //then
        assertThat(timesRepository.findAll()).contains(times);
    }

    @Test
    void 시간을_조회한다() {
        //given
        Times timesA = new Times(1L, "12:00");
        Times timesB = new Times(2L, "13:00");

        //when
        timesRepository.add(timesA);
        timesRepository.add(timesB);

        //then
        assertThat(timesRepository.findAll()).hasSize(2);
    }

    @Test
    void 시간을_삭제한다() {
        //given
        Long id = 1L;
        Times times = new Times(id, "00:00");

        //when
        timesRepository.add(times);
        timesRepository.remove(id);

        //then
        assertThat(timesRepository.findAll()).doesNotContain(times);
    }
}
