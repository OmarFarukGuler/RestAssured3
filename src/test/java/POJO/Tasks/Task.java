package POJO.Tasks;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Task {
    /** Task 6
     * create a request to https://jsonplaceholder.typicode.com/todos/2
     * expect status 200
     * Converting Into POJO
     */

    @Test
    public void extractingJsonPOJO() {
       user us=  given()

                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")

                .then()
               .extract().as(user.class)

               ;

        System.out.println("us = " + us);
    }
}
 //.extract().as(Location.class)

//            tel uzerinden bilgisayar internet baglantisi
//         1- usb kablosuyla telefonu bilgisayar bağlıoruz
//         2- usb tethering i ON yapıyoruz
//         3- bilgisayar bağlantı uyarısı verdiğinde EVET giyoruz