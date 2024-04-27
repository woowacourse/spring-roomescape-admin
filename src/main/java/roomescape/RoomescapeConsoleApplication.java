package roomescape;

import roomescape.console.repository.MemoryReservationRepository;
import roomescape.console.repository.MemoryReservationTimeRepository;
import roomescape.console.view.ConsoleInputView;
import roomescape.console.view.ConsoleOutputView;
import roomescape.console.view.dto.DomainCommand;
import roomescape.console.view.dto.FunctionCommand;
import roomescape.domain.ReservationRepository;
import roomescape.domain.ReservationService;
import roomescape.domain.ReservationTimeRepository;
import roomescape.domain.ReservationTimeService;
import roomescape.domain.dto.ReservationResponse;
import roomescape.domain.dto.ReservationTimeResponse;

public class RoomescapeConsoleApplication {

    private final ReservationService reservationService;
    private final ReservationTimeService reservationTimeService;
    private final ConsoleInputView inputView;
    private final ConsoleOutputView outputView;

    public RoomescapeConsoleApplication() {
        ReservationTimeRepository reservationTimeRepository = new MemoryReservationTimeRepository();
        ReservationRepository reservationRepository = new MemoryReservationRepository(reservationTimeRepository);
        this.reservationService = new ReservationService(reservationRepository);
        this.reservationTimeService = new ReservationTimeService(reservationTimeRepository);
        this.inputView = new ConsoleInputView();
        this.outputView = new ConsoleOutputView();
    }

    public static void main(String[] args) {
        new RoomescapeConsoleApplication().run();
    }

    public void run() {
        DomainCommand domainCommand;
        do {
            domainCommand = inputView.chooseDomain();
            chooseDomain(domainCommand);
        } while (domainCommand == null || domainCommand.isEndProgram());
    }

    private void chooseDomain(DomainCommand domainCommand) {
        if (domainCommand == DomainCommand.RESERVATION) {
            runReservationFunction();
        }
        if (domainCommand == DomainCommand.RESERVATION_TIME) {
            runReservationTimeFunction();
        }
    }

    public void runReservationFunction() {
        FunctionCommand functionCommand = inputView.chooseFunction();
        if (functionCommand == FunctionCommand.GET_ALL) {
            outputView.printReservations(reservationService.getAllReservations());
        }
        if (functionCommand == FunctionCommand.CREATE) {
            ReservationResponse reservation = reservationService.createReservation(inputView.inputReservation());
            outputView.printReservation(reservation);
        }
        if (functionCommand == FunctionCommand.DELETE) {
            reservationService.deleteReservation(inputView.inputDeletingId());
        }
    }

    public void runReservationTimeFunction() {
        FunctionCommand functionCommand = inputView.chooseFunction();
        if (functionCommand == FunctionCommand.GET_ALL) {
            outputView.printReservationTimes(reservationTimeService.getAllReservationTimes());
        }
        if (functionCommand == FunctionCommand.CREATE) {
            ReservationTimeResponse time = reservationTimeService.createReservationTime(
                    inputView.inputReservationTime());
            outputView.printReservationTime(time);
        }
        if (functionCommand == FunctionCommand.DELETE) {
            reservationTimeService.deleteReservationTime(inputView.inputDeletingId());
        }
    }

}
