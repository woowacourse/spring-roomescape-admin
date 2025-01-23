package roomescape.domain;

import java.util.Arrays;

public enum ReservationStatus {
    RESERVED("예약"),
    WAITING("대기");

    private String viewName;

    ReservationStatus(String viewName) {
        this.viewName = viewName;
    }

    public static ReservationStatus findByViewName(String viewName) {
        return Arrays.stream(values())
                .filter(rs -> rs.getViewName().equals(viewName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("viewName과 일치하는 예약 상태가 없습니다. viewName=" + viewName));
    }

    public String getViewName() {
        return viewName;
    }
}
