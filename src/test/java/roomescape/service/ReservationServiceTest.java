package roomescape.service;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.dto.reservation.ReservationCreateRequest;
import roomescape.dto.reservation.ReservationResponse;
import roomescape.dto.reservationtime.ReservationTimeCreateRequest;
import roomescape.dto.reservationtime.ReservationTimeResponse;
import roomescape.repository.ReservationFakeDao;
import roomescape.repository.ReservationTimeFakeDao;
import roomescape.repository.reservationtime.ReservationTimeRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("예약 서비스")
class ReservationServiceTest {

    private ReservationService reservationService;
    private ReservationTimeService reservationTimeService;
    private LocalTime startAt;
    private LocalDate date;

    @BeforeEach
    void setUp() {
        ReservationTimeRepository reservationTimeRepository = new ReservationTimeFakeDao();
        this.reservationTimeService = new ReservationTimeService(reservationTimeRepository);
        this.reservationService = new ReservationService(new ReservationFakeDao(), reservationTimeRepository);
        this.startAt = LocalTime.of(10, 10);
        this.date = LocalDate.of(2024, 11, 16);
    }

    @DisplayName("예약 서비스는 예약들을 조회한다.")
    @Test
    void readReservations() {
        // given
        createInitReservation();

        // when
        List<ReservationResponse> reservations = reservationService.readReservations();

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
        ReservationResponse reservation = reservationService.readReservation(id);

        // then
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(reservation.date()).isEqualTo(date);
        softAssertions.assertThat(reservation.name()).isEqualTo("클로버");
        softAssertions.assertAll();
    }

    @DisplayName("예약 서비스는 예약을 생성한다.")
    @Test
    void createReservation() {
        // given
        ReservationTimeResponse reservationTime = reservationTimeService
                .createTime(new ReservationTimeCreateRequest(startAt));
        ReservationCreateRequest request = new ReservationCreateRequest("클로버",
                date, reservationTime.id());

        // when
        ReservationResponse reservation = reservationService.createReservation(request);

        // then
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(reservation.date()).isEqualTo(date);
        softAssertions.assertThat(reservation.name()).isEqualTo("클로버");
        softAssertions.assertThat(reservation.time().getStartAt()).isEqualTo(reservationTime.startAt());
        softAssertions.assertAll();
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
        ReservationTimeResponse reservationTime = reservationTimeService
                .createTime(new ReservationTimeCreateRequest(startAt));
        ReservationCreateRequest request = new ReservationCreateRequest("클로버",
                date, reservationTime.id());
        reservationService.createReservation(request);
    }
}
