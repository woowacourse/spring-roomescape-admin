package roomescape.console.view.dto;

public enum DomainCommand {
    RESERVATION,
    RESERVATION_TIME,
    END_PROGRAM,
    ;

    public boolean isEndProgram() {
        return this == END_PROGRAM;
    }
}
