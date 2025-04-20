package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.Reservation;
import roomescape.test.fixture.ReservationFixture;
import roomescape.test.utility.ReservationTestUtility;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationRepository repository;

    @BeforeEach
    void beforeEach() {
        String sql = "DELETE FROM reservation";
        jdbcTemplate.update(sql);
    }

    @DisplayName("예약을 추가할 수 있다.")
    @Test
    void canAdd() {
        Reservation reservation = ReservationFixture.create("reservation1");
        long newId = repository.add(reservation);

        List<Reservation> reservations = repository.findAll();
        assertAll(
                () -> assertThat(reservations).hasSize(1),
                () -> ReservationTestUtility.checkReservationId(reservations.getFirst().getId(), newId),
                () -> ReservationTestUtility.checkReservationFieldWithoutId(reservations.getFirst(), reservation)
        );
    }

    @DisplayName("예약을 삭제할 수 있다.")
    @Test
    void canDelete() {
        long newId = repository.add(ReservationFixture.create("reservation1"));
        repository.deleteById(newId);

        List<Reservation> reservations = repository.findAll();
        assertThat(reservations).hasSize(0);
    }

    @DisplayName("모든 예약을 조회할 수 있다.")
    @Test
    void canFindAll() {
        repository.add(ReservationFixture.create("reservation1"));
        repository.add(ReservationFixture.create("reservation2"));

        List<Reservation> reservations = repository.findAll();
        assertThat(reservations).hasSize(2);
    }

    @DisplayName("Id에 해당하는 예약을 조회할 수 있다.")
    @Test
    void canFindById() {
        Reservation reservation = ReservationFixture.create("reservation1");
        long newId = repository.add(reservation);

        Optional<Reservation> savedReservation = repository.findById(newId);
        assertAll(
                () -> assertThat(savedReservation).isPresent(),
                () -> ReservationTestUtility.checkReservationId(savedReservation.get().getId(), newId),
                () -> ReservationTestUtility.checkReservationFieldWithoutId(savedReservation.get(), reservation)
        );
    }
}