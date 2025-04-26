package roomescape.user.reservation.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import roomescape.fake.FakeReservationRepository;

class FakeReservationRepositoryTest {

    @Nested
    class ValidCases {

        @Test
        @DisplayName("예약을 저장하면 조회할 수 있다.")
        void saveAndFindById() {
            // given
            var repository = new FakeReservationRepository();
            var reservation = new Reservation(null, "브라운", LocalDate.of(2025, 5, 1), 1L);

            // when
            var savedId = repository.save(reservation);
            var foundReservation = repository.findById(savedId);

            // then
            assertSoftly(softly -> {
                softly.assertThat(foundReservation).isPresent();
                softly.assertThat(foundReservation.get().getName()).isEqualTo("브라운");
                softly.assertThat(foundReservation.get().getDate()).isEqualTo(LocalDate.of(2025, 5, 1));
                softly.assertThat(foundReservation.get().getTimeId()).isEqualTo(1L);
            });
        }

        @Test
        @DisplayName("모든 예약을 조회할 수 있다.")
        void findAll() {
            // given
            var repository = new FakeReservationRepository();
            repository.save(new Reservation(null, "브라운", LocalDate.of(2025, 5, 1), 1L));
            repository.save(new Reservation(null, "포비", LocalDate.of(2025, 5, 2), 2L));

            // when
            var reservations = repository.findAll();

            // then
            assertThat(reservations).hasSize(2);
        }

        @Test
        @DisplayName("예약을 삭제할 수 있다.")
        void deleteById() {
            // given
            var repository = new FakeReservationRepository();
            var savedId = repository.save(new Reservation(null, "브라운", LocalDate.of(2025, 5, 1), 1L));

            // when
            repository.deleteById(savedId);

            // then
            assertThat(repository.findById(savedId)).isEmpty();
        }
    }
}
