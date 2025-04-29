package roomescape.user.reservation.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import roomescape.fake.FakeReservationTimeRepository;

class ReservationTimeServiceTest {

    private final FakeReservationTimeRepository reservationTimeRepository = new FakeReservationTimeRepository();

    @BeforeEach
    void setUp() {
        reservationTimeRepository.clear();
    }

    @AfterEach
    void tearDown() {
        reservationTimeRepository.clear();
    }

    @Nested
    class ValidCases {

        @Test
        @DisplayName("예약 시간을 저장할 수 있다.")
        void saveReservationTime() {
            // given
            var reservationTimeService = new ReservationTimeService(reservationTimeRepository);

            // when
            var savedId = reservationTimeService.saveReservationTime(new ReservationTime(null, LocalTime.of(10, 0)));
            var savedTime = reservationTimeRepository.findById(savedId).get();

            // then
            assertThat(savedTime.getStartAt()).isEqualTo(LocalTime.of(10, 0));
        }

        @Test
        @DisplayName("예약 시간 단건을 조회할 수 있다.")
        void findReservationTime() {
            // given
            var reservationTimeService = new ReservationTimeService(reservationTimeRepository);

            var savedId = reservationTimeRepository.save(new ReservationTime(null, LocalTime.of(11, 0)));

            // when
            var reservationTime = reservationTimeService.findReservationTime(savedId);

            // then
            assertThat(reservationTime.getId()).isEqualTo(savedId);
        }

        @Test
        @DisplayName("예약 시간 목록을 조회할 수 있다.")
        void findReservationTimes() {
            // given
            var reservationTimeService = new ReservationTimeService(reservationTimeRepository);

            reservationTimeRepository.save(new ReservationTime(null, LocalTime.of(12, 0)));

            // when
            var reservationTimes = reservationTimeService.findAllReservationTimes();

            // then
            assertThat(reservationTimes).hasSize(1);
        }

        @Test
        @DisplayName("예약 시간을 삭제할 수 있다.")
        void deleteReservationTime() {
            // given
            var reservationTimeService = new ReservationTimeService(reservationTimeRepository);

            var savedId = reservationTimeRepository.save(new ReservationTime(null, LocalTime.of(13, 0)));

            // when
            reservationTimeService.deleteReservationTime(savedId);

            // then
            assertThat(reservationTimeRepository.findById(savedId)).isEmpty();
        }
    }

    @Nested
    class InvalidCases {

        @Test
        @DisplayName("존재하지 않는 예약 시간을 조회하면 예외를 던진다.")
        void findReservationTime() {
            // given
            var reservationTimeService = new ReservationTimeService(reservationTimeRepository);

            // when & then
            assertThatThrownBy(() -> reservationTimeService.findReservationTime(999L))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("Reservation time not found");
        }
    }
}
