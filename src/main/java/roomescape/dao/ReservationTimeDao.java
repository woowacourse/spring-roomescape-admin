package roomescape.dao;

import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import roomescape.data.vo.ReservationTime;

public interface ReservationTimeDao {
    long save(final ReservationTime time);
    List<ReservationTime> findAll();
    void delete(final long id);
    ReservationTime findOne(final long id);

    default RowMapper<ReservationTime> actorRowMapper() {
        return (resultSet, rowNum) -> new ReservationTime(resultSet.getLong("id"),
                resultSet.getTime("start_at").toLocalTime());
    }
}
