package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.sql.Types;
import org.h2.tools.SimpleResultSet;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;

class ReservationRowMapperTest {

    @Test
    void mapRow() throws SQLException {
        SimpleResultSet rs = new SimpleResultSet();
        rs.addColumn("reservation_id", Types.BIGINT, 10, 0);
        rs.addColumn("name", Types.VARCHAR, 255, 0);
        rs.addColumn("date", Types.VARCHAR, 255, 0);
        rs.addColumn("time_id", Types.BIGINT, 10, 0);
        rs.addColumn("time_value", Types.VARCHAR, 255, 0);
        rs.addRow(1, "Jerry", "2024-04-23", 1, "10:00");
        rs.next();

        ReservationRowMapper rowMapper = new ReservationRowMapper();
        Reservation actual = rowMapper.mapRow(rs, 1);

        assertAll(
                () -> assertThat(actual.getId()).isEqualTo(1),
                () -> assertThat(actual.getName()).isEqualTo("Jerry"),
                () -> assertThat(actual.getDate()).isEqualTo("2024-04-23"),
                () -> assertThat(actual.getTime().getId()).isEqualTo(1),
                () -> assertThat(actual.getTime().getStartAt()).isEqualTo("10:00")
        );
    }
}
