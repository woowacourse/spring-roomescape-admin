package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.sql.Types;
import org.h2.tools.SimpleResultSet;
import org.junit.jupiter.api.Test;
import roomescape.domain.ReservationTime;

class ReservationTimeRowMapperTest {

    @Test
    void mapRow() throws SQLException {
        SimpleResultSet rs = new SimpleResultSet();
        rs.addColumn("id", Types.BIGINT, 10, 0);
        rs.addColumn("start_at", Types.VARCHAR, 255, 0);
        rs.addRow(1, "10:00");
        rs.next();

        ReservationTimeRowMapper rowMapper = new ReservationTimeRowMapper();
        ReservationTime actual = rowMapper.mapRow(rs, 1);

        assertAll(
                () -> assertThat(actual.getId()).isEqualTo(1),
                () -> assertThat(actual.getStartAt()).isEqualTo("10:00")
        );
    }
}
