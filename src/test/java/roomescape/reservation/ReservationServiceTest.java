package roomescape.reservation;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.repository.FakeReservationDao;
import roomescape.reservation.repository.ReservationRepository;

class ReservationServiceTest {

    ReservationRepository reservationRepository;
    ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservationRepository = new FakeReservationDao();
        reservationService = new ReservationService(reservationRepository);
    }

    @DisplayName("예약 생성에 성공한다.")
    @Test
    void create() {
        //given
        String name = "choco";
        LocalDate date = LocalDate.of(2024, 4, 18);
        LocalTime time = LocalTime.of(19, 55, 18);
        ReservationRequest reservationRequest = new ReservationRequest(name, date, time);

        //when
        long id = reservationService.create(reservationRequest);

        //then
        assertThat(id).isEqualTo(1);
    }

    @DisplayName("예약 조회에 성공한다.")
    @Test
    void find() {
        //given
        long id = 1;
        String name = "choco";
        LocalDate date = LocalDate.of(2024, 4, 18);
        LocalTime time = LocalTime.of(19, 55, 18);
        reservationRepository.save(new Reservation(id, name, date, time));

        //when
        List<ReservationResponse> reservations = reservationService.findAllReservations();

        //then
        assertThat(reservations).hasSize(1);
        assertThat(reservations.get(0).name()).isEqualTo(name);
        assertThat(reservations.get(0).date()).isEqualTo(date);
        assertThat(reservations.get(0).time()).isEqualTo(time);
    }

    @DisplayName("예약 삭제에 성공한다.")
    @Test
    void delete() {
        //given
        long id = 1;
        String name = "choco";
        LocalDate date = LocalDate.of(2024, 4, 18);
        LocalTime time = LocalTime.of(19, 55, 18);
        reservationRepository.save(new Reservation(id, name, date, time));

        //when
        boolean deleted = reservationService.delete(id);

        //then
        assertThat(deleted).isTrue();
        assertThat(reservationRepository.findAll()).hasSize(0);
    }
}
