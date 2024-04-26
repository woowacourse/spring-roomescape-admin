package roomescape.entity.exception;

import roomescape.controller.exception.IllegalRequestException;

public class NullFieldExistException extends IllegalRequestException {
    public NullFieldExistException(String s) {
        super(s);
    }
}
