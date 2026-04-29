package roomescape;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class ReservationDaoTest {

    private ReservationDao dao;

    @BeforeEach
    void setup() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:reservation_dao_test;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation");

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("schema.sql"));
        populator.execute(dataSource);

        this.dao = new ReservationDao(jdbcTemplate);
    }

    @Test
    void 예약_추가_테스트() {
        // given
        Reservation reservation = new Reservation(null, "브라운", "2023-08-05", "15:40");

        // when
        Long id = dao.insert(reservation);

        // then
        List<Reservation> reservations = dao.findAll();
        Reservation savedReservation = dao.findBy(id);
        assertAll(
                () -> assertThat(id).isNotNull(),
                () -> assertThat(reservations).hasSize(1),
                () -> assertThat(savedReservation.getName()).isEqualTo(reservation.getName()),
                () -> assertThat(savedReservation.getDate()).isEqualTo(reservation.getDate()),
                () -> assertThat(savedReservation.getTime()).isEqualTo(reservation.getTime()));
    }

    @Test
    void 예약_삭제_테스트() {
        // given
        Reservation reservation1 = new Reservation(null, "브라운", "2023-08-05", "15:40");
        Reservation reservation2 = new Reservation(null, "구구", "2023-08-06", "12:00");
        Long id1 = dao.insert(reservation1);
        Long id2 = dao.insert(reservation2);

        // when
        int deletedCount = dao.delete(id1);

        // then
        List<Reservation> reservations = dao.findAll();
        assertAll(
                () -> assertThat(deletedCount).isEqualTo(1),
                () -> assertThat(reservations).hasSize(1),
                () -> assertThatThrownBy(() -> dao.findBy(id1))
                        .isInstanceOf(EmptyResultDataAccessException.class));
    }
}
