package roomescape.test.utility;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HttpResponseTestUtility {

    public static <T> void checkStatusCode(ResponseEntity<T> response, HttpStatus expected) {
        assertThat(response)
                .extracting(ResponseEntity::getStatusCode)
                .isEqualTo(expected);
    }

    public static <T> void checkLocationHeader(ResponseEntity<T> response, String expected) {
        assertThat(response)
                .extracting(ResponseEntity::getHeaders)
                .extracting(HttpHeaders::getLocation)
                .extracting(URI::getPath)
                .isEqualTo(expected);
    }
}
