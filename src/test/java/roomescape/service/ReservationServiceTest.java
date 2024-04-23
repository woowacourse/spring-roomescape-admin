package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import roomescape.config.TestConfig;
import roomescape.controller.dto.ReservationRequest;
import roomescape.controller.dto.ReservationResponse;
import roomescape.domain.Reservation;
import roomescape.domain.TimeSlot;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeSlotRepository;

@SpringBootTest(
        classes = TestConfig.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = "spring.main.allow-bean-definition-overriding=true"
)
class ReservationServiceTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private ReservationService reservationService;

    @AfterEach
    void tearDown() {
        reservationRepository.deleteAll();
        timeSlotRepository.deleteAll();
    }

    @Test
    @DisplayName("예약을 추가한다.")
    void scheduleReservationTest() {
        // given
        TimeSlot timeSlot = createTimeSlot();
        ReservationRequest request = new ReservationRequest("브라운", "2023-08-05", timeSlot.getId());
        // when
        ReservationResponse response = reservationService.scheduleReservation(request);
        // then
        List<Reservation> reservations = reservationRepository.findAll();
        assertThat(response.id()).isEqualTo(reservations.get(0).getId());
    }

    @Test
    @DisplayName("예약을 추가할 때, 시간이 존재하지 않는다면 예외를 발생한다.")
    void cancelReservationNotFoundTest() {
        // given
        ReservationRequest request = new ReservationRequest("브라운", "2023-08-05", 999L);
        // when, then
        assertThatThrownBy(() -> reservationService.scheduleReservation(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 시간이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("과거의 시간을 예약하는 경우, 예외를 발생한다.")
    void createPastReservationTest() {
        // given
        TimeSlot timeSlot = createTimeSlot();
        ReservationRequest request = new ReservationRequest("아루", "1999-05-04", timeSlot.getId());
        // when, then
        assertThatThrownBy(() -> reservationService.scheduleReservation(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("과거의 시간을 예약할 수 없습니다.");
    }

    @Test
    @DisplayName("모든 예약을 조회한다.")
    void getAllReservationsTest() {
        // given
        TimeSlot timeSlot = createTimeSlot();
        List<Reservation> reservations = List.of(
                new Reservation("브라운", "2023-08-05", timeSlot),
                new Reservation("코니", "2023-08-06", timeSlot),
                new Reservation("제이지", "2023-08-07", timeSlot)
        );
        reservations.forEach(reservationRepository::addReservation);
        // when
        List<ReservationResponse> actual = reservationService.getAllReservations();
        // then
        assertThat(actual).hasSize(3);
    }

    @Test
    @DisplayName("취소할 때, id에 해당하는 예약이 존재한다면 성공적으로 취소한다.")
    void cancelReservationTest() {
        // given
        TimeSlot timeSlot = createTimeSlot();
        Reservation reservation = reservationRepository.addReservation(
                new Reservation("웨지", "2024-04-22", timeSlot)
        );
        // when
        reservationService.cancelReservation(reservation.getId());
        List<Reservation> actual = reservationRepository.findAll();
        // then
        assertThat(actual).isEmpty();
    }

    private TimeSlot createTimeSlot() {
        return timeSlotRepository.create(new TimeSlot("12:00"));
    }
}
