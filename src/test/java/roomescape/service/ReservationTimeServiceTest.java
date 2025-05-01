package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalTime;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.Repository.ReservationTimeRepository;
import roomescape.controller.dto.response.ReservationTimeResponse;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.service.dto.ReservationTimeCreation;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeServiceTest {

    private static final ReservationTime EXAMPLE_TIME = new ReservationTime(1L, LocalTime.of(12, 0));

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @Autowired
    private ReservationTimeService reservationTimeService;

    @BeforeEach
    void setUp() {
        reservationTimeRepository = new ReservationTimeRepository(jdbcTemplate);

        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation");
        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation_time");

        jdbcTemplate.execute("""
                CREATE TABLE reservation_time
                (
                    id       BIGINT       NOT NULL AUTO_INCREMENT,
                    start_at VARCHAR(255) NOT NULL,
                    PRIMARY KEY (id)
                );
                """);

        reservationTimeRepository.insert(EXAMPLE_TIME);
    }

    @DisplayName("시간을 받아 예약 시간을 생성한다")
    @Test
    void createTest() {
        // given
        LocalTime startAt = LocalTime.of(10, 0);
        ReservationTimeCreation creation = new ReservationTimeCreation(startAt);

        // when
        ReservationTimeResponse response = reservationTimeService.create(creation);

        // then
        assertThat(response.startAt()).isEqualTo(startAt);
    }

    @DisplayName("이미 존재하는 시간을 재생성하려는 경우 예외를 던진다")
    @Test
    void createExceptionTest() {
        // given
        LocalTime startAt = LocalTime.of(12, 0);
        ReservationTimeCreation creation = new ReservationTimeCreation(startAt);

        // when // then
        assertThatCode(() -> reservationTimeService.create(creation))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("존재하는 예약 시간을 조회한다")
    @Test
    void readAllTest() {
        // when // then
        assertThat(reservationTimeService.readAll().size()).isEqualTo(1);
    }

    @DisplayName("id 값에 해당하는 예약 시간을 삭제한다")
    @Test
    void deleteByTest() {
        // when // then
        assertThatCode(() -> reservationTimeService.deleteBy(1L))
                .doesNotThrowAnyException();
    }

    @DisplayName("존재하지 않는 예약 시간을 삭제하는 경우 예외를 던진다.")
    @Test
    void deleteExceptionTest() {
        // when // then
        assertThatCode(() -> reservationTimeService.deleteBy(100000L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageStartingWith("[ERROR]");
    }
}
