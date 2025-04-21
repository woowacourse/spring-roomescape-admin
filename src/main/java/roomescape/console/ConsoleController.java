package roomescape.console;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import roomescape.console.view.InputView;
import roomescape.console.view.OutputView;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.request.ReservationTimeCreateRequest;
import roomescape.infra.ReservationDatabase;
import roomescape.infra.ReservationTimeDatabase;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;

import java.util.List;

@Component
public class ConsoleController {

    private final InputView inputView;
    private final OutputView outputView;
    private final ReservationService reservationService;
    private final ReservationTimeService reservationTimeService;

    public ConsoleController(
            final InputView inputView,
            final OutputView outputView,
            @Qualifier("reservationMemoryDatabase") final ReservationDatabase reservationDatabase,
            @Qualifier("reservationTimeMemoryDatabase") final ReservationTimeDatabase reservationTimeDatabase
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.reservationService = new ReservationService(reservationDatabase);
        this.reservationTimeService = new ReservationTimeService(reservationTimeDatabase);
    }

    public void run() {
        while (true) {
            try {
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
            } catch (Exception e) {
                System.out.println("실행 중 오류가 발생하였습니다.");
            }
        }
    }
}
