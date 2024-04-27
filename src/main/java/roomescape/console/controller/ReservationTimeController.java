package roomescape.console.controller;

import org.springframework.stereotype.Controller;
import roomescape.console.view.ConsoleRequest;
import roomescape.console.controller.request.ReservationTimeRequest;
import roomescape.console.view.ConsoleResponse;
import roomescape.console.controller.response.ReservationTimeResponse;
import roomescape.core.domain.ReservationTime;
import roomescape.core.repository.ReservationTimeRepository;

import java.util.List;

@Controller
public class ReservationTimeController implements ConsoleController {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTimeResponse> findAll() {
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();
        List<ReservationTimeResponse> reservationTimeResponses = reservationTimes.stream()
                .map(ReservationTimeResponse::from)
                .toList();

        return reservationTimeResponses;
    }

    public ReservationTimeResponse save(ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = reservationTimeRequest.toEntity();

        ReservationTime savedReservationTime = reservationTimeRepository.save(reservationTime);

        return ReservationTimeResponse.from(savedReservationTime);
    }

    public void delete(Long id) {
        reservationTimeRepository.deleteById(id);
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
        ReservationTimeResponse response = save(mappedRequest);
        return ConsoleResponse.from(response);
    }

    private ConsoleResponse handleGetRequest(ConsoleRequest request) {
        List<ReservationTimeResponse> reservationTimeResponses = findAll();
        return ConsoleResponse.from(reservationTimeResponses);
    }

    private ConsoleResponse handleDeleteRequest(ConsoleRequest request) {
        Long id = Long.parseLong(request.getContents().get(0));
        delete(id);
        return ConsoleResponse.empty();
    }
}
