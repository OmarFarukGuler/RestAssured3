import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class _02_Tekrar {

    @Test
    void test1(){
        //Tüm places lerdedi state leri yazdir

        given()

                .when()
                .get("http://api.zippopotam.us/us/75221")

                .then()
                .log().body()
                .body("places.state",hasItem("Texas"))
                ;

    }
    @Test
    void test2(){

        given()

                .when()
                .get("http://api.zippopotam.us/us/75221")

                .then()
                .log().body()
                .body("places[0].'place name'",equalTo("Dallas"))
                .extract().cookies()

        ;

    }
    @Test
    void test3(){

        given()

                .when()
                .get("https://demo.mersys.io/")

                .then()
                .log().body()
                .statusCode(200);


        ;

    }
}
