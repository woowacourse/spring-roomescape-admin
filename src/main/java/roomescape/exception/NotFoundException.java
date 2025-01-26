package roomescape.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends BadRequestException {

    private final long id;

    public NotFoundException(ErrorCode code, long id) {
        super(code);
        this.id = id;
    }

    public NotFoundException(ErrorCode code, long id, Throwable cause) {
        super(code, cause);
        this.id = id;
    }

    @Override
    public String getMessage() {
        return this.code.getFormattedMessage(id);
    }
}
