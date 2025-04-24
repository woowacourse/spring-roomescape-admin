package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.model.ReservationTime;

@JdbcTest
public class H2ReservationTimeRepositoryTest {

    private H2ReservationTimeRepository h2ReservationTimeRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ReservationTime reservationTime;

    @BeforeEach
    void setUp() {
        h2ReservationTimeRepository = new H2ReservationTimeRepository(jdbcTemplate);

        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation_time");
        jdbcTemplate.execute("CREATE TABLE reservation_time ("
                             + "id      BIGINT       NOT NULL AUTO_INCREMENT, "
                             + "start_at    VARCHAR(255) NOT NULL, "
                             + "PRIMARY KEY (id))");

        reservationTime = new ReservationTime(LocalTime.of(10, 0));
    }

    @DisplayName("예약 시간을 추가할 수 있다.")
    @Test
    void addTest() {
        // given & when
        ReservationTime addedReservationTime = h2ReservationTimeRepository.add(reservationTime);

        // then
        assertAll(
                () -> assertThat(addedReservationTime)
                        .isNotNull(),
                () -> assertThat(addedReservationTime.getId())
                        .isEqualTo(1L),
                () -> assertThat(addedReservationTime.getStartAt())
                        .isEqualTo(reservationTime.getStartAt())
        );
    }

    @DisplayName("id로 예약 시간을 조회할 수 있다.")
    @Test
    void findByIdTest() {
        // given
        h2ReservationTimeRepository.add(reservationTime);

        // when
        ReservationTime foundReservationTime = h2ReservationTimeRepository.findById(1L);

        // then
        assertAll(
                () -> assertThat(foundReservationTime)
                        .isNotNull(),
                () -> assertThat(foundReservationTime.getId()).
                        isEqualTo(1L),
                () -> assertThat(foundReservationTime.getStartAt())
                        .isEqualTo(LocalTime.of(10, 0))
        );
    }

    @DisplayName("예약 시간 목록을 조회할 수 있다.")
    @Test
    void findAllTest() {
        // given
        h2ReservationTimeRepository.add(reservationTime);

        // when
        List<ReservationTime> reservationTimes = h2ReservationTimeRepository.findAll();

        // then
        assertThat(reservationTimes)
                .hasSize(1);
    }

    @DisplayName("id로 예약 시간을 삭제할 수 있다.")
    @Test
    void removeByIdTest() {
        // given
        h2ReservationTimeRepository.add(reservationTime);

        // when & then
        assertAll(
                () -> assertThatCode(() -> h2ReservationTimeRepository.removeById(1L))
                        .doesNotThrowAnyException(),
                () -> assertThat(h2ReservationTimeRepository.findAll())
                        .isEmpty()
        );
    }
}
