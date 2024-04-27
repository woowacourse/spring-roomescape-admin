package roomescape.controller.console.command.commandmapper;

import roomescape.controller.console.command.Command;
import roomescape.controller.console.command.End;
import roomescape.controller.console.command.reservationcommand.AddReservation;
import roomescape.controller.console.command.reservationcommand.DeleteReservation;
import roomescape.controller.console.command.reservationcommand.InquiryReservation;
import roomescape.view.InputView;
import roomescape.view.OutputView;

import java.util.function.Supplier;
import java.util.stream.Stream;

public enum ReservationCommandMapper {
    ADD_RESERVATION(1, AddReservation::new),
    DELETE_RESERVATION(2, DeleteReservation::new),
    INQUIRY_RESERVATION(3, InquiryReservation::new),
    END(4, End::new);

    private final int command;
    private final Supplier<? extends Command> commandSupplier;

    ReservationCommandMapper(int command, Supplier<? extends Command> commandSupplier) {
        this.command = command;
        this.commandSupplier = commandSupplier;
    }

    public static Command mapTo(InputView inputView, OutputView outputView) {
        outputView.printReservationOptions();
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
