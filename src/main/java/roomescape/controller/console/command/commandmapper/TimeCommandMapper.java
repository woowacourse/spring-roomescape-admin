package roomescape.controller.console.command.commandmapper;

import roomescape.controller.console.command.Command;
import roomescape.controller.console.command.End;
import roomescape.controller.console.command.timecommand.AddTime;
import roomescape.controller.console.command.timecommand.DeleteTime;
import roomescape.controller.console.command.timecommand.InquiryTime;
import roomescape.view.InputView;
import roomescape.view.OutputView;

import java.util.function.Supplier;
import java.util.stream.Stream;

public enum TimeCommandMapper {
    ADD_TIME(1, AddTime::new),
    DELETE_TIME(2, DeleteTime::new),
    INQUIRY_TIME(3, InquiryTime::new),
    END(4, End::new);

    private final int command;
    private final Supplier<? extends Command> commandSupplier;

    TimeCommandMapper(int command, Supplier<? extends Command> commandSupplier) {
        this.command = command;
        this.commandSupplier = commandSupplier;
    }

    public static Command mapTo(InputView inputView, OutputView outputView) {
        outputView.printTimeOptions();
        return mappingCommand(inputView.readOptions());
    }

    private static Command mappingCommand(int key) {
        return Stream.of(values())
                .filter(c -> c.command == key)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 옵션이 없습니다."))
                .commandSupplier
                .get();
    }
}
