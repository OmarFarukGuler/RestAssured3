/*Genel kullanılanlar

equalTo(X) - used to check whether an actual element value is equal to a pre-specified expected element value
hasItem("value") - used to see whether a collection of elements contains a specific pre-specified item value
hasSize(3) - used to verify the actual number of elements in a collection
not(equalTo(X)) - inverts any given matcher that exists in the Hamcrest

Number related assertions

equalTo – It checks whether the retrieved number from response is equal to the expected number.
greaterThan – checks extracted number is greater than the expected number.
greaterThanOrEqualTo – checks whether the extracted number is greater than equal to the expected number.
lessThan – It checks whether the retrieved number from response is lesser than to the expected number.
lessThanOrEqualTo – It checks whether the retrieved number from response is lesser than or equal to the expected number.

String related Assertions

equalTo – It checks whether the extracted string from JSON is equal to the expected string.
equalToIgnoringCase – It checks whether the extracted string from JSON is equal to the expected string without considering the case (small or capital).
equalToIgnoringWhiteSpace – It checks whether the extracted string from JSON is equal to the expected string by considering the white spaces.
containsString – It checks whether the extracted string from JSON contains the expected string as a substring.
startsWith – It checks whether the extracted string from JSON is starting with a given string or character.
endsWith – It checks whether the extracted string from JSON is ending with a given string or character.*/

