package roomescape.core.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import roomescape.core.domain.Reservation;
import roomescape.core.domain.ReservationTime;
import roomescape.web.repository.JdbcReservationRepository;
import roomescape.web.repository.JdbcReservationTimeRepository;

@EnableAutoConfiguration
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class ReservationTimeServiceTest {

    @Autowired
    private ReservationTimeService reservationTimeService;
    @Autowired
    private JdbcReservationRepository jdbcReservationRepository;
    @Autowired
    private JdbcReservationTimeRepository jdbcReservationTimeRepository;

    @AfterEach
    void afterEach() {
        jdbcReservationRepository.deleteAll();
        jdbcReservationTimeRepository.deleteAll();
    }

    @DisplayName("예약 시간의 예약이 존재하면 삭제할 수 없다.")
    @Test
    void deleteExistingReservation() {
        // given
        ReservationTime reservationTime = jdbcReservationTimeRepository.save(new ReservationTime(LocalTime.parse("10:10")));
        Reservation reservation = jdbcReservationRepository.save(
                new Reservation("pobi", LocalDate.parse("2020-10-10"), reservationTime));
        Long timeId = reservationTime.getId();
        Long reservationId = reservation.getId();

        // when & given
        assertThatThrownBy(() -> reservationTimeService.delete(timeId))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("존재하지 않는 ID는 삭제할 수 없다.")
    @Test
    void deleteNonExisting() {
        assertThatThrownBy(() -> reservationTimeService.delete(1L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("예약 시간을 삭제한다.")
    @Test
    void delete() {
        // given
        ReservationTime reservationTime = jdbcReservationTimeRepository.save(new ReservationTime(LocalTime.parse("10:10")));

        // when
        jdbcReservationTimeRepository.delete(reservationTime);

        // then
        Assertions.assertThat(jdbcReservationTimeRepository.findById(reservationTime.getId())).isEmpty();
    }
}
