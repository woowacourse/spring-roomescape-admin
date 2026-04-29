package roomescape.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import roomescape.domain.Reservation;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(JdbcReservationRepository.class)
@JdbcTest
class JdbcReservationRepositoryTest {
    private final ReservationRepository repository;

    @Autowired
    public JdbcReservationRepositoryTest(DataSource dataSource) {
        this.repository = new JdbcReservationRepository(dataSource);
    }

    @Test
    void 데이터_생성_테스트() {
        // given
        Reservation reservation = new Reservation(null, "브라운", "2026-04-28", "20:43");

        // when
        Long id = repository.createReservation(reservation);

        // then
        assertThat(id).isNotNull();

        List<Reservation> all = repository.findAll();
        assertThat(all).hasSize(1);
        assertThat(all.get(0).getName().value()).isEqualTo("브라운");
    }

    @Test
    void 데이터_전체_조회_테스트() {
        // given
        repository.createReservation(new Reservation(null, "브라운", "2026-04-28", "20:43"));
        repository.createReservation(new Reservation(null, "제임스", "2026-04-29", "10:00"));

        // when
        List<Reservation> reservations = repository.findAll();

        // then
        assertThat(reservations).hasSize(2);
        assertThat(reservations)
                .extracting(Reservation::getName)
                .anySatisfy(name -> assertThat(name.value()).isEqualTo("브라운"))
                .anySatisfy(name -> assertThat(name.value()).isEqualTo("제임스"));
    }

    @Test
    void 데이터_삭제_테스트() {
        // given
        Long id = repository.createReservation(new Reservation(null, "브라운", "2026-04-28", "20:43"));
        assertThat(repository.findAll()).hasSize(1);

        // when
        repository.deleteById(id);

        // then
        assertThat(repository.findAll()).isEmpty();
    }
}