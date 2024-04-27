package roomescape.console.controller;

import org.springframework.stereotype.Component;
import roomescape.console.controller.request.ReservationTimeRequest;
import roomescape.console.controller.response.ReservationTimeResponse;
import roomescape.console.view.ConsoleRequest;
import roomescape.console.view.ConsoleResponse;

import java.util.List;

@Component
public class ReservationTimeControllerAdapter implements ControllerAdapter {
    private final ReservationTimeController reservationTimeController;

    public ReservationTimeControllerAdapter(final ReservationTimeController reservationTimeController) {
        this.reservationTimeController = reservationTimeController;
    }

    @Override
    public ConsoleResponse dispatch(ConsoleRequest request) {
        return switch (request.getMethod()) {
            case "post" -> handlePostRequest(request);
            case "get" -> handleGetRequest(request);
            case "delete" -> handleDeleteRequest(request);
            default -> throw new IllegalArgumentException("잘못된 명령어입니다. 입력한 명령어 '" + request.getMethod() + "'");
        };
    }

    private ConsoleResponse handlePostRequest(ConsoleRequest request) {
        ReservationTimeRequest mappedRequest = ReservationTimeRequest.from(request.getContents());
        ReservationTimeResponse response = reservationTimeController.save(mappedRequest);
        return ConsoleResponse.from(response);
    }

    private ConsoleResponse handleGetRequest(ConsoleRequest request) {
        List<ReservationTimeResponse> reservationTimeResponses = reservationTimeController.findAll();
        return ConsoleResponse.from(reservationTimeResponses);
    }

    private ConsoleResponse handleDeleteRequest(ConsoleRequest request) {
        Long id = Long.parseLong(request.getContents().get(0));
        reservationTimeController.delete(id);
        return ConsoleResponse.empty();
    }
}
