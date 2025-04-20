package roomescape.test.utility;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HttpResponseTestUtility {

    public static <T> void checkStatusCode(ResponseEntity<T> response, HttpStatus expected) {
        assertThat(response.getStatusCode())
                .isEqualTo(expected);
    }

    public static <T> void checkLocationHeader(ResponseEntity<T> response, String expected) {
        assertThat(response.getHeaders().getLocation().getPath())
                .isEqualTo(expected);
    }
}
