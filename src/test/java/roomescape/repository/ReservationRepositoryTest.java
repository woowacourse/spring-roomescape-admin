package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static roomescape.fixture.ReservationFixture.INITIAL_RESERVATION_SIZE;
import static roomescape.fixture.ReservationFixture.RESERVATION_1_ID;
import static roomescape.fixture.ReservationTimeFixture.NO_RESERVATION_TIME_ID;
import static roomescape.fixture.ReservationTimeFixture.TIME_1_ID;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import roomescape.domain.Reservation;
import roomescape.fixture.ReservationFixture;

@Import(ReservationRepository.class)
@JdbcTest
class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository repository;

    @Test
    @DisplayName("예약을 추가한다.")
    void save() {
        // given
        Reservation reservation = ReservationFixture.newReservationWithoutId();

        // when
        Reservation savedReservation = repository.save(reservation);

        // then
        Reservation found = repository.findById(savedReservation.getId()).get();
        assertThat(savedReservation).isEqualTo(found);
    }

    @Test
    void findById() {
        // when
        Reservation found = repository.findById(RESERVATION_1_ID).get();

        // then
        assertThat(found.getName()).isEqualTo(ReservationFixture.reservation1().getName());
    }

    @Test
    void findAll() {
        // when
        List<Reservation> result = repository.findAll();

        // then
        assertThat(result.size()).isEqualTo(INITIAL_RESERVATION_SIZE);
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void delete() {
        // given
        assertThat(repository.findById(RESERVATION_1_ID)).isNotEmpty();

        // when
        repository.delete(RESERVATION_1_ID);

        // then
        assertThat(repository.findById(RESERVATION_1_ID)).isEmpty();
    }

    @Test
    @DisplayName("특정 시간에 예약이 존재하면 true를 반환한다.")
    void existsByTimeIdTrue() {
        // when
        boolean result = repository.existsByTimeId(TIME_1_ID);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("특정 시간에 예약이 존재하지 않으면 false를 반환한다.")
    void existsByTimeIdFalse() {
        // when
        boolean result = repository.existsByTimeId(NO_RESERVATION_TIME_ID);

        // then
        assertThat(result).isFalse();
    }
}
