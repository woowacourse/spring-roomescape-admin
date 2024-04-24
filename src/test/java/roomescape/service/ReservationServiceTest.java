package roomescape.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.dto.ReservationCreateRequest;
import roomescape.dto.ReservationTimeCreateRequest;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationFakeDao;
import roomescape.repository.ReservationTimeFakeDao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("예약 서비스")
class ReservationServiceTest {

    private ReservationService reservationService;
    private ReservationTimeService reservationTimeService;
    private LocalTime startAt;
    private LocalDate date;

    @BeforeEach
    void setUp() {
        this.reservationTimeService = new ReservationTimeService(new ReservationTimeFakeDao());
        this.reservationService = new ReservationService(new ReservationFakeDao(), reservationTimeService);
        this.startAt = LocalTime.of(10, 10);
        this.date = LocalDate.of(2024, 11, 16);
    }

    @DisplayName("예약 서비스는 예약들을 조회한다.")
    @Test
    void readReservations() {
        // given
        createInitReservation();

        // when
        List<Reservation> reservations = reservationService.readReservations();

        // then
        assertThat(reservations.size()).isEqualTo(1);
    }

    @DisplayName("예약 서비스는 id에 맞는 예약을 조회한다.")
    @Test
    void readReservation() {
        // given
        createInitReservation();
        Long id = 1L;

        // when
        Reservation reservation = reservationService.readReservation(id);

        // then
        assertAll(
                () -> assertThat(reservation.getDate()).isEqualTo(date),
                () -> assertThat(reservation.getName()).isEqualTo("클로버")
        );
    }

    @DisplayName("예약 서비스는 예약을 생성한다.")
    @Test
    void createReservation() {
        // given
        ReservationTime reservationTime = reservationTimeService
                .createTime(new ReservationTimeCreateRequest(startAt));
        ReservationCreateRequest request = new ReservationCreateRequest("클로버",
                date, reservationTime.getId());

        // when
        Reservation reservation = reservationService.createReservation(request);

        // then
        assertAll(
                () -> assertThat(reservation.getDate()).isEqualTo(date),
                () -> assertThat(reservation.getName()).isEqualTo("클로버"),
                () -> assertThat(reservation.getTime().getStartAt()).isEqualTo(reservationTime.getStartAt())
        );
    }

    @DisplayName("예약 서비스는 id에 맞는 예약을 삭제한다.")
    @Test
    void deleteReservation() {
        // given
        createInitReservation();
        Long id = 1L;

        // when & then
        assertThatCode(() -> reservationService.deleteReservation(id))
                .doesNotThrowAnyException();
    }

    private void createInitReservation() {
        ReservationTime reservationTime = reservationTimeService
                .createTime(new ReservationTimeCreateRequest(startAt));
        ReservationCreateRequest request = new ReservationCreateRequest("클로버",
                date, reservationTime.getId());
        reservationService.createReservation(request);
    }
}
