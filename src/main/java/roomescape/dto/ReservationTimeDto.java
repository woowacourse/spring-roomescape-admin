package roomescape.dto;

public record ReservationTimeDto(Long id, String startAt) {

    public roomescape.domain.ReservationTimeDto toDomain() {
        return new roomescape.domain.ReservationTimeDto(id, startAt);
    }
}
