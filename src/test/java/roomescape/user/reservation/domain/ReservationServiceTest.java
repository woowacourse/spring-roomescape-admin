package roomescape.user.reservation.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import roomescape.fake.FakeReservationRepository;
import roomescape.fake.FakeReservationTimeRepository;

class ReservationServiceTest {

    private final FakeReservationRepository reservationRepository = new FakeReservationRepository();
    private final FakeReservationTimeRepository reservationTimeRepository = new FakeReservationTimeRepository();

    @Nested
    class ValidCases {

        @Test
        @DisplayName("예약과 예약 시간을 함께 조회할 수 있다.")
        void findReservationsWithTimes() {
            // given
            var reservationService = new ReservationService(reservationRepository, reservationTimeRepository);

            var savedTimeId = reservationTimeRepository.save(new ReservationTime(null, LocalTime.of(10, 0)));
            reservationRepository.save(new Reservation(null, "브라운", LocalDate.of(2025, 5, 1), savedTimeId));

            // when
            var reservationsWithTimes = reservationService.findReservationsWithTimes();

            // then
            assertSoftly(softly -> {
                softly.assertThat(reservationsWithTimes).hasSize(1);
                softly.assertThat(reservationsWithTimes.entrySet().iterator().next().getKey().getName())
                        .isEqualTo("브라운");
                softly.assertThat(reservationsWithTimes.entrySet().iterator().next().getValue().getStartAt())
                        .isEqualTo(LocalTime.of(10, 0));
            });
        }

        @Test
        @DisplayName("단일 예약과 예약 시간을 함께 조회할 수 있다.")
        void findReservationWithTime() {
            // given
            var reservationService = new ReservationService(reservationRepository, reservationTimeRepository);

            var savedTimeId = reservationTimeRepository.save(new ReservationTime(null, LocalTime.of(14, 0)));
            var savedReservationId = reservationRepository.save(
                    new Reservation(null, "포비", LocalDate.of(2025, 6, 1), savedTimeId));

            // when
            var reservationWithTime = reservationService.findReservationWithTime(savedReservationId);

            // then
            assertSoftly(softly -> {
                softly.assertThat(reservationWithTime.getKey().getName()).isEqualTo("포비");
                softly.assertThat(reservationWithTime.getValue().getStartAt()).isEqualTo(LocalTime.of(14, 0));
            });
        }

        @Test
        @DisplayName("예약을 저장할 수 있다.")
        void saveReservationWithTime() {
            // given
            var reservationService = new ReservationService(reservationRepository, reservationTimeRepository);

            var reservation = new Reservation(null, "브라운", LocalDate.of(2025, 5, 1), 1L);

            // when
            var savedId = reservationService.saveReservationWithTime(reservation);

            // then
            assertThat(reservationRepository.findById(savedId)).isPresent();
        }

        @Test
        @DisplayName("예약을 삭제할 수 있다.")
        void deleteReservation() {
            // given
            var reservationService = new ReservationService(reservationRepository, reservationTimeRepository);

            var savedId = reservationRepository.save(new Reservation(null, "포비", LocalDate.of(2025, 5, 2), 1L));

            // when
            reservationService.deleteReservation(savedId);

            // then
            assertThat(reservationRepository.findById(savedId)).isEmpty();
        }
    }
}
