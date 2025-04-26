package roomescape.service;

import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import roomescape.domain.ReservationTime;
import roomescape.domain.dto.ReservationRequestDto;
import roomescape.repositiory.ReservationRepository;
import roomescape.repositiory.ReservationTimeRepository;

@JdbcTest
@Import({ReservationRepository.class, ReservationTimeRepository.class})
class ReservationServiceTest {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    private Long timeId;

    @BeforeEach
    void setUp() {
        timeId = reservationTimeRepository.add(new ReservationTime(LocalTime.now()));
    }


    @DisplayName("예약한다")
    @Test
    void addReservation() {
        // given
        ReservationService reservationService = new ReservationService(
                reservationRepository,
                reservationTimeRepository);
        // when
        Long id = reservationService.addReservation(
                new ReservationRequestDto("예약자", LocalDate.now(), timeId));

        // then
        Assertions.assertThat(id).isNotNull();
    }

    @DisplayName("예약을 취소한다")
    @Test
    void deleteReservation() {
        // given
        ReservationService reservationService = new ReservationService(
                reservationRepository,
                reservationTimeRepository);

        Long id = reservationService.addReservation(
                new ReservationRequestDto("예약자", LocalDate.now(), timeId));

        // when
        reservationService.deleteReservation(id);

        // then
        Assertions.assertThat(reservationService.readReservationAll()).isEmpty();
    }

    @DisplayName("예약 목록을 불러온다")
    @Test
    void readReservationAll() {
        // given
        ReservationService reservationService = new ReservationService(
                reservationRepository,
                reservationTimeRepository);

        Long id = reservationService.addReservation(
                new ReservationRequestDto("예약자", LocalDate.now(), timeId));

        // when
        int firstReadSize = reservationService.readReservationAll().size();
        reservationService.deleteReservation(id);
        int secondReadSize = reservationService.readReservationAll().size();

        // then
        Assertions.assertThat(firstReadSize).isEqualTo(1);
        Assertions.assertThat(secondReadSize).isEqualTo(0);
    }
}