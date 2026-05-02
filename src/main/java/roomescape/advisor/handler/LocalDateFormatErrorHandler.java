package roomescape.advisor.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.time.LocalDate;
import org.springframework.stereotype.Component;
import roomescape.error.ErrorResponse;

@Component
public class LocalDateFormatErrorHandler implements FormatErrorHandler {
    @Override
    public boolean supports(InvalidFormatException e) {
        return e.getTargetType() == LocalDate.class;
    }

    @Override
    public ErrorResponse handle(InvalidFormatException e) {
        return new ErrorResponse("날짜 형식은 yyyy-MM-dd 이어야 합니다.(ex. 2026-05-02)");
    }
}
