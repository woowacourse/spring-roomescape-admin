package roomescape.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationResDto;
import roomescape.repository.ReservationDao;
import roomescape.repository.ReservationTimeDao;
import roomescape.service.command.ReservationCommand;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

class ReservationServiceTest {

    private ReservationService reservationService;
    private ReservationTimeDao reservationTimeDao;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        DataSource dataSource = new DriverManagerDataSource(
                "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
                "sa",
                ""
        );

        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation");
        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation_time");

        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS reservation_time (" +
                        " id       BIGINT       NOT NULL AUTO_INCREMENT," +
                        " start_at VARCHAR(255) NOT NULL," +
                        " PRIMARY KEY (id)" +
                        ");"
        );

        jdbcTemplate.execute(
                " CREATE TABLE IF NOT EXISTS reservation (" +
                        " id      BIGINT       NOT NULL AUTO_INCREMENT," +
                        " name    VARCHAR(255) NOT NULL," +
                        " date    VARCHAR(255) NOT NULL," +
                        " time_id BIGINT," +
                        " PRIMARY KEY (id)," +
                        " FOREIGN KEY (time_id) REFERENCES reservation_time (id)" +
                        ");"
        );

        ReservationDao reservationDao = new ReservationDao(dataSource);
        reservationTimeDao = new ReservationTimeDao(dataSource);
        reservationService = new ReservationService(reservationDao, reservationTimeDao);
    }

    @Test
    void 예약_생성_정상() {
        // given
        String name = "브라운";
        LocalDate date = LocalDate.of(2023, 7, 4);
        LocalTime time = LocalTime.of(15, 40);
        ReservationTime reservationTime = reservationTimeDao.save(ReservationTime.create(time));

        // when
        ReservationResDto reservation = reservationService.createReservation(new ReservationCommand(name, date, reservationTime.getId()));

        // then
        Assertions.assertEquals(name, reservation.getName());
        Assertions.assertEquals(date, reservation.getDate());
    }

    @Test
    void 예약_단일_조회_정상() {
        // given
        String name = "브라운";
        LocalDate date = LocalDate.of(2023, 7, 4);
        LocalTime time = LocalTime.of(15, 40);
        ReservationTime reservationTime = reservationTimeDao.save(ReservationTime.create(time));
        ReservationResDto reservation = reservationService.createReservation(new ReservationCommand(name, date, reservationTime.getId()));

        // when
        ReservationResDto findReservation = reservationService.getReservationById(reservation.getId());

        // then
        Assertions.assertEquals(name, findReservation.getName());
        Assertions.assertEquals(date, findReservation.getDate());
    }

    @Test
    void 예약_단일_조회_에러() {
        // given
        String name = null;
        LocalDate date = LocalDate.of(2023, 7, 4);
        LocalTime time = LocalTime.of(15, 40);
        ReservationTime reservationTime = reservationTimeDao.save(ReservationTime.create(time));

        // when && then
        Assertions.assertThrows(IllegalArgumentException.class, () -> reservationService.createReservation(new ReservationCommand(name, date, reservationTime.getId())));
    }

    @Test
    void 예약_목록_조회_정상() {
        // given
        String name = "브라운";
        LocalDate date = LocalDate.of(2023, 5, 3);
        LocalTime time = LocalTime.of(15, 20);
        ReservationTime savedReservationTime = reservationTimeDao.save(ReservationTime.create(time));
        reservationService.createReservation(new ReservationCommand(name, date, savedReservationTime.getId()));

        String name2 = "포비";
        LocalDate date2 = LocalDate.of(2025, 7, 4);
        LocalTime time2 = LocalTime.of(17, 40);
        ReservationTime savedReservationTime2 = reservationTimeDao.save(ReservationTime.create(time2));
        reservationService.createReservation(new ReservationCommand(name2, date2, savedReservationTime2.getId()));

        // when
        List<ReservationResDto> reservations = reservationService.getReservations();

        // then
        Assertions.assertEquals(2, reservations.size());

        Assertions.assertEquals(name, reservations.get(0).getName());
        Assertions.assertEquals(name2, reservations.get(1).getName());
    }

    @Test
    void 예약_삭제_정상() {
        // given
        String name = "브라운";
        LocalDate date = LocalDate.of(2023, 7, 4);
        LocalTime time = LocalTime.of(15, 40);
        ReservationTime savedReservationTime = reservationTimeDao.save(ReservationTime.create(time));
        ReservationResDto reservation = reservationService.createReservation(new ReservationCommand(name, date, savedReservationTime.getId()));

        // when
        reservationService.deleteReservation(reservation.getId());

        // then
        Assertions.assertThrows(IllegalArgumentException.class, () -> reservationService.getReservationById(reservation.getId()));
    }
}
