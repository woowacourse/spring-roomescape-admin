package roomescape;

import roomescape.controller.ConsoleMainController;
import roomescape.repository.MemoryReservationRepository;
import roomescape.repository.MemoryReservationTimeRepository;
import roomescape.console.view.InputView;
import roomescape.console.view.OutputView;
import roomescape.service.RoomescapeService;

public class ConsoleRoomescapeApplication {
    public static void main(String[] args) {
        ConsoleMainController controller = new ConsoleMainController(new InputView(), new OutputView(), new RoomescapeService(new MemoryReservationRepository(), new MemoryReservationTimeRepository()));

        controller.run();
    }
}
