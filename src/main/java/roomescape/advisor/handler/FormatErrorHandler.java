package roomescape.advisor.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import roomescape.error.ErrorResponse;

public interface FormatErrorHandler {
    boolean supports(InvalidFormatException e);

    ErrorResponse handle(InvalidFormatException e);
}
