package roomescape.console;

import org.springframework.stereotype.Component;
import roomescape.console.view.InputView;
import roomescape.console.view.OutputView;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.request.ReservationTimeCreateRequest;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;

import java.util.List;

@Component
public class ConsoleController {

    private final InputView inputView;
    private final OutputView outputView;
    private final ReservationService reservationService;
    private final ReservationTimeService reservationTimeService;

    public ConsoleController(final InputView inputView, final OutputView outputView, final ReservationService reservationService, final ReservationTimeService reservationTimeService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.reservationService = reservationService;
        this.reservationTimeService = reservationTimeService;
    }

    public void run() {
        while (true) {
            switch (inputView.getCommandType()) {
                case 예약_전체_조회 -> {
                    final List<Reservation> result = reservationService.getAll();
                    outputView.printAllReservations(result);
                }
                case 예약_추가 -> {
                    final ReservationCreateRequest request = inputView.getReservationCreateRequest();
                    final Reservation reservation = reservationService.saveAndGet(request);
                    outputView.printSavedReservation(reservation);
                }
                case 예약_삭제 -> {
                    final long id = inputView.getDeleteReservationId();
                    reservationService.deleteById(id);
                    outputView.printDeleteCompleteMessage();
                }
                case 예약_시간_전체_조회 -> {
                    final List<ReservationTime> result = reservationTimeService.getAll();
                    outputView.printAllReservationTimes(result);
                }
                case 예약_시간_추가 -> {
                    final ReservationTimeCreateRequest request = inputView.getReservationTimeCreateRequest();
                    final ReservationTime result = reservationTimeService.saveAndGet(request);
                    outputView.printSavedReservationTime(result);
                }
                case 예약_시간_삭제 -> {
                    final long id = inputView.getDeleteReservationTimeId();
                    reservationTimeService.deleteById(id);
                    outputView.printDeleteCompleteMessage();
                }
                case 종료 -> {
                    return;
                }
            }
        }
    }
}
