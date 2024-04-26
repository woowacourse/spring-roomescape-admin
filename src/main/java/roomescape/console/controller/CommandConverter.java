package roomescape.console.controller;

import java.util.Arrays;
import java.util.List;

public class CommandConverter {

    public static List<String> convertPostReservation(final String read) {
        return Arrays.stream(read.split("/")).map(String::trim).toList();
    }
}
