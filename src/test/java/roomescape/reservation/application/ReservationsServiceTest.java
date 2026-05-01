package roomescape.reservation.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import roomescape.reservation.application.dto.ReservationRequest;
import roomescape.reservation.repository.ReservationEntity;
import roomescape.reservation.repository.ReservationsRepository;
import roomescape.reservation.repository.dto.Reservation;
import roomescape.time.application.dto.TimeInfo;
import roomescape.time.repository.TimeEntity;
import roomescape.time.repository.TimesRepository;

@ExtendWith(MockitoExtension.class)
class ReservationsServiceTest {

    @Mock
    ReservationsRepository reservationsRepository;

    @Mock
    TimesRepository timesRepository;

    @InjectMocks
    ReservationsService reservationsService;

    @DisplayName("모든 예약들을 조회한다.")
    @Test
    void getReservations() {
        // given
        given(reservationsRepository.findAllReservationsWithTime())
                .willReturn(List.of(
                        new Reservation(
                                1L,
                                "브라운",
                                LocalDate.of(2026, 4, 29),
                                new TimeInfo(1L, LocalTime.of(10, 0))
                        ),
                        new Reservation(
                                2L,
                                "포비",
                                LocalDate.of(2026, 4, 30),
                                new TimeInfo(2L, LocalTime.of(11, 0))
                        )
                ));

        // when
        List<Reservation> reservations = reservationsService.getReservations();

        // then
        assertThat(reservations).hasSize(2);
        verify(reservationsRepository).findAllReservationsWithTime();
    }

    @DisplayName("새로운 예약을 등록한다.")
    @Test
    void register() {
        // given
        given(reservationsRepository.saveReservation(any(ReservationEntity.class)))
                .willReturn(
                        new ReservationEntity(
                                1L,
                                "브라운",
                                LocalDate.of(2026, 4, 29),
                                1L
                        )
                );

        given(timesRepository.getTimeEntityById(1L))
                .willReturn(
                        new TimeEntity(
                                1L,
                                LocalTime.of(10, 0)
                        )
                );

        ReservationRequest request = new ReservationRequest(
                "브라운",
                LocalDate.of(2026, 4, 29),
                1L
        );

        // when
        Reservation reservation = reservationsService.register(request);

        // then
        assertThat(reservation.id()).isEqualTo(1L);
        assertThat(reservation.name()).isEqualTo("브라운");
        assertThat(reservation.date()).isEqualTo(LocalDate.of(2026, 4, 29));
        assertThat(reservation.timeInfo().startAt()).isEqualTo(LocalTime.of(10, 0));

        verify(reservationsRepository).saveReservation(any(ReservationEntity.class));
        verify(timesRepository).getTimeEntityById(1L);
    }
}
