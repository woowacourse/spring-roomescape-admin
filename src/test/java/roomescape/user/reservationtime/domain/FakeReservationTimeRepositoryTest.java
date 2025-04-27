package roomescape.user.reservationtime.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import roomescape.fake.FakeReservationTimeRepository;
import roomescape.user.reservation.domain.ReservationTime;

class FakeReservationTimeRepositoryTest {

    @Nested
    class ValidCases {

        @Test
        @DisplayName("예약 시간을 저장하면 조회할 수 있다.")
        void saveAndFindById() {
            // given
            var repository = new FakeReservationTimeRepository();
            var reservationTime = new ReservationTime(null, LocalTime.of(10, 30));

            // when
            var savedId = repository.save(reservationTime);
            var foundReservationTime = repository.findById(savedId);

            // then
            assertSoftly(softly -> {
                softly.assertThat(foundReservationTime).isPresent();
                softly.assertThat(foundReservationTime.get().getStartAt()).isEqualTo(LocalTime.of(10, 30));
            });
        }

        @Test
        @DisplayName("모든 예약 시간을 조회할 수 있다.")
        void findAll() {
            // given
            var repository = new FakeReservationTimeRepository();
            repository.save(new ReservationTime(null, LocalTime.of(9, 0)));
            repository.save(new ReservationTime(null, LocalTime.of(10, 0)));

            // when
            var reservationTimes = repository.findAll();

            // then
            assertThat(reservationTimes).hasSize(2);
        }

        @Test
        @DisplayName("예약 시간을 삭제할 수 있다.")
        void deleteById() {
            // given
            var repository = new FakeReservationTimeRepository();
            var savedId = repository.save(new ReservationTime(null, LocalTime.of(9, 30)));

            // when
            repository.deleteById(savedId);

            // then
            assertThat(repository.findById(savedId)).isEmpty();
        }
    }
}
