package roomescape.time.repository;

import java.util.List;

public interface TimesRepository {
    TimeEntity saveTime(TimeEntity entity);

    List<TimeEntity> getTimes();

    void deleteTimeById(Long id);
}
