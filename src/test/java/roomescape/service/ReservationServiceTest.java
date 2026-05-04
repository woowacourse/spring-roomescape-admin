package roomescape.service;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import roomescape.controller.dto.ReservationRequestDto;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.fake.InMemoryReservationRepository;
import roomescape.service.fake.InMemoryReservationTimeRepository;

class ReservationServiceTest {
    private final ReservationRepository reservationRepository = new InMemoryReservationRepository();
    private final ReservationTimeRepository reservationTimeRepository = new InMemoryReservationTimeRepository();
    private final ReservationService reservationService = new ReservationService(reservationRepository,
            reservationTimeRepository);

    @Nested
    @DisplayName("save(): ")
    class Save {
        @Test
        @DisplayName("예약시간이 존재한다면, 예약에 성공한다.")
        void save() {
            ReservationTime saved = reservationTimeRepository.save(new ReservationTime(null, LocalTime.of(10, 0)));
            ReservationRequestDto reservationRequestDto = new ReservationRequestDto("티온", LocalDate.of(2026, 5, 3),
                    saved.getId());

            assertThatNoException().isThrownBy(() -> reservationService.save(reservationRequestDto));

        }

        @Test
        @DisplayName("예약시간이 존재하지 않는다면, 예외를 반환한다")
        void throwIllegalArgumentException_when_notExistsReservationTime() {
            ReservationRequestDto reservationRequestDto = new ReservationRequestDto("티온", LocalDate.of(2026, 5, 3), 1L);
            reservationTimeRepository.delete(reservationRequestDto.timeId());
            assertThatThrownBy(() -> reservationService.save(reservationRequestDto))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
