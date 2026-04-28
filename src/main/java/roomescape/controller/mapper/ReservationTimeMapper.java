package roomescape.controller.mapper;

import org.springframework.stereotype.Component;
import roomescape.dto.ReservationTimeCreateRequest;
import roomescape.service.command.ReservationTimeCreateCommand;

@Component
public class ReservationTimeMapper {

    public ReservationTimeCreateCommand mapCreate(
            ReservationTimeCreateRequest requestDto
    ) {
        return new ReservationTimeCreateCommand(
                requestDto.startAt()
        );
    }
}
