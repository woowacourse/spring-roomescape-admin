package roomescape.console.config;

import roomescape.console.dao.ReservationMemoryDao;
import roomescape.console.dao.ReservationTimeMemoryDao;
import roomescape.console.presentation.DispatcherConsole;
import roomescape.console.presentation.ReservationConsoleController;
import roomescape.console.presentation.ReservationTimeConsoleController;
import roomescape.console.view.InputView;
import roomescape.console.view.OutputView;
import roomescape.core.domain.ReservationRepository;
import roomescape.core.domain.ReservationTimeRepository;
import roomescape.core.service.ReservationService;
import roomescape.core.service.ReservationTimeService;

public class DependencyInjector {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;
    private final ReservationService reservationService;
    private final ReservationTimeService reservationTimeService;
    private final InputView inputView;
    private final OutputView outputView;
    private final ReservationConsoleController reservationController;
    private final ReservationTimeConsoleController reservationTimeController;
    private final DispatcherConsole dispatcherConsole;

    public DependencyInjector() {
        this.reservationRepository = new ReservationMemoryDao();
        this.reservationTimeRepository = new ReservationTimeMemoryDao();

        this.reservationService = new ReservationService(reservationRepository, reservationTimeRepository);
        this.reservationTimeService = new ReservationTimeService(reservationTimeRepository);

        this.inputView = new InputView();
        this.outputView = new OutputView();

        this.reservationController = new ReservationConsoleController(
                reservationService, outputView, inputView);
        this.reservationTimeController = new ReservationTimeConsoleController(
                reservationTimeService, outputView, inputView);

        this.dispatcherConsole = new DispatcherConsole(reservationController, reservationTimeController);
    }

    public DispatcherConsole getDispatcherConsole() {
        return dispatcherConsole;
    }

    public OutputView getOutputView() {
        return outputView;
    }

    public InputView getInputView() {
        return inputView;
    }
}
