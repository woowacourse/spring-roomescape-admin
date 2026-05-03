package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static roomescape.TestFixture.createReservation;
import static roomescape.TestFixture.createTime;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import roomescape.controller.dto.ReservationRequestDto;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

class ReservationServiceTest {

    private final ReservationRepository reservationRepository = mock(ReservationRepository.class);
    private final ReservationTimeRepository reservationTimeRepository = mock(ReservationTimeRepository.class);
    private final ReservationService reservationService;

    public ReservationServiceTest() {
        reservationService = new ReservationService(reservationRepository, reservationTimeRepository);
    }

    @Nested
    @DisplayName("save(): ")
    class Save {
        @Test
        @DisplayName("예약시간이 존재한다면, 예약에 성공한다.")
        void save() {
            ReservationRequestDto reservationRequestDto = new ReservationRequestDto("티온", LocalDate.of(2026, 5, 3), 1L);

            when(reservationTimeRepository.isExists(reservationRequestDto.timeId())).thenReturn(true);
            when(reservationTimeRepository.findById(reservationRequestDto.timeId())).thenReturn(createTime());
            when(reservationRepository.save(any())).thenReturn(createReservation());
            assertThat(reservationService.save(reservationRequestDto))
                    .isEqualTo(createReservation());
        }

        @Test
        @DisplayName("예약시간이 존재하지 않는다면, 예외를 반환한다")
        void throwIllegalArgumentException_when_notExistsReservationTime() {
            ReservationRequestDto reservationRequestDto = new ReservationRequestDto("티온", LocalDate.of(2026, 5, 3), 1L);

            when(reservationTimeRepository.isExists(reservationRequestDto.timeId())).thenReturn(false);
            assertThatThrownBy(() -> reservationService.save(reservationRequestDto))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
