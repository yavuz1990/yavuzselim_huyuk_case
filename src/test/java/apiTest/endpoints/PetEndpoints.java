package apiTest.endpoints;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.File;

import static io.restassured.RestAssured.given;

public class PetEndpoints {


    public static Response findByStatus(String status){

        return given()
                .queryParam("status",status)
                .when()
                .get(Routes.find_by_status);
    }



    public static Response addANewPet(String body){


        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(body)
                .when()
                .post(Routes.add_a_new_pet);
    }

    public static Response updatePet(String body){


        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(body)
                .when()
                .put(Routes.update_pet);
    }



    public static Response findPetById(Integer petId){

        return given()
                .pathParam("petId",petId)
                .when()
                .get(Routes.find_pet_id);
    }


    public static Response updatePetWithFormData(Integer petId){


        return given()

                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .accept("*/*")
                .pathParam("petId",petId)
                .formParam("name","Test Update" )
                .formParam("status", "sold")
                .when()
                .post(Routes.update_pet_with_form_data);
    }

    public static Response updatePetWithFormDataInvalid(Integer petId) {
        // Hatalı parametreler simüle edilir
        return given()
                .contentType("application/x-www-form-urlencoded")
                .accept("*/*")
                .pathParam("petId", petId)
                .formParam("invalidParam", "invalidValue") // Hatalı parametreler simüle edilir
                .when()
                .post(Routes.update_pet_with_form_data);
    }

    public static Response uploadImage(Integer petId, File file){


        return given()

                .contentType(ContentType.MULTIPART)
                .accept(ContentType.JSON)
                .pathParam("petId",petId)
                .multiPart("additionalMetadata","new image")
                .multiPart("file", file, "image/jpeg")
                .when()
                .post(Routes.upload_image);
    }

    public static Response deletePet(Integer petId){

        return given()
                .pathParam("petId",petId)
                .when()
                .delete(Routes.deletePet);
    }


}
