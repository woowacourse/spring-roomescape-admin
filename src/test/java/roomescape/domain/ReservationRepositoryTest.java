package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import roomescape.dto.ReservationCreateDto;


/*
 * 테스트 데이터베이스 초기 데이터
 * {ID=1, NAME=브라운, DATE=2024-05-04, TIME=16:00}
 * {ID=2, NAME=엘라, DATE=2024-05-04, TIME=17:00}
 * {ID=3, NAME=릴리, DATE=2023-08-05, TIME=15:40}
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/ResetTestData.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class ReservationRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationRepository reservationRepository;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("H2 데이터베이스에 연결한다.")
    void connectH2Database() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("DATABASE");
            assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("예약 조회 시 데이터베이스에서 데이터를 가져온다.")
    void findAllReservations() {
        List<Reservation> reservations = RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", Reservation.class);

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

        assertThat(reservations.size()).isEqualTo(count);
    }

    @Test
    @DisplayName("특정 예약 id의 데이터를 조회한다.")
    void findReservationById() {
        Reservation findReservations = reservationRepository.findById(2);

        assertThat(findReservations.getName()).isEqualTo("엘라");
    }

    @Test
    void createReservation() {
        ReservationCreateDto createDto = new ReservationCreateDto("브라운", "2023-08-05", "10:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(createDto)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .header("Location", "/reservations/4");

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(count).isEqualTo(4);
    }

    @Test
    @DisplayName("데이터베이스에서 예약을 삭제한다")
    void deleteReservation() {
        RestAssured.given().log().all()
                .when().delete("/reservations/3")
                .then().log().all()
                .statusCode(204);

        Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(countAfterDelete).isEqualTo(2);
    }
}
