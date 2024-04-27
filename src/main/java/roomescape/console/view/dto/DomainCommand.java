package roomescape.console.view.dto;

public enum DomainCommand {
    RESERVATION,
    RESERVATION_TIME,
    END_PROGRAM,
    ;

    public boolean isEndProgram() {
        return this == END_PROGRAM;
    }

    public boolean isReservationDomain() {
        return this == RESERVATION;
    }

    public boolean isTimeDomain() {
        return this == RESERVATION_TIME;
    }

}
