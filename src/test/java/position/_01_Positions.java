package position;

import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class _01_Positions {
  //  ObjectClass objectClass = new ObjectClass();

    Cookies cookies;

    String name ;
    String shortName ;
    String alphaNumeric ;

    String positionID;

    @BeforeClass
    void Login() {

        baseURI = "https://demo.mersys.io/";

        ObjectClass objectClass = new ObjectClass();
        objectClass.setUsername("richfield.edu");
        objectClass.setPassword("Richfield2020!");
        objectClass.setRememberMe("true");

        cookies =
                given()
                        .contentType(ContentType.JSON)
                        .body(objectClass)
                        // .log().body()

                        .when()
                        .post("auth/login")

                        .then()
                        .statusCode(200)
                        // .log().all()
                        .extract().response().getDetailedCookies()

        ;


    }

/*5fe0786230cc4d59295712cf*///tenant
    @Test
    public void createPosition() {

        name= ObjectClass.RandomString();
        shortName=ObjectClass.RandomString();

        ObjectClass objectClass1 = new ObjectClass();
        objectClass1.setName(name);
        objectClass1.setShortName(shortName);
//        objectClass1.setTenantId(alphaNumeric);

//        Map<String,String> object=new HashMap<>();
//        object.put("name","dcsdsd");
//        object.put("shortName","sdfsdvsd");

       // baseURI = "https://demo.mersys.io/";
        positionID =
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(objectClass1)
                        .log().body()

                        .when()
                        .post("school-service/api/employee-position")

                        .then()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        .log().body()
                        //.body("message",containsString("successfully created"))  //  Employee Position
                        .extract().jsonPath().getString("id")

        ;

        System.out.println("positionID = " + positionID);

    }

}

/*REST API Http Response Codes
200: OK. Everything worked as expected.
201: A resource was successfully created in response to a POST request. The Location header contains the URL pointing to the newly created resource.
204: The request was handled successfully and the response contains no body content (like a DELETE request).
304: The resource was not modified. You can use the cached version.
400: Bad request. This could be caused by various actions by the user, such as providing invalid JSON data in the request body etc.
401: Authentication failed.
403: The authenticated user is not allowed to access the specified API endpoint.
404: The requested resource does not exist.
405: Method not allowed. Please check the Allow header for the allowed HTTP methods.
415: Unsupported media type. The requested content type or version number is invalid.
422: Data validation failed (in response to a POST request, for example). Please check the response body for detailed error messages.
429: Too many requests. The request was rejected due to rate limiting.
500: Internal server error. This could be caused by internal program errors.*/