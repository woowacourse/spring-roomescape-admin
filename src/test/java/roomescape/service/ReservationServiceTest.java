package roomescape.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;
import roomescape.FakeReservationRepositoryImpl;
import roomescape.FakeReservationTimeRepositoryImpl;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.dto.ReservationRegisterDto;
import roomescape.service.dto.ReservationResponseDto;

class ReservationServiceTest {
    private final ReservationTimeRepository reservationTimeRepository = new FakeReservationTimeRepositoryImpl();
    private final ReservationRepository reservationRepository = new FakeReservationRepositoryImpl();

    private final ReservationService reservationService = new ReservationService(
            reservationRepository, reservationTimeRepository
    );

    @Test
    void 예약_저장_시에_저장된_id를_반환한다() {
        // given
        ReservationTime reservationTime = new ReservationTime(LocalTime.of(23, 30));
        Long savedReservationTimeId = reservationTimeRepository.save(reservationTime);

        // when
        Long savedId = reservationService.saveReservation(
                new ReservationRegisterDto(
                        LocalDate.now().plusDays(1),
                        "히로",
                        savedReservationTimeId
                ));

        // then
        assertThat(savedId).isEqualTo(1L);
    }

    @Test
    void 저장되지_않은_ReservationTime을_이용해서_Reservation을_저장하고자_하면_예외가_발생한다() {
        // given
        ReservationRegisterDto reservationRegisterDto = new ReservationRegisterDto(
                LocalDate.now().plusDays(1),
                "히로",
                1L
        );

        // when & then
        assertThatThrownBy(() -> reservationService.saveReservation(reservationRegisterDto))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void id를_통해_Reservation을_조회한다() {
        // given
        LocalTime startAt = LocalTime.of(23, 30);
        ReservationTime reservationTime = new ReservationTime(startAt);
        Long savedReservationTimeId = reservationTimeRepository.save(reservationTime);

        String name = "히로";
        LocalDate reservationDate = LocalDate.now().plusDays(1);
        Long savedReservationId = reservationRepository.save(new Reservation(
                name,
                reservationDate,
                reservationTime
        ));

        // when
        ReservationResponseDto responseDto = reservationService.findReservationById(savedReservationId);

        // then
        assertAll(
                () -> assertThat(responseDto.id()).isEqualTo(savedReservationId),
                () -> assertThat(responseDto.name()).isEqualTo(name),
                () -> assertThat(responseDto.date()).isEqualTo(reservationDate),
                () -> assertThat(responseDto.time().id()).isEqualTo(savedReservationTimeId),
                () -> assertThat(responseDto.time().startAt()).isEqualTo(startAt)
        );
    }

    @Test
    void 존재하지_않는_id를_이용해_Reservation을_조회하려고_하면_예외가_발생한다() {
        assertThatThrownBy(() -> reservationService.findReservationById(1L))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void 존재하는_모든_Reservation을_조회한다() {
        // given
        LocalTime startAt = LocalTime.of(23, 30);
        ReservationTime reservationTime = new ReservationTime(startAt);
        Long savedReservationTimeId = reservationTimeRepository.save(reservationTime);

        Reservation firstReservation = new Reservation("히로", LocalDate.now().plusDays(1), reservationTime);
        Reservation secondReservation = new Reservation("히포", LocalDate.now().plusDays(2), reservationTime);

        Long firstReservationSavedId = reservationRepository.save(firstReservation);
        Long secondReservationSavedId = reservationRepository.save(secondReservation);

        // when
        List<ReservationResponseDto> responseDtos = reservationService.findAllReservations();

        // then
        assertAll(
                () -> assertThat(responseDtos).hasSize(2),

                () -> assertThat(responseDtos.getFirst().id()).isEqualTo(firstReservationSavedId),
                () -> assertThat(responseDtos.getFirst().name()).isEqualTo("히로"),
                () -> assertThat(responseDtos.getFirst().date()).isEqualTo(LocalDate.now().plusDays(1)),
                () -> assertThat(responseDtos.getFirst().time().id()).isEqualTo(savedReservationTimeId),
                () -> assertThat(responseDtos.getFirst().time().startAt()).isEqualTo(startAt),

                () -> assertThat(responseDtos.get(1).id()).isEqualTo(secondReservationSavedId),
                () -> assertThat(responseDtos.get(1).name()).isEqualTo("히포"),
                () -> assertThat(responseDtos.get(1).date()).isEqualTo(LocalDate.now().plusDays(2)),
                () -> assertThat(responseDtos.get(1).time().id()).isEqualTo(savedReservationTimeId),
                () -> assertThat(responseDtos.get(1).time().startAt()).isEqualTo(startAt)
        );
    }

    @Test
    void id로_저장된_객체를_삭제한다() {
        // given
        LocalTime startAt = LocalTime.of(23, 30);
        ReservationTime reservationTime = new ReservationTime(startAt);
        reservationTimeRepository.save(reservationTime);

        Reservation reservation = new Reservation("히로", LocalDate.now().plusDays(1), reservationTime);
        Long savedReservationId = reservationRepository.save(reservation);

        // when
        reservationService.deleteReservationById(savedReservationId);

        // then
        assertThat(reservationRepository.findById(savedReservationId)).isEmpty();
    }

    @Test
    void 존재하지_않는_id의_객체를_삭제하고자_하면_예외가_발생한다() {
        assertThatThrownBy(() -> reservationService.deleteReservationById(1L))
                .isInstanceOf(ResponseStatusException.class);
    }
}
