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
import roomescape.reservation.model.ReservationTimeDetails;

@JdbcTest(properties = "application-test.properties")
@Import(ReservationTimeRepository.class)
class ReservationReservationTimeRepositoryTest {

    @Autowired
    ReservationTimeRepository reservationTimeRepository;

    ReservationTimeDetails reservationTimeDetails = new ReservationTimeDetails(LocalTime.of(12, 0));

    @DisplayName("Time을 추가한다.")
    @Test
    void insertTime() {
        // when
        ReservationTime reservationTime = reservationTimeRepository.insertTime(reservationTimeDetails);

        // then
        assertThat(reservationTime)
                .hasFieldOrPropertyWithValue("startAt", LocalTime.of(12, 0));
    }

    @DisplayName("id에 해당하는 Time을 삭제한다.")
    @Test
    void deleteById() {
        // given
        ReservationTime reservationTime = reservationTimeRepository.insertTime(reservationTimeDetails);

        // when
        boolean isDeleted = reservationTimeRepository.deleteTimeById(reservationTime.getId());

        // then
        assertThat(isDeleted).isTrue();
    }

    @DisplayName("전체 Time을 읽어온다.")
    @Test
    void findAll() {
        // given
        reservationTimeRepository.insertTime(reservationTimeDetails);

        // when
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();

        // then
        assertThat(reservationTimes).hasSize(1);
        assertThat(reservationTimes.getFirst())
                .hasFieldOrPropertyWithValue("startAt", LocalTime.of(12, 0));
    }
}