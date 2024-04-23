package roomescape.dao;

import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import roomescape.data.vo.ReservationTime;

public interface ReservationTimeDao {
    long save(final ReservationTime time);
    List<ReservationTime> findAll();

    default RowMapper<ReservationTime> actorRowMapper() {
        return (resultSet, rowNum) -> new ReservationTime(resultSet.getLong("id"),
                resultSet.getTime("start_at").toLocalTime());
    }
}
