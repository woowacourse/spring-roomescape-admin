package roomescape.reservation.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.CreateReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.repository.ReservationRepository;

class ReservationServiceTest {
    ReservationService reservationService;

    @BeforeEach
    void setup() {
        ReservationRepository reservationRepository = new ReservationRepository();
        reservationRepository.save(new Reservation(reservationRepository.generateId(), "한다", LocalDate.of(2023, 8, 5),
                LocalTime.of(15, 40)));
        reservationRepository.save(new Reservation(reservationRepository.generateId(), "판다", LocalDate.of(2023, 10, 5),
                LocalTime.of(15, 40)));

        this.reservationService = new ReservationService(reservationRepository);
    }

    @Test
    @DisplayName("전체 예약 정보를 가져온다.")
    void findAll() {
        //given & when
        List<ReservationResponse> reservationsResponse = reservationService.findAll();

        //then
        assertThat(reservationsResponse.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("예약을 추가한다.")
    void create() {
        //given & when
        reservationService.create(new CreateReservationRequest("브라운", LocalDate.of(2023, 1, 1), LocalTime.of(10, 0)));

        //then
        assertThat(reservationService.findAll().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void delete() {
        //given
        Long id = 1L;

        //when
        reservationService.delete(id);

        //then
        assertThat(reservationService.findAll().size()).isEqualTo(1);
    }
}