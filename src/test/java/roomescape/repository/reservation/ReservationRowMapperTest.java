package roomescape.repository.reservation;

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
import roomescape.domain.Reservation;

@ExtendWith(MockitoExtension.class)
class ReservationRowMapperTest {

    private ReservationRowMapper rowMapper;

    @BeforeEach
    void setUp() {
        rowMapper = new ReservationRowMapper();
    }

    @Mock
    private ResultSet resultSet;

    @DisplayName("RowMapper는 ResultSet을 이용하여 Reservation을 매핑해서 반환한다.")
    @Test
    void return_reservation_by_map_row() throws SQLException {
        given(resultSet.getLong("reservation_id")).willReturn(1L);
        given(resultSet.getString("name")).willReturn("재즈");
        given(resultSet.getString("date")).willReturn("2024-04-23");
        given(resultSet.getLong("time_id")).willReturn(1L);
        given(resultSet.getString("time_value")).willReturn("11:12");

        Reservation reservation = rowMapper.mapRow(resultSet, 1);

        assertAll(
                () -> assertThat(reservation.getId()).isEqualTo(1L),
                () -> assertThat(reservation.getName()).isEqualTo("재즈"),
                () -> assertThat(reservation.getDate()).isEqualTo("2024-04-23"),
                () -> assertThat(reservation.getTimeId()).isEqualTo(1L),
                () -> assertThat(reservation.getTimeStartAt()).isEqualTo("11:12")
        );
    }

}
