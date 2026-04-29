package roomescape.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import roomescape.dto.ReservationCreateReqDto;
import roomescape.dto.ReservationResDto;
import roomescape.repository.ReservationDao;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

class ReservationServiceTest {

    private ReservationService reservationService;
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
        jdbcTemplate.execute(
                "CREATE TABLE reservation (" +
                "    id      BIGINT       NOT NULL AUTO_INCREMENT," +
                "    name    VARCHAR(255) NOT NULL," +
                "    date    VARCHAR(255) NOT NULL," +
                "    time    VARCHAR(255) NOT NULL," +
                "    PRIMARY KEY (id)" +
                ")"
        );

        ReservationDao reservationDao = new ReservationDao(dataSource);
        reservationService = new ReservationService(reservationDao);
    }

    @Test
    void 예약_생성_정상() {
        // given
        String name = "브라운";
        LocalDate date = LocalDate.of(2023, 7, 4);
        LocalTime time = LocalTime.of(15, 40);

        // when
        ReservationResDto reservation = reservationService.createReservation(new ReservationCreateReqDto(name, date, time));
        ReservationResDto findReservation = reservationService.getReservationById(reservation.getId());

        // then
        Assertions.assertEquals(name, findReservation.getName());
        Assertions.assertEquals(date, findReservation.getDate());
        Assertions.assertEquals(time, findReservation.getTime());
    }

    @Test
    void 예약_단일_조회_정상() {
        // given
        String name = "브라운";
        LocalDate date = LocalDate.of(2023, 7, 4);
        LocalTime time = LocalTime.of(15, 40);
        ReservationResDto reservation = reservationService.createReservation(new ReservationCreateReqDto(name, date, time));

        // when
        ReservationResDto findReservation = reservationService.getReservationById(reservation.getId());

        // then
        Assertions.assertEquals(name, findReservation.getName());
        Assertions.assertEquals(date, findReservation.getDate());
        Assertions.assertEquals(time, findReservation.getTime());
    }

    @Test
    void 예약_단일_조회_에러() {
        // given
        String name = null;
        LocalDate date = LocalDate.of(2023, 7, 4);
        LocalTime time = LocalTime.of(15, 40);

        // when && then
        Assertions.assertThrows(IllegalArgumentException.class, () -> reservationService.createReservation(new ReservationCreateReqDto(name, date, time)));
    }

    @Test
    void 예약_목록_조회_정상() {
        // given
        String name = "브라운";
        LocalDate date = LocalDate.of(2023, 5, 3);
        LocalTime time = LocalTime.of(15, 20);
        reservationService.createReservation(new ReservationCreateReqDto(name, date, time));

        String name2 = "포비";
        LocalDate date2 = LocalDate.of(2025, 7, 4);
        LocalTime time2 = LocalTime.of(17, 40);
        reservationService.createReservation(new ReservationCreateReqDto(name2, date2, time2));

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
        ReservationResDto reservation = reservationService.createReservation(new ReservationCreateReqDto(name, date, time));

        // when
        reservationService.deleteReservation(reservation.getId());

        // then
        Assertions.assertThrows(IllegalArgumentException.class, () -> reservationService.getReservationById(reservation.getId()));
    }
}
