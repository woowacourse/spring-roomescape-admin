package roomescape.advisor.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.time.LocalTime;
import org.springframework.stereotype.Component;
import roomescape.error.ErrorResponse;

@Component
public class LocalTimeFormatErrorHandler implements FormatErrorHandler {
    @Override
    public boolean supports(InvalidFormatException e) {
        return e.getTargetType() == LocalTime.class;
    }

    @Override
    public ErrorResponse handle(InvalidFormatException e) {
        return new ErrorResponse("시간 형식은 HH:mm 이어야 합니다.(ex. 14:00)");
    }
}
