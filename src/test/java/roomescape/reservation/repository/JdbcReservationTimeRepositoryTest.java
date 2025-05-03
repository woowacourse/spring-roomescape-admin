package roomescape.reservation.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import roomescape.reservation.model.ReservationTime;
import roomescape.reservation.repository.jdbc.JdbcReservationTimeRepository;

@JdbcTest(properties = "application-test.properties")
@Import(JdbcReservationTimeRepository.class)
class JdbcReservationTimeRepositoryTest {

    @Autowired
    JdbcReservationTimeRepository jdbcReservationTimeRepository;

    ReservationTime reservationTimeWithoutId = ReservationTime.createWithoutId(LocalTime.of(12, 0));

    @DisplayName("Time을 추가한다.")
    @Test
    void insertTime() {
        // when
        ReservationTime reservationTime = jdbcReservationTimeRepository.insertTime(reservationTimeWithoutId);

        // then
        assertThat(reservationTime.getStartAt()).isEqualTo(LocalTime.of(12, 0));
    }

    @DisplayName("id에 해당하는 Time을 삭제한다.")
    @Test
    void deleteById() {
        // given
        ReservationTime reservationTime = jdbcReservationTimeRepository.insertTime(reservationTimeWithoutId);

        // when
        assertThatCode(() -> jdbcReservationTimeRepository.deleteTimeById(reservationTime.getId()));
    }

    @DisplayName("전체 Time을 읽어온다.")
    @Test
    void findAll() {
        // given
        jdbcReservationTimeRepository.insertTime(reservationTimeWithoutId);

        // when
        List<ReservationTime> reservationTimes = jdbcReservationTimeRepository.findAll();

        // then
        assertThat(reservationTimes).hasSize(1);
        assertThat(reservationTimes.getFirst().getStartAt()).isEqualTo(LocalTime.of(12, 0));
    }

    @DisplayName("id에 해당하는 Time이 존재하면 true를 반환한다.")
    @Test
    void existById() {
        // given
        ReservationTime reservationTime = jdbcReservationTimeRepository.insertTime(reservationTimeWithoutId);

        // when
        boolean isExist = jdbcReservationTimeRepository.existsTimeById(reservationTime.getId());

        // then
        assertThat(isExist).isTrue();
    }

    @DisplayName("id에 해당하는 Time이 존재하지 않으면 false를 반환한다.")
    @Test
    void existByIdFalse() {
        // given
        ReservationTime reservationTime = jdbcReservationTimeRepository.insertTime(reservationTimeWithoutId);
        jdbcReservationTimeRepository.deleteTimeById(reservationTime.getId());

        // when
        boolean isExist = jdbcReservationTimeRepository.existsTimeById(reservationTime.getId());

        // then
        assertThat(isExist).isFalse();
    }
}