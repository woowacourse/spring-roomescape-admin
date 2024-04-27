package roomescape.console.controller;

import org.springframework.stereotype.Component;
import roomescape.console.controller.request.ReservationRequest;
import roomescape.console.controller.response.ReservationResponse;
import roomescape.console.view.ConsoleRequest;
import roomescape.console.view.ConsoleResponse;
import roomescape.console.view.ResponseMapper;

import java.util.List;

@Component
public class ReservationControllerAdapter implements ControllerAdapter {
    private final ReservationController reservationController;
    private final ResponseMapper responseMapper;

    public ReservationControllerAdapter(ReservationController reservationController, ResponseMapper responseMapper) {
        this.reservationController = reservationController;
        this.responseMapper = responseMapper;
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
        ReservationRequest mappedRequest = ReservationRequest.from(request.getContents());
        ReservationResponse response = reservationController.save(mappedRequest);
        return responseMapper.mapToReservationConsoleResponse(response);
    }

    private ConsoleResponse handleGetRequest(ConsoleRequest request) {
        List<ReservationResponse> reservationTimeResponses = reservationController.findAll();
        return responseMapper.mapToReservationConsoleResponse(reservationTimeResponses);
    }

    private ConsoleResponse handleDeleteRequest(ConsoleRequest request) {
        Long id = Long.parseLong(request.getContents().get(0));
        reservationController.delete(id);
        return responseMapper.empty();
    }
}
