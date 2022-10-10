package GoRest;

import com.sun.xml.internal.bind.v2.TODO;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.databind.node.JsonNodeType.POJO;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class GoRestUsersTests {

    /////////////******************************************************/////////////////////////////////


    @BeforeClass
    public void before() {

        baseURI = "https://gorest.co.in/public/v2/";

    }

    @Test(enabled = false)
    public void createUser() {
        int userID =
                given()//appi methoduna gitmeden once yapilacaklar bolumu

                        .header("Authorization", "Bearer 6a844245e27f09f5577ce2b132f3158130f123e5c609c1a5c4c5a71443c33755")
                        .contentType(ContentType.JSON)
                        .body("{\"name\":\"" + getRandomName() + "\", \"gender\":\"male\", \"email\":\"" + getRandomEmail() + "\", \"status\":\"active\"}")

                        .when()
                        .post("users")


                        .then()
                        .log().body()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        .extract().path("id");

        System.out.println("id = " + userID);

    }

    public String getRandomEmail() {

        return RandomStringUtils.randomAlphabetic(8).toLowerCase() + "@gmail.com";

    }

    public String getRandomName() {

        return RandomStringUtils.randomAlphabetic(6);
    }

    @Test(enabled = false)
    public void createUserMap() {

        Map<String, String> newUser = new HashMap<>();
        newUser.put("name", getRandomName());
        newUser.put("gender", "male");
        newUser.put("email", getRandomEmail());
        newUser.put("status", "active");

        int userID =
                given()//appi methoduna gitmeden once yapilacaklar bolumu

                        .header("Authorization", "Bearer 6a844245e27f09f5577ce2b132f3158130f123e5c609c1a5c4c5a71443c33755")
                        .contentType(ContentType.JSON)
                        .body(newUser)//********************************Map ten otomatik aliyor

                        .when()
                        .post("users")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        .extract().path("id");

        System.out.println("id = " + userID);

    }

    int userID = 0;
    User newUser;

    @Test
    public void createUserObject() {

        newUser = new User();
        newUser.setName(getRandomName());
        newUser.setGender("male");
        newUser.setEmail(getRandomEmail());
        newUser.setStatus("active");


        userID =
                given()//appi methoduna gitmeden once yapilacaklar bolumu

                        .header("Authorization", "Bearer 6a844245e27f09f5577ce2b132f3158130f123e5c609c1a5c4c5a71443c33755")
                        .contentType(ContentType.JSON)
                        .body(newUser)

                        .when()
                        .post("users")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        //.extract().path("id")
                        .extract().jsonPath().getInt("id")//boyle de oluyor.int e cevirerek aldik
        // path : class veya tip dönüşümüne imkan veremeyen direk veriyi verir. List<String> gibi
        // jsonPath : class dönüşümüne ve tip dönüşümüne izin vererek , veriyi istediğimiz formatta verir.
        ;

        System.out.println("createUserObject / id = " + userID);

    }


    @Test(dependsOnMethods = "createUserObject", priority = 1)
    public void updateUser() {

        newUser.setName("omer");////////////////*///////////////////***************************///////////////////////////
        userID =
                given()//appi methoduna gitmeden once yapilacaklar bolumu

                        .header("Authorization", "Bearer 6a844245e27f09f5577ce2b132f3158130f123e5c609c1a5c4c5a71443c33755")
                        .contentType(ContentType.JSON)
                        .body(newUser)
                        .log().body()
                        .pathParam("userID", userID)
                        .log().uri()


                        .when()
                        .put("users/{userID}")

                        .then()
                        .log().body()
                        .statusCode(200)
                        .body("name", equalTo("omer"))
                        .extract().path("id")

        ;

        System.out.println("id = " + userID);

    }

    @Test(dependsOnMethods = "createUserObject", priority = 2)
    public void getUserByID() {


        given()//appi methoduna gitmeden once yapilacaklar bolumu

                .header("Authorization", "Bearer 6a844245e27f09f5577ce2b132f3158130f123e5c609c1a5c4c5a71443c33755")
                .contentType(ContentType.JSON)
                .log().body()
                .pathParam("userID", userID)

                .when()
                .get("users/{userID}")

                .then()
                .log().body()
                .statusCode(200)
                .body("id", equalTo(userID))
        ;


    }

    @Test(dependsOnMethods = "createUserObject", priority = 3)
    public void deleteUserByID() {


        given()//appi methoduna gitmeden once yapilacaklar bolumu

                .header("Authorization", "Bearer 6a844245e27f09f5577ce2b132f3158130f123e5c609c1a5c4c5a71443c33755")
                .contentType(ContentType.JSON)
                .log().body()
                .pathParam("userID", userID)

                .when()
                .delete("users/{userID}")

                .then()
                .log().body()
                .statusCode(204)

        ;


    }

    @Test(dependsOnMethods = "deleteUserByID")
    public void deleteUserByIDNegativeTest() {


        given()//appi methoduna gitmeden once yapilacaklar bolumu

                .header("Authorization", "Bearer 6a844245e27f09f5577ce2b132f3158130f123e5c609c1a5c4c5a71443c33755")
                .contentType(ContentType.JSON)
                .log().body()
                .pathParam("userID", userID)

                .when()
                .delete("users/{userID}")

                .then()
                .log().body()
                .statusCode(404)

        ;


    }

    @Test
    public void getUsers() {

        Response response =            //sonradan aklandi
                given()//appi methoduna gitmeden once yapilacaklar bolumu

                        .header("Authorization", "Bearer 6a844245e27f09f5577ce2b132f3158130f123e5c609c1a5c4c5a71443c33755")

                        .when()
                        .get("users")

                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().response()     //sonradan eklendi

                ;

        int idUser3Path = response.path("[2].id");
        int idUser3JsonPath = response.jsonPath().getInt("[2].id");
        System.out.println("idUser3Path = " + idUser3Path);
        System.out.println("idUser3JsonPath = " + idUser3JsonPath);


    }

    //// TODO lar//////////////////////////////////////////////////////////////////////////////////////
    @Test //TODO 1
    public void get3idWithPath() {


        List<Integer> id =
                given()//appi methoduna gitmeden once yapilacaklar bolumu

                        .header("Authorization", "Bearer 6a844245e27f09f5577ce2b132f3158130f123e5c609c1a5c4c5a71443c33755")

                        .when()
                        .get("users")

                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().path("id");
        System.out.println("id = " + id);
        for (int i = 0; i < 3; i++) {
            System.out.println("id " + (i + 1) + " = " + id.get(i));
        }


    }

    @Test //TODO 1.1
    public void get3idWithJsonPath() {


        List<Integer> id =
                given()//appi methoduna gitmeden once yapilacaklar bolumu

                        .header("Authorization", "Bearer 6a844245e27f09f5577ce2b132f3158130f123e5c609c1a5c4c5a71443c33755")

                        .when()
                        .get("users")

                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().jsonPath().getList("id");
        System.out.println("id = " + id);
        for (int i = 0; i < 3; i++) {
            System.out.println("id " + (i + 1) + " = " + id.get(i));
        }


    }

    @Test //TODO 2
    public void allDataPutClass() {


        TODO2 todo2 =
                given()
                        //   .header("Authorization","Bearer 6a844245e27f09f5577ce2b132f3158130f123e5c609c1a5c4c5a71443c33755")

                        .log().uri()

                        .when()
                        .get("https://gorest.co.in/public/v2/users")                      //2309


                        .then()
                        .log().body()
                        .extract().as(TODO2.class);

        System.out.println("todo2.getName() = " + todo2.getName());
    }

    @Test //TODO 3 //1 usersi class a atma(nesneye atma)
    public void getUserByIDExtract() {


        TODO2 todo2 =
                given()
                        .header("Authorization", "Bearer 6a844245e27f09f5577ce2b132f3158130f123e5c609c1a5c4c5a71443c33755")
                        .contentType(ContentType.JSON)
                        .log().body()
                        .pathParam("UserID", 3414)

                        .when()
                        .get("https://gorest.co.in/public/v2/users/{3414}")                      //2309


                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().as(TODO2.class);

        System.out.println("todo2 = " + todo2);
        System.out.println("todo2.getName() = " + todo2.getName());
    }

    @Test //TODO 3.1 //1 usersi class a atma(nesneye atma)jsonpath ile cozum
    public void getUserByIDExtractWithJsonPath() {


        TODO2 todo2 =
                given()
                        .header("Authorization", "Bearer 6a844245e27f09f5577ce2b132f3158130f123e5c609c1a5c4c5a71443c33755")
                        .contentType(ContentType.JSON)
                        .log().body()
                        .pathParam("UserID", 3414)

                        .when()
                        .get("https://gorest.co.in/public/v2/users/{3414}")                      //2309


                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().jsonPath().getObject("", TODO2.class)//path olmadigi icin bos gectik

                ;

        System.out.println("todo2 = " + todo2);
        System.out.println("todo2.getName() = " + todo2.getName());
    }

    @Test //TODO 2.1 //tamamıni class a yollama
    public void getUserByIDExtractTumunuClasaAtma() {


        Response response =
                given()
                        .header("Authorization", "Bearer 6a844245e27f09f5577ce2b132f3158130f123e5c609c1a5c4c5a71443c33755")
                        .contentType(ContentType.JSON)
                        .log().body()


                        .when()
                        .get("https://gorest.co.in/public/v2/users")                      //2309


                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().response();

        TODO2[] userPath = response.as(TODO2[].class);
        List<TODO2> userJsonPath = response.jsonPath().getList("", TODO2.class);
        // System.out.println("todo2 = " + todo2);
        // System.out.println("todo2.getName() = " + todo2.getName());
    }

    @Test //TODO 2.1 //tamamıni class a yollama
    public void PartialExtract() {


        Response response =
                given()
                        .header("Authorization", "Bearer 6a844245e27f09f5577ce2b132f3158130f123e5c609c1a5c4c5a71443c33755")
                        .contentType(ContentType.JSON)
                        .log().body()


                        .when()
                        .get("https://gorest.co.in/public/v1/users")                      //version 1 olduguna dikkat et


                        .then()
                        // .log().body()
                        .statusCode(200)
                        .extract().response();

        //response.as();  tum gelen response uygun nesneleri clasa atar

        List<TODO2> partialData = response.jsonPath().getList("data", TODO2.class); //bu sekilde verilerin sadece bir
        // kismini alabiliyoruz.(path yazabildigimi icin)

        System.out.println("partialData = " + partialData);

// Daha önceki örneklerde (as) Clas dönüşümleri için tüm yapıya karşılık gelen
        // gereken tüm classları yazarak dönüştürüp istediğimiz elemanlara ulaşıyorduk.
        // Burada ise(JsonPath) aradaki bir veriyi clasa dönüştürerek bir list olarak almamıza
        // imkan veren JSONPATH i kullandık.Böylece tek class ise veri alınmış oldu
        // diğer class lara gerek kalmadan

        // path : class veya tip dönüşümüne imkan veremeyen direk veriyi verir. List<String> gibi
        // jsonPath : class dönüşümüne ve tip dönüşümüne izin vererek , veriyi istediğimiz formatta verir.


    }
}



class User{
    private String name;
    private String gender;
    private String email;
    private String status;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

// TODO : 3 usersın id sini alınız (path ve jsonPath ile ayrı ayrı yapınız)
// TODO : Tüm gelen veriyi bir nesneye atınız
// TODO : GetUserByID testinde dönen user ı bir nesneye atınız.

class TODO2{
    private int id;
    private String name;
    private String email;
    private String gender;
    private String status;

   private List<List<String>> ToDo;

    public List<List<String>> getToDo() {
        return ToDo;
    }

    public void setToDo(List<List<String>> toDo) {
        ToDo = toDo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TODO2{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", status='" + status + '\'' +

                '}';
    }
}