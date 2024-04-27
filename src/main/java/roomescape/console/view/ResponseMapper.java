package roomescape.console.view;

import org.springframework.stereotype.Component;
import roomescape.console.controller.response.ReservationResponse;
import roomescape.console.controller.response.ReservationTimeResponse;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ResponseMapper {
    public ConsoleResponse empty() {
        return new ConsoleResponse("");
    }

    public ConsoleResponse mapToReservationTimeConsoleResponse(List<ReservationTimeResponse> responses) {
        String content = responses.stream()
                .map(this::mapToReservationTimeConsoleResponse)
                .map(ConsoleResponse::getContent)
                .collect(Collectors.joining("\n"));
        return new ConsoleResponse(content);
    }

    public ConsoleResponse mapToReservationTimeConsoleResponse(ReservationTimeResponse response) {
        String idInfo = "id - " + response.id();
        String startAtInfo = "startAt - " + response.startAt();
        return new ConsoleResponse(String.join(", ", idInfo, startAtInfo));
    }


    public ConsoleResponse mapToReservationConsoleResponse(List<ReservationResponse> responses) {
        String content = responses.stream()
                .map(this::mapToReservationConsoleResponse)
                .map(ConsoleResponse::getContent)
                .collect(Collectors.joining("\n"));
        return new ConsoleResponse(content);
    }

    public ConsoleResponse mapToReservationConsoleResponse(ReservationResponse response) {
        String idInfo = "id - " + response.id();
        String nameInfo = "name - " + response.name();
        String dateInfo = "date - " + response.date().toString();
        String startAtInfo = "startAt - " + response.time().startAt();
        return new ConsoleResponse(String.join(", ", idInfo, nameInfo, dateInfo, startAtInfo));
    }
}
