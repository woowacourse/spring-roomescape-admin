package roomescape.console.view;

import roomescape.console.controller.response.ReservationTimeResponse;

import java.util.List;
import java.util.stream.Collectors;

public class ConsoleResponse {
    private final String content;

    private ConsoleResponse(final String content) {
        this.content = content;
    }

    public static ConsoleResponse empty() {
        return new ConsoleResponse("");
    }

    public static ConsoleResponse from(List<ReservationTimeResponse> reservationTimeResponses) {
        String content = reservationTimeResponses.stream()
                .map(ConsoleResponse::from)
                .map(reservationTimeResponse -> reservationTimeResponse.content)
                .collect(Collectors.joining("\n"));
        return new ConsoleResponse(content);
    }

    public static ConsoleResponse from(ReservationTimeResponse reservationTimeResponse) {
        String idInfo = "id - " + reservationTimeResponse.id();
        String startAtInfo = "startAt - " + reservationTimeResponse.startAt();
        return new ConsoleResponse(String.join(", ", idInfo, startAtInfo));
    }

    public String getContent() {
        return content;
    }
}
