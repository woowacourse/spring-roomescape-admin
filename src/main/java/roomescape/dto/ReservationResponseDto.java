package roomescape.dto;

public class ReservationResponseDto {

    private final Long id;
    private final String name;

    public ReservationResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
