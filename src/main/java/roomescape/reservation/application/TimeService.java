package roomescape.reservation.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.reservation.domain.Time;
import roomescape.reservation.infra.TimeRepository;
import roomescape.reservation.presentation.dto.request.TimeSaveRequest;
import roomescape.reservation.presentation.dto.response.TimeFindResponse;
import roomescape.reservation.presentation.dto.response.TimeSaveResponse;

@Service
@RequiredArgsConstructor
public class TimeService {
    private final TimeRepository timeRepository;

    public TimeSaveResponse saveTime(TimeSaveRequest body) {
        Time time = timeRepository.save(body.startAt());

        return new TimeSaveResponse(time.getId(), time.getStartAt());
    }

    public List<TimeFindResponse> findAllTimes(){
        return timeRepository.findAll().stream()
                .map(time -> new TimeFindResponse(
                        time.getId(),
                        time.getStartAt()
                ))
                .toList();
    }
}
