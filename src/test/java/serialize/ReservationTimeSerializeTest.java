package serialize;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTimeSerializeTest {

    @DisplayName("빌드옵션 관계없이 기본생성자가 있는 객체는 역직렬화 할 수 있다.")
    @Test
    void basic() {
        JsonPath jsonPath = new JsonPath("""
        {
            "id": "1",
            "startAt": "10:10"
        }""");

        TestBasic testBasic = jsonPath.getObject(".", TestBasic.class);

        assertThat(testBasic.getId()).isEqualTo(1);
        assertThat(testBasic.getStartAt()).isEqualTo("10:10");
    }

    @DisplayName("Intellij로 빌드할 경우 -parameters 옵션 추가하면 기본생성자가 없는 객체는 역직렬화 할 수 있다.")
    @Test
    void nonBasic() {
        JsonPath jsonPath = new JsonPath("""
        {
            "id": "1",
            "startAt": "10:10"
        }""");

        TestNoneBasic testBasic = jsonPath.getObject(".", TestNoneBasic.class);

        assertThat(testBasic.getId()).isEqualTo(1);
        assertThat(testBasic.getStartAt()).isEqualTo("10:10");
    }

    static class TestBasic {
        private Long id;
        private String startAt;

        protected TestBasic() {
        }

        public TestBasic(Long id, String startAt) {
            this.id = id;
            this.startAt = startAt;
        }

        public Long getId() {
            return id;
        }

        public String getStartAt() {
            return startAt;
        }
    }

    static class TestNoneBasic {
        private Long id;
        private String startAt;

        public TestNoneBasic(Long id, String startAt) {
            this.id = id;
            this.startAt = startAt;
        }

        public Long getId() {
            return id;
        }

        public String getStartAt() {
            return startAt;
        }
    }
}
