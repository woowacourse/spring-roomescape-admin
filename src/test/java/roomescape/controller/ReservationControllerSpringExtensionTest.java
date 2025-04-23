package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import roomescape.config.SpringExtensionTestConfig;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;
import roomescape.exceptions.EntityNotFoundException;
import roomescape.repository.ReservationH2Repository;
import roomescape.repository.ReservationTimeH2Repository;
import roomescape.service.ReservationService;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringExtensionTestConfig.class)
@ActiveProfiles("spring-extension-test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationControllerSpringExtensionTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ReservationController controller;

    @BeforeEach
    public void setup() {
        String timeSql = "INSERT INTO reservation_time (start_at) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(timeSql, new String[]{"id"});
            ps.setString(1, "15:00");
            return ps;
        }, keyHolder);

        ReservationService service = new ReservationService(new ReservationH2Repository(jdbcTemplate),
                new ReservationTimeH2Repository(jdbcTemplate));
        controller = new ReservationController(service);
    }

    @Test
    @DisplayName("예약 목록을 조회한다.")
    void readReservation() {
        //given
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.of(15, 0));
        Reservation reservation1 = new Reservation(1L, "브라운", LocalDate.now().plusDays(1), reservationTime);
        Reservation reservation2 = new Reservation(2L, "네오", LocalDate.now().plusDays(1), reservationTime);

        List<Reservation> reservations = List.of(reservation1, reservation2);
        String sql = "insert into reservation (name, date, time_id) values (?, ?, ?)";
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
        ReservationRequestDto dto = new ReservationRequestDto("브라운", fixedDate, 1L);
        //when
        controller.postReservation(dto);
        //then
        String sql = "select r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at as time_value "
                + "from reservation as r "
                + "inner join reservation_time as t "
                + "on r.time_id = t.id "
                + "where r.id = ?";
        Reservation reservation = jdbcTemplate.queryForObject(sql, getReservationRowMapper(), 1L);
        Assertions.assertNotNull(reservation);
        assertThat(reservation.name()).isEqualTo(dto.name());
    }

    @Test
    @DisplayName("존재하는 ID로 삭제 요청 시 성공적으로 처리되어야 한다")
    void deleteExistingReservation() {
        //given
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.of(15, 0));

        Reservation reservation1 = new Reservation(1L, "브라운", LocalDate.now().plusDays(1), reservationTime);
        Reservation reservation2 = new Reservation(2L, "네오", LocalDate.now().plusDays(1), reservationTime);

        List<Reservation> reservations = List.of(reservation1, reservation2);
        String insertSql = "insert into reservation (name, date, time_id) values (?, ?, ?)";
        jdbcTemplate.batchUpdate(insertSql, getBatchPreparedStatementSetter(reservations));
        long reservationId = 1L;

        //when
        controller.deleteReservation(reservationId);

        //then
        String selectSql = "select r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at as time_value "
                + "from reservation as r "
                + "inner join reservation_time as t "
                + "on r.time_id = t.id "
                + "where r.id = ?";
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

    private RowMapper<Reservation> getReservationRowMapper() {
        return (rs, rowNum) -> new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getDate("date").toLocalDate(),
                new ReservationTime(
                        rs.getLong("time_id"),
                        rs.getTime("time_value").toLocalTime()
                )
        );
    }

    private BatchPreparedStatementSetter getBatchPreparedStatementSetter(List<Reservation> reservations) {
        return new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Reservation reservation = reservations.get(i);
                ps.setString(1, reservation.name());
                ps.setDate(2, Date.valueOf(reservation.date()));
                ps.setLong(3, reservation.time().id());
            }

            @Override
            public int getBatchSize() {
                return reservations.size();
            }
        };
    }
}
