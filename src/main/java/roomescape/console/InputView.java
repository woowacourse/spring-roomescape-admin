package roomescape.console;

import java.util.Scanner;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationTimeRequest;

public class InputView {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static Command inputCommand() {
        System.out.println("input command. (ex - reservations/get)");
        String input = SCANNER.nextLine();
        return Command.findBy(input);
    }

    public static ReservationRequest inputReservationRequest() {
        System.out.println(
                "input reservation request(name,date,timeId). (ex - harry,2024-05-11,1)");
        String input = SCANNER.nextLine();
        String[] split = input.split(",");
        if (split.length == 3) {
            return new ReservationRequest(split[0], split[1], Long.parseLong(split[2]));
        }
        throw new IllegalArgumentException();
    }

    public static long inputDeleteId() {
        System.out.println("input id to delete");
        String input = SCANNER.nextLine();
        return Long.parseLong(input);
    }

    public static ReservationTimeRequest inputReservationTimeRequest() {
        System.out.println("input reservation time request(startAt). (ex - 10:00)");
        String input = SCANNER.nextLine();
        return new ReservationTimeRequest(input);
    }
}
