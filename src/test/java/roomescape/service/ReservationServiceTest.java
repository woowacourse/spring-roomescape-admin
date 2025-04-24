package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import roomescape.TestDao;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;
import roomescape.repository.repository.ReservationRepository;
import roomescape.repository.repository.ReservationTimeRepository;

class ReservationServiceTest {

    private final ReservationRepository reservationRepository = new ReservationRepository(new TestDao());
    private final ReservationTimeRepository reservationTimeRepository = new ReservationTimeRepository(new TestDao());
    private final ReservationService service = new ReservationService(reservationRepository, reservationTimeRepository);

    @Test
    @DisplayName("모든 데이터를 조회한다")
    void getAll() {
        // given
        ReservationTime time = reservationTimeRepository.save(new ReservationTime(null, LocalTime.now()));
        reservationRepository.save(new Reservation(null, "moko", LocalDate.now(), time));
        reservationRepository.save(new Reservation(null, "moko", LocalDate.now(), time));

        // when
        List<ReservationResponse> all = service.getAll();

        // then
        assertThat(all).size().isEqualTo(2);
    }

    @Test
    @DisplayName("데이터를 저장한다")
    void create() {
        // given
        ReservationTime time = reservationTimeRepository.save(new ReservationTime(null, LocalTime.now()));

        // when
        service.create(new ReservationRequest(null, "moko", LocalDate.now(), time.id()));

        // then
        assertThat(reservationRepository.getAll()).size().isEqualTo(1);
    }

    @Test
    @DisplayName("데이터를 제거한다")
    void remove() {
        // given
        ReservationTime time = reservationTimeRepository.save(new ReservationTime(null, LocalTime.now()));
        Reservation reservation = reservationRepository.save(new Reservation(null, "moko", LocalDate.now(), time));

        // when
        service.remove(reservation.id());

        // then
        assertThat(reservationRepository.getAll()).size().isEqualTo(0);
    }
}
