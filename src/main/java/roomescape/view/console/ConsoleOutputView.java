package roomescape.view.console;

import roomescape.dto.ReservationResponseDto;
import roomescape.dto.ReservationTimeResponseDto;
import java.util.Collection;

public class ConsoleOutputView {

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printErrorMessage(String message) {
        printMessage("ERROR: " + message);
    }

    public void printResult(ReservationResponseDto reservationResponseDto) {
        System.out.println(reservationResponseDto);
    }

    public void printResult(ReservationTimeResponseDto reservationTimeResponseDto) {
        System.out.println(reservationTimeResponseDto);
    }

    public void printCollection(Collection<?> collection) {
        System.out.println(collection);
    }
}
