package roomescape.reservation.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.ReservationDate;
import roomescape.reservation.domain.ReservationId;
import roomescape.reservation.domain.ReservationRepository;
import roomescape.reservation.domain.ReserverName;
import roomescape.reservation.ui.dto.CreateReservationWebRequest;
import roomescape.reservation.ui.dto.ReservationResponse;
import roomescape.reservation_time.domain.ReservationTime;
import roomescape.reservation_time.domain.ReservationTimeId;
import roomescape.reservation_time.domain.ReservationTimeRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class ReservationServiceImplTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @Test
    @DisplayName("예약을 생성하고 조회할 수 있다")
    void createAndFindReservation() {
        // given
        final ReservationTime reservationTime = reservationTimeRepository.save(
                ReservationTime.of(
                        ReservationTimeId.unassigned(),
                        LocalTime.of(10, 0)));

        final CreateReservationWebRequest requestDto = new CreateReservationWebRequest(
                "브라운",
                LocalDate.of(2023, 8, 5),
                reservationTime.getId().getValue()
        );

        // when
        final ReservationResponse responseDto = reservationService.create(requestDto);
        final List<ReservationResponse> reservations = reservationService.getAll();

        // then
        assertThat(reservations).hasSize(1);
        assertThat(reservations.getFirst().id()).isEqualTo(responseDto.id());
        assertThat(reservations.getFirst().name()).isEqualTo("브라운");
    }

    @Test
    @DisplayName("예약을 삭제할 수 있다")
    void deleteReservation() {
        // given
        final ReservationTime reservationTime = reservationTimeRepository.save(
                ReservationTime.of(
                        ReservationTimeId.unassigned(),
                        LocalTime.of(10, 0)));

        final Reservation reservation = reservationRepository.save(Reservation.of(
                ReservationId.unassigned(),
                ReserverName.from("브라운"),
                ReservationDate.from(LocalDate.of(2023, 8, 5)),
                reservationTime
        ));

        // when
        reservationService.delete(reservation.getId());

        // then
        assertThat(reservationRepository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 예약을 삭제하려 하면 예외가 발생한다")
    void deleteNonExistentReservation() {
        // given
        final ReservationId id = ReservationId.from(-1L);

        // when
        // then
        assertThatThrownBy(() -> reservationService.delete(id))
                .isInstanceOf(NoSuchElementException.class);
    }
}
