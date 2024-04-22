package roomescape.reservation.service;

import org.springframework.stereotype.Service;
import roomescape.reservation.domain.TimeSlot;
import roomescape.reservation.dto.TimeSlotRequest;
import roomescape.reservation.dto.TimeSlotResponse;
import roomescape.reservation.repository.TimeSlotRepository;

@Service
public class TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;

    public TimeSlotService(final TimeSlotRepository timeSlotRepository) {
        this.timeSlotRepository = timeSlotRepository;
    }

    public TimeSlotResponse create(TimeSlotRequest timeSlotRequest) {
        TimeSlot timeSlot = new TimeSlot(timeSlotRequest.startAt());
        return TimeSlotResponse.from(timeSlotRepository.save(timeSlot));
    }
}
