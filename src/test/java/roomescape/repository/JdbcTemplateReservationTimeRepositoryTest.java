package roomescape.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class JdbcTemplateReservationTimeRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private JdbcTemplateReservationTimeRepository repository;

    @BeforeEach
    void setUp() {
        repository = new JdbcTemplateReservationTimeRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("ReservationTime 데이터를 데이터베이스에 저장한다.")
    public void save() {
        // given
        ReservationTime reservationTime = new ReservationTime(LocalTime.of(10, 30));

        // when
        ReservationTime saved = repository.save(reservationTime);

        // then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getStartAt()).isEqualTo(reservationTime.getStartAt());
    }

}
