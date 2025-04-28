package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.sql.Date;
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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import roomescape.config.SpringExtensionTestConfig;
import roomescape.dao.ReservationH2Dao;
import roomescape.dao.ReservationTimeH2Dao;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;
import roomescape.exceptions.EntityNotFoundException;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringExtensionTestConfig.class)
@ActiveProfiles("spring-extension-test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationControllerSpringExtensionTest {

    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    private ReservationController controller;

    @BeforeEach
    public void setup() {
        String timeSql = "insert into reservation_time (start_at) values (:startAt)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource("startAt", "15:00");

        namedJdbcTemplate.update(timeSql, params, keyHolder, new String[]{"id"});

        ReservationService service = new ReservationService(new ReservationH2Dao(namedJdbcTemplate));
        ReservationTimeService timeService = new ReservationTimeService(new ReservationTimeH2Dao(namedJdbcTemplate));
        controller = new ReservationController(service, timeService);
    }

    @Test
    @DisplayName("예약 목록을 조회한다.")
    void readReservation() {
        //given
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.of(15, 0));
        Reservation reservation1 = new Reservation(1L, "브라운", LocalDate.now().plusDays(1), reservationTime);
        Reservation reservation2 = new Reservation(2L, "네오", LocalDate.now().plusDays(1), reservationTime);

        List<Reservation> reservations = List.of(reservation1, reservation2);
        String sql = "insert into reservation (name, date, time_id) values (:name, :date, :timeId)";

        SqlParameterSource[] batch = reservations.stream()
                .map(ReservationControllerSpringExtensionTest::getMapSqlParameterSource)
                .toArray(SqlParameterSource[]::new);

        namedJdbcTemplate.batchUpdate(sql, batch);

        //when
        List<ReservationResponse> result = controller.readReservation();

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.getFirst()).isEqualTo(ReservationResponse.toDto(reservation1));
        assertThat(result.getLast()).isEqualTo(ReservationResponse.toDto(reservation2));
    }

    @Test
    @DisplayName("예약 관리 페이지 내에서 예약 추가")
    void postReservation() {
        //given
        LocalDate fixedDate = LocalDate.of(2026, 5, 15);
        ReservationRequest dto = new ReservationRequest("브라운", fixedDate, 1L);
        //when
        controller.postReservation(dto);
        //then
        String sql = "select r.id as id, r.name, r.date, t.id as time_id, t.start_at as time_value "
                + "from reservation as r "
                + "inner join reservation_time as t "
                + "on r.time_id = t.id "
                + "where r.id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource("id", 1L);
        Reservation reservation = namedJdbcTemplate.queryForObject(sql, params, getReservationRowMapper());

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
        String insertSql = "insert into reservation (name, date, time_id) values (:name, :date, :timeId)";

        SqlParameterSource[] batch = reservations.stream()
                .map(ReservationControllerSpringExtensionTest::getMapSqlParameterSource)
                .toArray(SqlParameterSource[]::new);

        namedJdbcTemplate.batchUpdate(insertSql, batch);
        long reservationId = 1L;

        //when
        controller.deleteReservation(reservationId);

        //then
        String selectSql = "select r.id as id, r.name, r.date, t.id as time_id, t.start_at as time_value "
                + "from reservation as r "
                + "inner join reservation_time as t "
                + "on r.time_id = t.id "
                + "where r.id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource("id", 1L);

        assertThatThrownBy(() -> namedJdbcTemplate.queryForObject(selectSql, params, Reservation.class))
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

    private static MapSqlParameterSource getMapSqlParameterSource(Reservation reservation) {
        return new MapSqlParameterSource()
                .addValue("name", reservation.name())
                .addValue("date", Date.valueOf(reservation.date()))
                .addValue("timeId", reservation.time().id());
    }
}
