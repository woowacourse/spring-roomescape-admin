package roomescape.controller.console.command.commandmapper;

import roomescape.controller.console.command.Command;
import roomescape.controller.console.command.End;
import roomescape.view.InputView;
import roomescape.view.OutputView;

import java.util.stream.Stream;

public enum RoomEscapeCommandMapper {
    TIME_MANAGEMENT(1, TimeCommandMapper::mapTo),
    RESERVATION_MANAGEMENT(2, ReservationCommandMapper::mapTo),
    END(3, (InputView inputView, OutputView outputview) -> new End());

    private final int command;
    private final CommandMapper commandMapper;

    RoomEscapeCommandMapper(int command, CommandMapper commandMapper) {
        this.command = command;
        this.commandMapper = commandMapper;
    }

    public static Command mapTo(InputView inputView, OutputView outputView) {
        outputView.printAllOptions();
        return mappingCommand(inputView, outputView, inputView.readOptions());
    }

    private static Command mappingCommand(InputView inputView, OutputView outputView, int target) {
        return Stream.of(values())
                .filter(c -> c.command == target)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 옵션이 없습니다."))
                .commandMapper
                .mapTo(inputView, outputView);
    }
}
