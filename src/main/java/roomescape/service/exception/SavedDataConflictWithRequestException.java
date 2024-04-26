package roomescape.service.exception;

import roomescape.controller.exception.IllegalRequestException;

public class SavedDataConflictWithRequestException extends IllegalRequestException {
    public SavedDataConflictWithRequestException(String s) {
        super(s);
    }
}
