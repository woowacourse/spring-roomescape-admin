package roomescape.domain;

public enum ReservationStatus {
    RESERVED("예약"),
    WAITING("대기");

    private String viewName;

    ReservationStatus(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }
}
