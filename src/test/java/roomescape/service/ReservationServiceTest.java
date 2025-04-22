package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Name;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

class ReservationServiceTest {

    ReservationService reservationService;
    ReservationRepository reservationRepository;
    ReservationTimeRepository reservationTimeRepository;

    @BeforeEach
    void setUp() {
        reservationRepository = new FakeReservationRepository();
        reservationTimeRepository = new FakeReservationTimeRepository();
        reservationService = new ReservationService(reservationRepository, reservationTimeRepository);
    }

    @DisplayName("저장된 예약을 모두 찾는다.")
    @Test
    void findAll() {
        // given
        ReservationTime reservationTime = new ReservationTime(LocalTime.of(10, 0));
        reservationTimeRepository.save(reservationTime);
        Reservation reservation = new Reservation(Name.from("훌라"), LocalDate.of(2024, 4, 21), reservationTime);
        reservationRepository.save(reservation);

        // when
        List<ReservationResponse> responses = reservationService.findAll();

        //then
        assertAll(() -> {
            assertThat(responses).hasSize(1);
            assertThat(responses.getFirst().name()).isEqualTo("훌라");
        });
    }

    @DisplayName("id를 통해 예약을 찾는다.")
    @Test
    void findById() {
        // given
        ReservationTime reservationTime = new ReservationTime(LocalTime.of(10, 0));
        reservationTimeRepository.save(reservationTime);
        Reservation reservation = new Reservation(Name.from("훌라"), LocalDate.of(2024, 4, 21), reservationTime);
        reservationRepository.save(reservation);

        // when
        ReservationResponse response = reservationService.findById(1);

        //then
        assertAll(() -> {
            assertThat(response.id()).isEqualTo(1);
            assertThat(response.name()).isEqualTo("훌라");
            assertThat(response.date()).isEqualTo(LocalDate.of(2024,4,21));
            assertThat(response.time().startAt()).isEqualTo(LocalTime.of(10,0));
        });
    }

    @DisplayName("예약을 저장한다.")
    @Test
    void save() {
        // given
        ReservationTime reservationTime = new ReservationTime(LocalTime.of(10, 0));
        reservationTimeRepository.save(reservationTime);
        ReservationCreateRequest request = new ReservationCreateRequest("훌라", LocalDate.of(2024,4,21), 1);

        // when
        ReservationResponse saved = reservationService.save(request);

        //then
        reservationRepository.findById(1);
        assertAll(() -> {
            assertThat(saved.id()).isEqualTo(1);
            assertThat(saved.name()).isEqualTo("훌라");
            assertThat(saved.date()).isEqualTo(LocalDate.of(2024,4,21));
            assertThat(saved.time().startAt()).isEqualTo(LocalTime.of(10,0));
        });
    }

    @DisplayName("id를 통해 예약을 삭제한다.")
    @Test
    void deleteById() {
        // given
        ReservationTime reservationTime = new ReservationTime(LocalTime.of(10, 0));
        reservationTimeRepository.save(reservationTime);
        ReservationCreateRequest request = new ReservationCreateRequest("훌라", LocalDate.of(2024,4,21), 1);
        reservationService.save(request);

        // when
        reservationService.deleteById(1);

        //then
        assertThat(reservationRepository.findAll()).isEmpty();
    }
}