import POJO.Location;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class _01_ZippoTest {

    @Test
    public void test() {

        given()
                //hazirlik islemleri yapacaz(token,send body,parametreler)
                .when()

                //link ve method veriyoruz
                .then();//postman deki test bolumune denk geliyor
        //assertion ve verileri ele alma


    }

    @Test
    public void statusCodeTest() {

        given()

                .when()

                .get("http://api.zippopotam.us/us/90210")


                .then()
                .log().all()                      //sonucu log da yazdirmak icin
                .statusCode(200)                 //assert

        ;


    }

    @Test
    public void ContetntTypeTest() {

        given()

                .when()

                .get("http://api.zippopotam.us/us/90210")


                .then()
                .log().body()                     //sonucu log da yazdirmak icin
                .statusCode(200)
                .contentType(ContentType.JSON)//assert

        ;


    }

    @Test
    public void CheckStateInResponseBody() {

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")


                .then()
                .log().body()                     //sonucu log da yazdirmak icin
                .statusCode(200)
                .contentType(ContentType.JSON)//assert
                .body("country", equalTo("United States"))

        ;


    }

    @Test
    public void bodyJsonPathTest2() {

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")


                .then()
                .log().body()
                .body("places[0].state", equalTo("California"))
                .statusCode(200)

        ;


    }

    @Test
    public void bodyJsonPathTest3() {

        given()

                .when()
                .get("http://api.zippopotam.us/tr/01000")


                .then()
                .log().body()
                .body("places.'place name'", hasItem("Çaputçu Köyü"))//tum places lerde sorgular
                .statusCode(200)

        ;


    }

    @Test
    public void bodyJsonPathTest4() {

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")


                .then()
                .log().body()
                .body("places", hasSize(1))
                .statusCode(200)

        ;


    }

    @Test
    public void bodyJsonPathTestCokluSorgu() {

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")


                .then()
                .log().body()
                .body("places", hasSize(1))
                .body("places.state", hasItem("California"))
                .body("places[0].'place name'", equalTo("Beverly Hills"))
                .statusCode(200)

        ;


    }

    @Test
    public void PathParamTest() {

        given()
                .pathParam("Country", "us")    //path parametrelerini bu sekilde de verebiliyoruz
                .pathParam("ZipKod", "90210")
                .log().uri()

                .when()
                .get("http://api.zippopotam.us/{Country}/{ZipKod}")


                .then()
                .log().body()
                .statusCode(200)

        ;


    }

    @Test
    public void PathParamTestSoru() {
        //90210 dan 90213 ye kadar test sonuclarinda places in size inin hepsinde 1 geldigini test ediniz

        for (int i = 90210; i <= 90250; i++) {
            given()
                    .pathParam("Country", "us")
                    .pathParam("ZipKod", i)
                    .log().uri()

                    .when()
                    .get("http://api.zippopotam.us/{Country}/{ZipKod}")


                    .then()
                    .log().body()
                    .body("places", hasSize(1))
                    .statusCode(200)

            ;
        }

    }

    @Test
    public void QueryParamTest() {


        given()
                .param("page", 1)
                .log().uri()

                .when()
                .get("http://gorest.co.in/public/v1/users")


                .then()
                .log().body()
                .body("meta.pagination.page", equalTo(1))
                .statusCode(200)

        ;

    }


    @Test
    public void QueryParamTestSoru() {


        for (int pageNo = 1; pageNo <= 10; pageNo++) {
            given()
                    .param("page", pageNo)
                    .log().uri()//request linki verir

                    .when()
                    .get("http://gorest.co.in/public/v1/users")


                    .then()
                    .log().body()
                    .body("meta.pagination.page", equalTo(pageNo))
                    .statusCode(200)

            ;


        }
    }


    RequestSpecification requestSpecs;
    ResponseSpecification responseSpecs;

    @BeforeClass
    void Setup() {

        baseURI = "http://gorest.co.in/public/v1";

        requestSpecs = new RequestSpecBuilder()
                .log(LogDetail.URI)
                .setAccept(ContentType.JSON)
                .build();
        responseSpecs = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.BODY)
                .build();
    }

    @Test
    public void requestResponseSpecification() {


        given()
                .param("page", 1)
                .spec(requestSpecs)

                .when()
                .get("/users")


                .then()
                .log().body()
                .body("meta.pagination.page", equalTo(1))
                .spec(responseSpecs)

        ;


    }

    @Test
    public void extractingJsonPath() {
        String placeName = given()

                .when()
                .get("http://api.zippopotam.us/us/90210")


                .then()
                .log().body()                     //sonucu log da yazdirmak icin
                .statusCode(200)
                .extract().path("places[0].'place name'");
        System.out.println("placeName = " + placeName);

    }

    @Test
    public void extractingJsonPathInt() {
        int limit =
                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        .statusCode(200)
                        .extract().path("meta.pagination.limit");

        System.out.println("limit = " + limit);
        Assert.assertEquals(limit, 10, "test sonucu");

    }

    @Test
    public void extractingJsonPathInt2() {
        int id =
                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        .statusCode(200)
                        .extract().path("data[2].id");

        System.out.println("limit = " + id);

    }

    @Test
    public void extractingJsonPathIntList() {
        List<Integer> idler =
                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        .statusCode(200)
                        .extract().path("data.id")//karsiligi list<Integer>
                ;

        System.out.println("limit = " + idler);
        Assert.assertTrue(idler.contains(3045));
    }

    @Test
    public void extractingJsonPathStringList() {
        List<String> isimler =
                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        .statusCode(200)
                        .extract().path("data.name")//karsiligi list<Integer>
                ;

        System.out.println("limit = " + isimler);
        Assert.assertTrue(isimler.contains("Datta Achari"));


    }

    @Test
    public void extractingJsonPathStringList2() {
        Response response =
                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        .statusCode(200)
                        .extract().response()//tum body i alip string integer her ne varsa alinabilir
                ;

        List<Integer> idler = response.path("data.id");
        List<Integer> isimler = response.path("data.name");
        int limit = response.path("meta.pagination.limit");

        System.out.println("limit = " + limit);
        System.out.println("isimler = " + isimler);
        System.out.println("idler = " + idler);


    }

    @Test
    public void extractingJsonPOJO() {
        Location yer = given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .extract().as(Location.class);

        System.out.println("yer = " + yer);

        System.out.println("yer.getcountry = " + yer.getCountry());
        System.out.println("yer.getPlaces().get(0).getPlacename() = "
                + yer.getPlaces().get(0).getPlacename());

    }

}

