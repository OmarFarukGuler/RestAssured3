package CampusMersys;

import CampusMersys.CampusModel.Cauntry;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.*;

import java.util.*;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class countryTest {

    Cookies cookies;

    @BeforeClass

    public void loginCampus() {

        baseURI = "https://demo.mersys.io/"; //school-service/api
        //{"username": "richfield.edu","password": "Richfield2020!","rememberMe": "true"}
        Map<String, String> credential = new HashMap<>();
        credential.put("username", "richfield.edu");
        credential.put("password", "Richfield2020!");
        credential.put("rememberMe", "true");

        cookies = given()
                .contentType(ContentType.JSON)
                .body(credential)

                .when()
                .post("auth/login")

                .then()
                //.log().all()
                .statusCode(200)
                .extract().response().getDetailedCookies()

        ;
        // System.out.println("cookies = " + cookies);

    }
  /*  @Test
    public void searchCountry(){
     //   countries/search
       given()

               .when()
               .post("countries/search")

               .then()
               .statusCode(200)
               .log().body()
               ;



    }*/

    String countryID;
    String countryName = Cauntry.generateCountryName();
    String countryCode = Cauntry.generateCountryCode();

    @Test
    public void createCountry() {

        Cauntry cauntry = new Cauntry();
        cauntry.setName(countryName);           //generateCountryName
        cauntry.setCode(countryCode);              //generateCountryCode


        countryID = given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(cauntry)


                .when()
                .post("/school-service/api/countries")

                .then()
                .statusCode(201)
                .log().body()
                .extract().jsonPath().getString("id")

        ;


    }

    @Test(dependsOnMethods = "createCountry")
    public void createCountryNegativeTest() {//ayni country i yeniden kaydetme testi

        Cauntry cauntry = new Cauntry();
        cauntry.setName(countryName);           //generateCountryName
        cauntry.setCode(countryCode);              //generateCountryCode


        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(cauntry)



                .when()
                .post("/school-service/api/countries")

                .then()
                .log().body()
                .statusCode(400)
                //.body("message",equalTo("The Country with Name\""+countryName+"\"already exists."))


        ;


    }
    String newCountryName=countryName+countryName;
    String newCountryCode=countryCode+countryCode;
    @Test(dependsOnMethods = "createCountry")
    public void updateCountry() {

        Cauntry cauntry = new Cauntry();
        cauntry.setId(countryID);
        cauntry.setName(newCountryName);           //generateCountryName
        cauntry.setCode(newCountryCode);              //generateCountryCode


      given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(cauntry)


                .when()
                .put("/school-service/api/countries")

                .then()
                .statusCode(200)
                .log().body()
              .body("name",equalTo(newCountryName))

        ;


    }
    @Test(dependsOnMethods = "updateCountry")
    public void deleteCountry() {


        given()
                .cookies(cookies)
                .pathParam("countryID",countryID)


                .when()
                .delete("/school-service/api/countries/{countryID}")

                .then()
                .log().body()
                .statusCode(200)

        ;


    }
    @Test(dependsOnMethods = "deleteCountry")
    public void deleteCountryNegative() {//silineni silmeye calis


        given()
                .cookies(cookies)
                .pathParam("countryID",countryID)


                .when()
                .delete("/school-service/api/countries/{countryID}")

                .then()
                .log().body()
                .statusCode(400)
                .body("message",equalTo("Country not found"))

        ;


    }
    @Test(dependsOnMethods = "deleteCountryNegative")
    public void updateCountryNegativeTest() {//ayni country i yeniden kaydetme testi

        Cauntry cauntry = new Cauntry();
        cauntry.setId(countryID);
        cauntry.setName(newCountryName);           //generateCountryName
        cauntry.setCode(newCountryCode);              //generateCountryCode


        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(cauntry)



                .when()
                .put("/school-service/api/countries")

                .then()
                .log().body()
                .statusCode(400)
                .body("message",equalTo("Country not found"))



        ;


    }
}
