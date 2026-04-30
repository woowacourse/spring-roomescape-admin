package roomescape.global;


import org.springframework.http.HttpStatusCode;

public record CustomErrorResponse(
        HttpStatusCode status,
        String message
) {
}
