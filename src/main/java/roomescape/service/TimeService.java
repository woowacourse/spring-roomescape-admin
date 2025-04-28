package roomescape.service;

import java.util.List;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;

public interface TimeService {

    List<TimeResponse> findAllTime();

    TimeResponse createTime(TimeRequest timeRequest);

    int deleteTimeById(Long id);
}
