package apiTest.endpoints;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import apiTest.payload.User;

public class UserEndPoints {

    // User Module ---> Create,Read,Update,Delete

    public static Response createUser(User payload){

        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .post(Routes.post_url);
    }

    public static Response readUser(String userName){

        return given()
                .pathParam("username",userName)
                .when()
                .get(Routes.get_url);
    }

    public static Response updateUser(String userName,User payload){

        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .pathParam("username",userName)
                .when()
                .put(Routes.put_url);
    }

    public static Response deleteUser(String userName){

        return given()
                .pathParam("username",userName)
                .when()
                .delete(Routes.delete_url);
    }



}
