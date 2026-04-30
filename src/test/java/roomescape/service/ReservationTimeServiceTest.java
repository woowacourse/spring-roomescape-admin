package roomescape.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import roomescape.dto.ReservationTimeCreateReqDto;
import roomescape.dto.ReservationTimeResDto;
import roomescape.repository.ReservationTimeDao;

import javax.sql.DataSource;
import java.time.LocalTime;
import java.util.List;

class ReservationTimeServiceTest {

    private ReservationTimeService reservationTimeService;
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

        ReservationTimeDao reservationTimeDao = new ReservationTimeDao(dataSource);
        reservationTimeService = new ReservationTimeService(reservationTimeDao);
    }

    @Test
    void 시간_생성_정상() {
        // given
        LocalTime time = LocalTime.of(15, 40);
        ReservationTimeCreateReqDto reservationTimeCreateReqDto = new ReservationTimeCreateReqDto(time);

        // when
        ReservationTimeResDto savedTime = reservationTimeService.createTime(reservationTimeCreateReqDto);

        // then
        Assertions.assertEquals(time, savedTime.getStartAt());
    }

    @Test
    void 시간_목록_조회_정상() {
        // given
        LocalTime time = LocalTime.of(15, 40);
        ReservationTimeCreateReqDto reservationTimeCreateReqDto = new ReservationTimeCreateReqDto(time);
        reservationTimeService.createTime(reservationTimeCreateReqDto);

        LocalTime time2 = LocalTime.of(18, 20);
        ReservationTimeCreateReqDto reservationTimeCreateReqDto2 = new ReservationTimeCreateReqDto(time2);
        reservationTimeService.createTime(reservationTimeCreateReqDto2);

        // when
        List<ReservationTimeResDto> reservationTimeResDtos = reservationTimeService.getTimes();

        // then
        Assertions.assertEquals(2, reservationTimeResDtos.size());

        Assertions.assertEquals(time, reservationTimeResDtos.get(0).getStartAt());
        Assertions.assertEquals(time2, reservationTimeResDtos.get(1).getStartAt());
    }

    @Test
    void 시간_삭제_정상() {
        // given
        LocalTime time = LocalTime.of(15, 40);
        ReservationTimeCreateReqDto reservationTimeCreateReqDto = new ReservationTimeCreateReqDto(time);
        ReservationTimeResDto savedTime = reservationTimeService.createTime(reservationTimeCreateReqDto);

        // when
        reservationTimeService.deleteTime(savedTime.getId());

        // then
        List<ReservationTimeResDto> times = reservationTimeService.getTimes();
        Assertions.assertEquals(0, times.size());
    }
}
