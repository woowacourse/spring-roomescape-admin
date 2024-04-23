package roomescape.repository.time;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import roomescape.domain.ReservationTime;

@ExtendWith(MockitoExtension.class)
class ReservationTimeRowMapperTest {

    private ReservationTimeRowMapper rowMapper;

    @BeforeEach
    void setUp() {
        rowMapper = new ReservationTimeRowMapper();
    }

    @Mock
    private ResultSet resultSet;

    @DisplayName("RowMapper는 ResultSet을 이용하여 ReservationTime을 매핑해서 반환한다.")
    @Test
    void return_reservation_time_by_rowMapper() throws SQLException {
        given(resultSet.getLong("id")).willReturn(1L);
        given(resultSet.getString("start_at")).willReturn("17:14");

        ReservationTime reservationTime = rowMapper.mapRow(resultSet, 1);

        assertAll(
                () -> assertThat(reservationTime.getId()).isEqualTo(1L),
                () -> assertThat(reservationTime.getStartAt()).isEqualTo("17:14")
        );
    }
}
