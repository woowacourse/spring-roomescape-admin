package roomescape.controller.console;

import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import roomescape.controller.dto.TimeSlotCreationRequest;
import roomescape.controller.dto.TimeSlotCreationResponse;
import roomescape.service.TimeSlotService;
import roomescape.view.InputView;
import roomescape.view.OutputView;

@Controller
@Profile("console")
public class TimeSlotConsoleController extends ManagementController {

    private final TimeSlotService timeSlotService;

    public TimeSlotConsoleController(TimeSlotService timeSlotService) {
        super();
        this.timeSlotService = timeSlotService;
    }

    @Override
    protected void showAllResults() {
        List<TimeSlotCreationResponse> responses = timeSlotService.getAllTimes();
        OutputView.printTimeSlots(responses);
        OutputView.printTimeSlotManagementMenu();
    }

    @Override
    public void create() {
        TimeSlotCreationRequest request = InputView.createTimeSlotRequest();
        TimeSlotCreationResponse response = timeSlotService.addTime(request);
        OutputView.printTimeSlotCreationResponse(response);
    }

    @Override
    public void delete() {
        long timeSlotId = InputView.inputDeleteTimeSlotId();
        timeSlotService.removeTime(timeSlotId);
        OutputView.printDeleteTimeSlotMessage();
    }
}
