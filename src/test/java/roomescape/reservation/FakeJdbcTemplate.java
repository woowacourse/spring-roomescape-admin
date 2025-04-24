package roomescape.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.lang.Nullable;
import roomescape.reservationTime.ReservationTime;

public class FakeJdbcTemplate extends JdbcTemplate {
//
//    private final List<Reservation> database;
//
//    public FakeJdbcTemplate() {
//        LocalDate date = LocalDate.of(2025, 4, 22);
//        ReservationTime time = new ReservationTime(null, LocalTime.of(10, 0));
//
//        this.database = new ArrayList<>(List.of(
//                new Reservation(1L, "mimi", date, time),
//                new Reservation(2L, "norang", date, time),
//                new Reservation(3L, "mint", date, time)
//        ));
//    }
//
//    public FakeJdbcTemplate(List<Reservation> reservations) {
//        this.database = reservations;
//    }
//
//    @Override
//    public int update(PreparedStatementCreator preparedStatementCreator, KeyHolder keyHolder) {
//        keyHolder.getKeyList().add(Map.of("id", 1L));
//        return 1;
//    }
//
//    @Override
//    public <T> List<T> query(String sql, RowMapper<T> rowMapper) {
//        return (List<T>) database;
//    }
//
//    @Override
//    public int update(String sql, @Nullable Object... args) {
//        Long id = (Long) args[0];
//        database.removeIf(reservation -> Objects.equals(reservation.id(), id));
//        return 1;
//    }
//
//    @Override
//    public boolean equals(Object other) {
//        if (other == null || getClass() != other.getClass()) {
//            return false;
//        }
//        FakeJdbcTemplate that = (FakeJdbcTemplate) other;
//        return Objects.equals(database, that.database);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hashCode(database);
//    }
}
