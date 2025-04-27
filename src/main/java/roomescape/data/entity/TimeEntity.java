package roomescape.data.entity;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.springframework.jdbc.core.RowMapper;
import roomescape.business.domain.Time;

public record TimeEntity(Long id, String startAt) {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final RowMapper<TimeEntity> DEFAULT_ROW_MAPPER =
            (rs, rowNum) -> new TimeEntity(
                    rs.getLong(1),
                    rs.getString(2));

    public static RowMapper<TimeEntity> getDefaultRowMapper() {
        return DEFAULT_ROW_MAPPER;
    }

    public Time toDomain() {
        return Time.createWithId(
                id,
                LocalTime.parse(startAt, TIME_FORMATTER)
        );
    }

    public static TimeEntity from(final Time time) {
        return new TimeEntity(
                time.getId(),
                TIME_FORMATTER.format(time.getStartAt())
        );
    }
}
