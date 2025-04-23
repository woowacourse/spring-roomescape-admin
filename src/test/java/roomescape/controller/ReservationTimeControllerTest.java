package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import roomescape.dao.ReservationTimeH2Dao;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;
import roomescape.exceptions.EntityNotFoundException;
import roomescape.service.ReservationTimeService;

public class ReservationTimeControllerTest {

    private static EmbeddedDatabase dataSource;
    private static JdbcTemplate jdbcTemplate;
    private static ReservationTimeController controller;

    @BeforeAll
    public static void setUpClass() {
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("schema.sql")
                .build();
        jdbcTemplate = new JdbcTemplate(dataSource);

        ReservationTimeService service = new ReservationTimeService(new ReservationTimeH2Dao(jdbcTemplate));
        controller = new ReservationTimeController(service);
    }

    @BeforeEach
    public void setup() {
        jdbcTemplate.update("delete from reservation_time");
        jdbcTemplate.execute("alter table reservation_time alter column id restart with 1");
    }

    @Test
    @DisplayName("예약 시간 목록을 조회한다.")
    void readReservationTime() {
        //given
        ReservationTime reservationTime1 = new ReservationTime(1L, LocalTime.of(15, 0));
        ReservationTime reservationTime2 = new ReservationTime(2L, LocalTime.of(16, 0));

        List<ReservationTime> reservations = List.of(reservationTime1, reservationTime2);
        String sql = "insert into reservation_time (start_at) values (?)";
        jdbcTemplate.batchUpdate(sql, getBatchPreparedStatementSetter(reservations));

        //when
        List<ReservationTimeResponse> result = controller.readReservationTime();

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.getFirst()).isEqualTo(ReservationTimeResponse.toDto(reservationTime1));
        assertThat(result.getLast()).isEqualTo(ReservationTimeResponse.toDto(reservationTime2));
    }

    @Test
    @DisplayName("예약 시간 관리 페이지 내에서 예약 시간을 추가한다.")
    void postReservationTime() {
        //given
        LocalTime fixedTime = LocalTime.of(14, 30);

        ReservationTimeRequest dto = new ReservationTimeRequest(fixedTime);
        //when
        controller.postReservationTime(dto);
        //then
        String sql = "select * from reservation_time where id=?";
        ReservationTime reservationTime = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new ReservationTime(
                rs.getLong("id"),
                rs.getTime("start_at").toLocalTime()
        ), 1L);
        Assertions.assertNotNull(reservationTime);
        assertThat(reservationTime).isEqualTo(dto.toEntity(1L));
    }

    @Test
    @DisplayName("존재하는 ID로 삭제 요청 시 성공적으로 처리되어야 한다")
    void deleteExistingReservationTime() {
        //given
        ReservationTime reservationTime1 = new ReservationTime(1L, LocalTime.of(15, 50));
        ReservationTime reservationTime2 = new ReservationTime(2L, LocalTime.of(15, 55));

        List<ReservationTime> reservationTimes = List.of(reservationTime1, reservationTime2);
        String insertSql = "insert into reservation_time (start_at) values (?)";
        jdbcTemplate.batchUpdate(insertSql, getBatchPreparedStatementSetter(reservationTimes));
        long reservationId = 1L;

        //when
        controller.deleteReservationTime(reservationId);

        //then
        String selectSql = "select * from reservation_time where id=?";
        assertThatThrownBy(() -> jdbcTemplate.queryForObject(selectSql, Reservation.class, 1L))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("존재하지 않는 ID로 삭제 요청 시 404 응답이 반환되어야 한다")
    void deleteNonExistingReservationTime() {
        //given
        long nonExistingId = 999L;

        //when & then
        assertThatThrownBy(() -> controller.deleteReservationTime(nonExistingId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("[ERROR] 예약 데이터를 찾을 수 없습니다:999");
    }

    private BatchPreparedStatementSetter getBatchPreparedStatementSetter(List<ReservationTime> reservationTimes) {
        return new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ReservationTime reservationTime = reservationTimes.get(i);
                ps.setTime(1, Time.valueOf(reservationTime.startAt()));
            }

            @Override
            public int getBatchSize() {
                return reservationTimes.size();
            }
        };
    }
}
