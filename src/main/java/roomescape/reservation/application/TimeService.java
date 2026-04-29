package roomescape.reservation.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.reservation.domain.Time;
import roomescape.reservation.infra.TimeRepository;
import roomescape.reservation.presentation.dto.request.TimeSaveRequest;
import roomescape.reservation.presentation.dto.response.TimeSaveResponse;

@Service
@RequiredArgsConstructor
public class TimeService {
    private final TimeRepository timeRepository;

    public TimeSaveResponse saveTime(TimeSaveRequest body) {
        Time time = timeRepository.save(body.startAt());

        return new TimeSaveResponse(time.getId(), time.getStartAt());
    }
}
