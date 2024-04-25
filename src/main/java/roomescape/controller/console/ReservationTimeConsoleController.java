package roomescape.controller.console;

import java.util.List;
import org.springframework.stereotype.Controller;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.service.ReservationTimeService;
import roomescape.view.ReservationTimeView;

@Controller
public class ReservationTimeConsoleController {
    private final ReservationTimeView reservationTimeView;
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeConsoleController(final ReservationTimeService reservationTimeService) {
        reservationTimeView = new ReservationTimeView();
        this.reservationTimeService = reservationTimeService;
    }

    public void saveTime() {
        ReservationTimeRequest reservationTimeRequest = new ReservationTimeRequest(
                reservationTimeView.readStartAt()
        );
        reservationTimeService.saveTime(reservationTimeRequest);
        reservationTimeView.printSuccessfullyAdded();
    }

    public void deleteTime() {
        List<ReservationTimeResponse> reservationTimeResponses = getReservationTimeResponses();
        if (reservationTimeResponses.isEmpty()) {
            reservationTimeView.printHasNotAnyReservationTimeToDelete();
            return;
        }

        int index = reservationTimeView.readIndexToDelete(reservationTimeResponses);
        if (index < 0 || index > reservationTimeResponses.size()) {
            throw new IllegalArgumentException("[ERROR] 올바른 예약 가능 시간을 입력해주세요.");
        }

        reservationTimeService.deleteTime(reservationTimeResponses.get(index - 1).id());
        reservationTimeView.printSuccessfullyDeleted();
    }

    public void getTimes() {
        reservationTimeView.printReservationTimes(getReservationTimeResponses());
    }

    private List<ReservationTimeResponse> getReservationTimeResponses() {
        return reservationTimeService.getTimes()
                .stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }
}
