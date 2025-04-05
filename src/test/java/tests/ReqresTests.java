package tests;

import Reqres_objects.*;
import com.google.gson.Gson;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ReqresTests {

    public static final String BASE_URL = "https://reqres.in/api/";
    SoftAssert softAssert = new SoftAssert();

    @Test(description = "Create User, check status code, 'name' field, 'job' field")
    public void postCreateUserTest() {

        User user = User.builder()
                .name("morpheus")
                .job("leader")
                .build();

        given()
                .log().all()
                .body(user)
                .header("Content-type", "application/json")
                .log().all()
                .when()
                .post(BASE_URL + "users")
                .then()
                .log().all()
                .body("name", equalTo("morpheus"),
                        "job", equalTo("leader"))
                .statusCode(201);
    }

    @Test(description = "Get list of users and check status code and all exist fields of the first user ")
    public void getUsersListTest() {

        String body = given()
                .log().all()
         .when()
                .get(BASE_URL + "users?page=2")
         .then()
                .log().all()
                .statusCode(200)
                .extract().body().asString();

        UsersList usersList = new Gson().fromJson(body, UsersList.class);
        softAssert.assertEquals(usersList.getData().get(0).getId(), 7);
        softAssert.assertEquals(usersList.getData().get(0).getFirstName(), "Michael");
        softAssert.assertEquals(usersList.getData().get(0).getLastName(), "Lawson");
        softAssert.assertEquals(usersList.getData().get(0).getEmail(), "michael.lawson@reqres.in");
        softAssert.assertEquals(usersList.getData().get(0).getAvatar(), "https://reqres.in/img/faces/7-image.jpg");
        softAssert.assertAll();
    }

    @Test(description = "Get single user and check status code and all exist fields ")
    public void getSingleUserTest() {

        String body = given()
                .log().all()
                .when()
                .get(BASE_URL + "users/2")
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().asString();

        UserForUsersList user = new Gson().fromJson(body, UserForUsersList.class);
        softAssert.assertEquals(user.getData().getId(), 2);
        softAssert.assertEquals(user.getData().getFirstName(), "Janet");
        softAssert.assertEquals(user.getData().getLastName(), "Weaver");
        softAssert.assertEquals(user.getData().getEmail(), "janet.weaver@reqres.in");
        softAssert.assertEquals(user.getData().getAvatar(), "https://reqres.in/img/faces/2-image.jpg");
        softAssert.assertEquals(user.getSupport().getUrl(),"https://contentcaddy.io?utm_source=reqres&utm_medium=json&utm_campaign=referral");
        softAssert.assertEquals(user.getSupport().getText(),"Tired of writing endless social media content? Let Content Caddy generate it for you.");
        softAssert.assertAll();
    }

    @Test(description = "Get a user that doesn't exist and check status code ")
    public void getSingleUserNotFoundTest() {

        given()
                .log().all()
        .when()
                .get(BASE_URL + "users/23")
        .then()
                .log().all()
                .statusCode(404);
    }

    @Test(description = "Get list of colors and check status code and all exist fields of the first color ")
    public void getColorsListTest() {

        String body = given()
                .log().all()
                .when()
                .get(BASE_URL +"unknown")
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().asString();

        ColorList colorList = new Gson().fromJson(body, ColorList.class);
        softAssert.assertEquals(colorList.getData().get(0).getId(), 1);
        softAssert.assertEquals(colorList.getData().get(0).getName(), "cerulean");
        softAssert.assertEquals(colorList.getData().get(0).getYear(), 2000);
        softAssert.assertEquals(colorList.getData().get(0).getColor(), "#98B2D1");
        softAssert.assertEquals(colorList.getData().get(0).getPantoneValue(), "15-4020");
        softAssert.assertAll();
    }

    @Test(description = "Get single color and check status code and all exist fields")
    public void getSingleColorTest() {

        String body = given()
                .log().all()
                .when()
                .get(BASE_URL +"unknown/2")
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().asString();

        Color color = new Gson().fromJson(body, Color.class);
        softAssert.assertEquals(color.getData().getId(), 2);
        softAssert.assertEquals(color.getData().getName(), "fuchsia rose");
        softAssert.assertEquals(color.getData().getYear(), 2001);
        softAssert.assertEquals(color.getData().getColor(), "#C74375");
        softAssert.assertEquals(color.getData().getPantoneValue(), "17-2031");
        softAssert.assertAll();
    }

    @Test(description = "Get a color that doesn't exist and check status code")
    public void getSingleColorNotFoundTest() {

        given()
                .log().all()
                .when()
                .get(BASE_URL +"unknown/23")
                .then()
                .log().all()
                .statusCode(404);
    }

    @Test(description = "put update and check status code and all exist fields")
    public void putUpdateTest() {

        User user = User.builder()
                .name("morpheus")
                .job("zion resident")
                .build();

        given()
                .log().all()
                .body(user)
                .header("Content-type", "application/json")
                .log().all()
                .when()
                .put(BASE_URL +"users/2")
                .then()
                .log().all()
                .body("name", equalTo("morpheus"),
                        "job", equalTo("zion resident"),
                        "updatedAt", notNullValue())
                .statusCode(200);
    }

    @Test(description = "patch update and check status code and all exist fields")
    public void patchUpdateTest() {
        
        User user = User.builder()
                .name("morpheus")
                .job("zion resident")
                .build();

        given()
                .log().all()
                .body(user)
                .header("Content-type", "application/json")
                .log().all()
                .when()
                .patch(BASE_URL +"users/2")
                .then()
                .log().all()
                .body("name", equalTo("morpheus"),
                        "job", equalTo("zion resident"),
                        "updatedAt", notNullValue())
                .statusCode(200);
    }

    @Test(description = "delete update and check status code")
    public void deleteUpdateTest() {

        given()
                .log().all()
                .when()
                .delete(BASE_URL +"users/2")
                .then()
                .log().all()
                .statusCode(204);
    }

    @Test(description = "post successful register data and check status code and all exist fields")
    public void postSuccessfulRegisterTest() {

        given()
                .log().all()
                .body("{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }")
                .header("Content-type", "application/json")
                .log().all()
                .when()
                .post(BASE_URL +"register")
                .then()
                .log().all()
                .body("id", equalTo(4),
                        "token", equalTo("QpwL5tke4Pnpja7X4"))
                .statusCode(200);
    }


    @Test(description = "post unsuccessful register data and check status code and all exist fields")
    public void postUnsuccessfulRegisterTest() {

        given()
                .log().all()
                .body("{ \"email\": \"sydney@fife\"}")
                .header("Content-type", "application/json")
                .log().all()
                .when()
                .post(BASE_URL +"register")
                .then()
                .log().all()
                .body("error", equalTo("Missing password"))
                .statusCode(400);
    }

    @Test(description = "post successful login data and check status code and all exist fields")
    public void postSuccessfulLoginTest() {

        given()
                .log().all()
                .body("{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }")
                .header("Content-type", "application/json")
                .log().all()
                .when()
                .post(BASE_URL +"login")
                .then()
                .log().all()
                .body("token", equalTo("QpwL5tke4Pnpja7X4"))
                .statusCode(200);
    }

    @Test(description = "post unsuccessful login data and check status code and all exist fields")
    public void postUnsuccessfulLoginTest() {

        given()
                .log().all()
                .body("{ \"email\": \"peter@klaven\"}")
                .header("Content-type", "application/json")
                .log().all()
                .when()
                .post(BASE_URL +"login")
                .then()
                .log().all()
                .body("error", equalTo("Missing password"))
                .statusCode(400);
    }

    @Test(description = "Get delayed response and check status code and all exist fields of the first user ")
    public void getDelayedResponseTest() {

        String body = given()
                .log().all()
                .when()
                .get(BASE_URL + "users?delay=3")
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().asString();

        UsersList usersList = new Gson().fromJson(body, UsersList.class);
        softAssert.assertEquals(usersList.getData().get(0).getId(), 1);
        softAssert.assertEquals(usersList.getData().get(0).getEmail(), "george.bluth@reqres.in");
        softAssert.assertEquals(usersList.getData().get(0).getFirstName(), "George");
        softAssert.assertEquals(usersList.getData().get(0).getLastName(), "Bluth");
        softAssert.assertEquals(usersList.getData().get(0).getAvatar(), "https://reqres.in/img/faces/1-image.jpg");
        softAssert.assertAll();
    }
}
