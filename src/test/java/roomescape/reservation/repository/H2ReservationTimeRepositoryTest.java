package roomescape.reservation.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import roomescape.reservation.model.ReservationTime;
import roomescape.reservation.repository.h2.H2ReservationTimeRepository;

@JdbcTest(properties = "application-test.properties")
@Import(H2ReservationTimeRepository.class)
class H2ReservationTimeRepositoryTest {

    @Autowired
    H2ReservationTimeRepository h2ReservationTimeRepository;

    ReservationTime reservationTimeWithoutId = ReservationTime.createWithoutId(LocalTime.of(12, 0));

    @DisplayName("Time을 추가한다.")
    @Test
    void insertTime() {
        // when
        ReservationTime reservationTime = h2ReservationTimeRepository.insertTime(reservationTimeWithoutId);

        // then
        assertThat(reservationTime.getStartAt()).isEqualTo(LocalTime.of(12, 0));
    }

    @DisplayName("id에 해당하는 Time을 삭제한다.")
    @Test
    void deleteById() {
        // given
        ReservationTime reservationTime = h2ReservationTimeRepository.insertTime(reservationTimeWithoutId);

        // when
        boolean isDeleted = h2ReservationTimeRepository.deleteTimeById(reservationTime.getId());

        // then
        assertThat(isDeleted).isTrue();
    }

    @DisplayName("전체 Time을 읽어온다.")
    @Test
    void findAll() {
        // given
        h2ReservationTimeRepository.insertTime(reservationTimeWithoutId);

        // when
        List<ReservationTime> reservationTimes = h2ReservationTimeRepository.findAll();

        // then
        assertThat(reservationTimes).hasSize(1);
        assertThat(reservationTimes.getFirst().getStartAt()).isEqualTo(LocalTime.of(12, 0));
    }
}