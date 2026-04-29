package roomescape.service.command;

public record ReservationCreateCommand(
        String name,
        String date,
        long timeId
) {
}
