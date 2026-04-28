package roomescape.domain.reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import roomescape.domain.reservation.dto.CreateReservationRequest;
import roomescape.domain.reservation.dto.CreateReservationResponse;
import roomescape.domain.reservation.dto.ReservationResponse;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservationService = new ReservationService(reservationRepository);
    }

    @Test
    void 예약을_생성하면_응답_dto로_반환한다() {
        // given
        CreateReservationRequest request = new CreateReservationRequest(
            null,
            "보예",
            LocalDate.of(2023, 8, 5),
            LocalTime.of(15, 40)
        );
        Reservation savedReservation = Reservation.createWithId(
            1L,
            Reservation.createWithoutId(
                "보예",
                LocalDate.of(2023, 8, 5),
                LocalTime.of(15, 40)
            )
        );

        // when
        when(reservationRepository.save(any(Reservation.class))).thenReturn(savedReservation);
        CreateReservationResponse response = reservationService.createReservation(request);

        // then
        assertAll(
            () -> assertThat(response.id()).isEqualTo(1L),
            () -> assertThat(response.name()).isEqualTo("보예"),
            () -> assertThat(response.date()).isEqualTo(LocalDate.of(2023, 8, 5)),
            () -> assertThat(response.time()).isEqualTo(LocalTime.of(15, 40))
        );
    }

    @Test
    void 예약_목록을_조회하면_응답_dto_리스트로_반환한다() {
        // given
        Reservation firstReservation = Reservation.createWithId(
            1L,
            Reservation.createWithoutId(
                "보예",
                LocalDate.of(2023, 8, 5),
                LocalTime.of(15, 40)
            )
        );
        Reservation secondReservation = Reservation.createWithId(
            2L,
            Reservation.createWithoutId("수민",
                LocalDate.of(2023, 8, 6),
                LocalTime.of(16, 0)
            )
        );

        // when
        when(reservationRepository.findAll()).thenReturn(List.of(firstReservation, secondReservation));
        List<ReservationResponse> responses = reservationService.getAllReservations();

        // then
        assertAll(
            () -> assertThat(responses).hasSize(2),
            () -> assertThat(responses).extracting(ReservationResponse::id).containsExactly(1L, 2L),
            () -> assertThat(responses).extracting(ReservationResponse::name).containsExactly("보예", "수민")
        );
    }

    @Test
    void 존재하는_예약을_삭제하면_삭제_상태로_변경된다() {
        // given
        Long id = 1L;
        Reservation reservation = Reservation.createWithId(
            id,
            Reservation.createWithoutId(
                "보예",
                LocalDate.of(2023, 8, 5),
                LocalTime.of(15, 40)
            )
        );

        // when
        when(reservationRepository.findReservation(id)).thenReturn(Optional.of(reservation));
        reservationService.deleteReservation(id);

        // then
        assertThat(reservation.isDeleted()).isTrue();
    }

    @Test
    void 존재하지_않는_예약을_삭제할_시_예외가_발생한다() {
        // given
        Long id = 1L;

        // when
        when(reservationRepository.findReservation(id)).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> reservationService.deleteReservation(id))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("존재하지 않는 예약입니다.");
    }
}
