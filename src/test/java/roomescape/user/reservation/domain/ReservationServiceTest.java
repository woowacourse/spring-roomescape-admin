package roomescape.user.reservation.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import roomescape.fake.FakeReservationRepository;

class ReservationServiceTest {

    private final FakeReservationRepository reservationRepository = new FakeReservationRepository();

    @BeforeEach
    void setUp() {
        reservationRepository.clear();
    }

    @AfterEach
    void tearDown() {
        reservationRepository.clear();
    }

    @Nested
    class ValidCases {

        @Test
        @DisplayName("예약을 저장할 수 있다.")
        void saveReservation() {
            // given
            var reservationService = new ReservationService(reservationRepository);

            // when
            var savedId = reservationService.saveReservation(new Reservation(
                    null, "브라운", LocalDate.of(2025, 5, 1),
                    new ReservationTime(null, LocalTime.of(12, 0))));
            var savedReservation = reservationRepository.findById(savedId).get();

            // then
            assertSoftly(softly -> {
                softly.assertThat(savedReservation.getName()).isEqualTo("브라운");
                softly.assertThat(savedReservation.getDate()).isEqualTo(LocalDate.of(2025, 5, 1));
                softly.assertThat(savedReservation.extractTime()).isEqualTo(LocalTime.of(12, 0));
            });
        }

        @Test
        @DisplayName("예약 단건을 조회할 수 있다.")
        void findReservation() {
            // given
            var reservationService = new ReservationService(reservationRepository);

            var savedId = reservationRepository.save(
                    new Reservation(null, "포비", LocalDate.of(2025, 6, 1), new ReservationTime(null, LocalTime.of(12, 0))));

            // when
            var reservation = reservationService.findReservation(savedId);

            // then
            assertThat(reservation.getId()).isEqualTo(savedId);
        }

        @Test
        @DisplayName("예약 목록을 조회할 수 있다.")
        void findReservations() {
            // given
            var reservationService = new ReservationService(reservationRepository);

            reservationRepository.save(
                    new Reservation(null, "브라운", LocalDate.of(2025, 5, 1), new ReservationTime(null, LocalTime.of(12, 0))));

            // when
            var reservations = reservationService.findReservations();

            // then
            assertThat(reservations).hasSize(1);
        }

        @Test
        @DisplayName("예약을 삭제할 수 있다.")
        void deleteReservation() {
            // given
            var reservationService = new ReservationService(reservationRepository);

            var savedId = reservationRepository.save(
                    new Reservation(null, "포비", LocalDate.of(2025, 5, 2), new ReservationTime(null, LocalTime.of(12, 0))));

            // when
            reservationService.deleteReservation(savedId);

            // then
            assertThat(reservationRepository.findById(savedId)).isEmpty();
        }
    }

    @Nested
    class InvalidCases {

        @Test
        @DisplayName("존재하지 않는 예약을 조회하면 예외를 던진다.")
        void findReservation() {
            // given
            var reservationService = new ReservationService(reservationRepository);

            var nonExistentId = 999L;

            // when & then
            assertThatThrownBy(() -> reservationService.findReservation(nonExistentId))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("Reservation not found");
        }
    }
}
