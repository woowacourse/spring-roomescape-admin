package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import roomescape.domain.Name;
import roomescape.domain.Reservation;

@JdbcTest
public class ReservationDaoTest {

    ReservationDao reservationDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        reservationDao = new ReservationDao(jdbcTemplate);
    }

    @DisplayName("DB에 예약을 추가한다.")
    @Test
    void save() {
        // given
        Reservation reservation = new Reservation(
                Name.from("훌라"),
                LocalDate.of(2024, 4, 20),
                LocalTime.of(12, 17)
        );

        // when
        reservationDao.save(reservation);

        //then
        assertThat(reservationDao.findAll()).hasSize(1);
    }

    @DisplayName("DB에 저장된 예약을 모두 찾는다.")
    @Test
    void findAll() {
        // given
        Reservation reservation = new Reservation(
                Name.from("훌라"),
                LocalDate.of(2024, 4, 20),
                LocalTime.of(12, 17)
        );
        reservationDao.save(reservation);

        // when
        List<Reservation> result = reservationDao.findAll();

        //then
        assertThat(result).hasSize(1);
    }

    @DisplayName("id를 통해 DB에서 예약을 찾는다.")
    @DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
    @Test
    void findById() {
        // given
        Reservation reservation = new Reservation(
                Name.from("훌라"),
                LocalDate.of(2024, 4, 20),
                LocalTime.of(12, 17)
        );
        reservationDao.save(reservation);

        // when
        Reservation result = reservationDao.findById(1);

        //then
        assertThat(result.getId()).isEqualTo(1);
    }

    @DisplayName("존재하지 않는 id를 찾으면 예외를 던진다.")
    @Test
    void findById_throw_exception_when_id_not_exists() {
        assertThatThrownBy(() -> reservationDao.findById(1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 예약 id 입니다.");
    }

    @DisplayName("id를 통해 DB에서 예약을 삭제한다.")
    @Test
    void deleteById() {
        // given
        Reservation reservation = new Reservation(
                Name.from("훌라"),
                LocalDate.of(2024, 4, 20),
                LocalTime.of(12, 17)
        );
        reservationDao.save(reservation);

        // when
        //then
        assertThatCode(() -> reservationDao.deleteById(1))
                .doesNotThrowAnyException();
    }
}
