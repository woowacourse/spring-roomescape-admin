package roomescape.service;

import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import roomescape.domain.ReservationTime;
import roomescape.domain.dto.ReservationRequestDto;
import roomescape.domain.dto.ReservationTimeRequestDto;
import roomescape.repositiory.GeneralRepository;
import roomescape.repositiory.ReservationRepository;
import roomescape.repositiory.ReservationTimeRepository;

@JdbcTest
@Import({ReservationRepository.class, ReservationTimeRepository.class})
class ReservationServiceTest {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @DisplayName("예약한다")
    @Test
    void addReservation() {
        // given
        ReservationService reservationService = new ReservationService(
                reservationRepository,
                reservationTimeRepository);

        // when
        Long id = reservationService.addReservation(
                new ReservationRequestDto("예약자", LocalDate.now(), LocalTime.now()));

        // then
        Assertions.assertThat(id).isNotNull();
    }

    @DisplayName("예약을 취소한다")
    @Test
    void delete() {
        // given
        ReservationService reservationService = new ReservationService(
                reservationRepository,
                reservationTimeRepository);
        Long id = reservationService.addReservation(
                new ReservationRequestDto("예약자", LocalDate.now(), LocalTime.now()));

        // when
        reservationService.delete(id);

        // then
        Assertions.assertThat(reservationService.readAll()).isEmpty();
    }

    @DisplayName("예약 목록을 불러온다")
    @Test
    void readAll() {
        // given
        ReservationService reservationService = new ReservationService(
                reservationRepository,
                reservationTimeRepository);
        Long id = reservationService.addReservation(
                new ReservationRequestDto("예약자", LocalDate.now(), LocalTime.now()));

        // when
        int firstReadSize = reservationService.readAll().size();
        reservationService.delete(id);
        int secondReadSize = reservationService.readAll().size();

        // then
        Assertions.assertThat(firstReadSize).isEqualTo(1);
        Assertions.assertThat(secondReadSize).isEqualTo(0);
    }
}