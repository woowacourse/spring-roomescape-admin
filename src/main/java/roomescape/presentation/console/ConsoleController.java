package roomescape.presentation.console;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import roomescape.business.domain.Reservation;
import roomescape.business.domain.ReservationTime;
import roomescape.business.service.ReservationService;
import roomescape.business.service.ReservationTimeService;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.request.ReservationTimeCreateRequest;
import roomescape.infra.ReservationDatabase;
import roomescape.infra.ReservationTimeDatabase;
import roomescape.presentation.console.view.InputView;
import roomescape.presentation.console.view.OutputView;

import java.util.List;

@Component
@ConditionalOnProperty(name = "room-escape.console-view.enabled", havingValue = "true")
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
