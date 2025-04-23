package roomescape.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.dto.reservation.ReservationResponse;
import roomescape.util.H2DataSourceFactory;

class ReservationDaoTest {

    private ReservationDao reservationDao;

    @BeforeEach
    void setup() {
         DataSource dataSource = H2DataSourceFactory.getDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        reservationDao = new ReservationDao(jdbcTemplate);

        H2DataSourceFactory.initializeTable(jdbcTemplate.getDataSource());

        List<Reservation> reservations = List.of(
                new Reservation("루키", LocalDate.of(2025, 4, 20), LocalTime.of(15, 30)),
                new Reservation("슬링키", LocalDate.of(2025, 4, 18), LocalTime.of(21, 22)),
                new Reservation("범블비", LocalDate.of(2025, 4, 21), LocalTime.of(11, 30))
        );

        String sql = "insert into reservation (name, date, time) values (?, ?, ?)";

        jdbcTemplate.batchUpdate(sql,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        Reservation reservation = reservations.get(i);
                        ps.setString(1, reservation.getName());
                        ps.setString(2, reservation.getDate().toString());
                        ps.setString(3, reservation.getTime().toString());
                    }

                    @Override
                    public int getBatchSize() {
                        return reservations.size();
                    }
                });
    }

    @DisplayName("Reservation 객체를 저장한다")
    @Test
    void create_reservation_test() {
        // given
        String name = "루키";
        LocalDate date = LocalDate.of(2024, 12, 31);
        LocalTime time = LocalTime.of(23, 59);
        Reservation reservation = new Reservation("루키", date, time);

        // when
        ReservationResponse actual = reservationDao.createReservation(reservation);

        // then
        ReservationResponse expected = new ReservationResponse(4L, name, date, time);
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("Reservation 데이터를 정상적으로 삭제한다")
    @Test
    void delete_reservation_test() {
        // given
        Long id = 3L;

        // when
        reservationDao.deleteReservation(id);

        // then
        assertThat(reservationDao.getReservations()).hasSize(2);
    }

    @DisplayName("저장된 Reservation 목록들을 조회한다")
    @Test
    void get_reservations_test() {
        // when
        List<ReservationResponse> reservations = reservationDao.getReservations();

        // then
        assertThat(reservations).containsExactly(
                new ReservationResponse(1L, "루키", LocalDate.of(2025, 4, 20), LocalTime.of(15, 30)),
                new ReservationResponse(2L, "슬링키", LocalDate.of(2025, 4, 18), LocalTime.of(21, 22)),
                new ReservationResponse(3L, "범블비", LocalDate.of(2025, 4, 21), LocalTime.of(11, 30))
        );
    }
}
