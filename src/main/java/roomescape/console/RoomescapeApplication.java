package roomescape.console;

import roomescape.console.controller.MainController;
import roomescape.console.repository.MemoryReservationRepository;
import roomescape.console.repository.MemoryReservationTimeRepository;
import roomescape.console.view.InputView;
import roomescape.console.view.OutputView;
import roomescape.service.RoomescapeService;

public class RoomescapeApplication {
    public static void main(String[] args) {
        MainController controller = new MainController(new InputView(), new OutputView(), new RoomescapeService(new MemoryReservationRepository(), new MemoryReservationTimeRepository()));

        controller.run();
    }
}
