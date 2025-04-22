package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
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
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.entity.Reservation;
import roomescape.exceptions.EntityNotFoundException;
import roomescape.repository.ReservationH2Repository;

public class ReservationControllerTest {

    private static EmbeddedDatabase dataSource;
    private static JdbcTemplate jdbcTemplate;
    private static ReservationController controller;

    @BeforeAll
    public static void setUpClass() {
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("schema.sql")
                .build();
        jdbcTemplate = new JdbcTemplate(dataSource);
        controller = new ReservationController(
                new ReservationH2Repository(jdbcTemplate));
    }

    @BeforeEach
    public void setup() {
        jdbcTemplate.update("delete from reservation");
        jdbcTemplate.execute("alter table reservation alter column id restart with 1");
    }

    @Test
    @DisplayName("예약 목록을 조회한다.")
    void readReservation() {
        //given
        Reservation reservation1 = new Reservation(1L, "브라운", LocalDate.now().plusDays(1), LocalTime.of(15, 50));
        Reservation reservation2 = new Reservation(2L, "네오", LocalDate.now().plusDays(1), LocalTime.of(15, 55));

        List<Reservation> reservations = List.of(reservation1, reservation2);
        String sql = "insert into reservation (name, date, time) values (?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, getBatchPreparedStatementSetter(reservations));

        //when
        List<ReservationResponseDto> result = controller.readReservation();

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.getFirst()).isEqualTo(ReservationResponseDto.toDto(reservation1));
        assertThat(result.getLast()).isEqualTo(ReservationResponseDto.toDto(reservation2));
    }

    @Test
    @DisplayName("예약 관리 페이지 내에서 예약 추가")
    void postReservation() {
        //given
        LocalDate fixedDate = LocalDate.of(2026, 5, 15);
        LocalTime fixedTime = LocalTime.of(14, 30);

        ReservationRequestDto dto = new ReservationRequestDto("브라운", fixedDate, fixedTime);
        //when
        controller.postReservation(dto);
        //then
        String sql = "select * from reservation where id=?";
        Reservation reservation = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getDate("date").toLocalDate(),
                rs.getTime("time").toLocalTime()
        ), 1L);
        Assertions.assertNotNull(reservation);
        assertThat(reservation).isEqualTo(dto.toEntity(1L));
    }

    @Test
    @DisplayName("존재하는 ID로 삭제 요청 시 성공적으로 처리되어야 한다")
    void deleteExistingReservation() {
        //given
        Reservation reservation1 = new Reservation(1L, "브라운", LocalDate.now().plusDays(1), LocalTime.of(15, 50));
        Reservation reservation2 = new Reservation(2L, "네오", LocalDate.now().plusDays(1), LocalTime.of(15, 55));

        List<Reservation> reservations = List.of(reservation1, reservation2);
        String insertSql = "insert into reservation (name, date, time) values (?, ?, ?)";
        jdbcTemplate.batchUpdate(insertSql, getBatchPreparedStatementSetter(reservations));
        long reservationId = 1L;

        //when
        controller.deleteReservation(reservationId);

        //then
        String selectSql = "select * from reservation where id=?";
        assertThatThrownBy(() -> jdbcTemplate.queryForObject(selectSql, Reservation.class, 1L))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("존재하지 않는 ID로 삭제 요청 시 404 응답이 반환되어야 한다")
    void deleteNonExistingReservation() {
        //given
        long nonExistingId = 999L;

        //when & then
        assertThatThrownBy(() -> controller.deleteReservation(nonExistingId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("[ERROR] 예약 데이터를 찾을 수 없습니다:999");
    }

    private BatchPreparedStatementSetter getBatchPreparedStatementSetter(List<Reservation> reservations) {
        return new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Reservation reservation = reservations.get(i);
                ps.setString(1, reservation.name());
                ps.setDate(2, Date.valueOf(reservation.date()));
                ps.setTime(3, Time.valueOf(reservation.time()));
            }

            @Override
            public int getBatchSize() {
                return reservations.size();
            }
        };
    }
}
