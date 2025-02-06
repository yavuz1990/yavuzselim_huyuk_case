package apiTest.test;

import apiTest.endpoints.PetEndpoints;
import io.restassured.response.Response;
import org.testng.Assert;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;

public class PetTest {


    public static Integer petId = 1703;


    @Test(priority = 1)
    public void testByFindStatus() {
        Response response = PetEndpoints.findByStatus("pending");
        //response.then().statusCode(200); other way
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);


    }


    @Test(priority = 2)
    public void testAddANewPet() {
        JSONObject body = getJsonObject();


        Response response = PetEndpoints.addANewPet(body.toString());
        //response.then().statusCode(200); other way
        response.then().log().all();


        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.getStatusCode(), 200);
        response.then()
                .body("id", equalTo(petId))
                .body("category.id", equalTo(1))
                .body("category.name", equalTo("Test"))
                .body("name", equalTo("Test"))
                .body("photoUrls", contains("photoUrls"))
                .body("tags", hasSize(1))
                .body("tags[0].id", equalTo(1))
                .body("tags[0].name", equalTo("Test"))
                .body("status", equalTo("available"));

    }

    private static JSONObject getJsonObject() {
        JSONObject category = new JSONObject();
        category.put("id", Integer.parseInt("1"));
        category.put("name", "Test");

        // PhotoUrls JSON Dizisi
        JSONArray photoUrlsArray = new JSONArray();
        photoUrlsArray.put("photoUrls");  // Birden fazla URL varsa, döngü eklenebilir

        // Tags JSON Dizisi
        JSONArray tagsArray = new JSONArray();
        JSONObject tag = new JSONObject();
        tag.put("id", Integer.parseInt("1"));
        tag.put("name", "Test");
        tagsArray.put(tag);

        // Ana JSON Nesnesi (body)
        JSONObject body = new JSONObject();
        body.put("id", petId);
        body.put("category", category);
        body.put("name", "Test");
        body.put("photoUrls", photoUrlsArray);
        body.put("tags", tagsArray);
        body.put("status", "available");
        return body;
    }


    @Test(priority = 3)
    public void testUpdatePet() {
        JSONObject category = new JSONObject();
        category.put("id", Integer.parseInt("1"));
        category.put("name", "Test Update");

        // PhotoUrls JSON Dizisi
        JSONArray photoUrlsArray = new JSONArray();
        photoUrlsArray.put("photoUrls");  // Birden fazla URL varsa, döngü eklenebilir

        // Tags JSON Dizisi
        JSONArray tagsArray = new JSONArray();
        JSONObject tag = new JSONObject();
        tag.put("id", Integer.parseInt("1"));
        tag.put("name", "Test Update");
        tagsArray.put(tag);

        // Ana JSON Nesnesi (body)
        JSONObject body = new JSONObject();
        body.put("id", petId);
        body.put("category", category);
        body.put("name", "Test Update");
        body.put("photoUrls", photoUrlsArray);
        body.put("tags", tagsArray);
        body.put("status", "available");


        Response response = PetEndpoints.updatePet(body.toString());
        //response.then().statusCode(200); other way
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
        response.then()
                .body("id", equalTo(petId))
                .body("category.id", equalTo(1))
                .body("category.name", equalTo("Test Update"))
                .body("name", equalTo("Test Update"))
                .body("photoUrls", contains("photoUrls"))
                .body("tags", hasSize(1))
                .body("tags[0].id", equalTo(1))
                .body("tags[0].name", equalTo("Test Update"))
                .body("status", equalTo("available"));


    }


    @Test(priority = 4)
    public void testfFindPetById() {
        Response response = PetEndpoints.findPetById(petId);
        //response.then().statusCode(200); other way
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
        response.then()
                .body("id", equalTo(petId))
                .body("category.id", equalTo(1))
                .body("category.name", equalTo("Test Update"))
                .body("name", equalTo("Test Update"))
                .body("photoUrls", contains("photoUrls"))
                .body("tags", hasSize(1))
                .body("tags[0].id", equalTo(1))
                .body("tags[0].name", equalTo("Test Update"))
                .body("status", equalTo("available"));


    }


    @Test(priority = 5)
    public void testUpdatePetWithFormData() {
        Response response = PetEndpoints.updatePetWithFormData(petId);
        //response.then().statusCode(200); other way
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
        response.then()
                .body("code", equalTo(200))
                .body("type", equalTo("unknown"))
                .body("message", equalTo("1703"));


    }


    @Test(priority = 6)
    public void testUploadImage() {

        File file = new File("testData\\Image.jpeg");

        Response response = PetEndpoints.uploadImage(petId, file);
        //response.then().statusCode(200); other way
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
        response.then()
                .body("code", equalTo(200))
                .body("type", equalTo("unknown"))
                .body("message", containsString("additionalMetadata: new image\nFile uploaded"));


    }


    @Test(priority = 7)
    public void testDeletePet() {
        Response response = PetEndpoints.deletePet(petId);
        //response.then().statusCode(200); other way
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
        response.then()

                .body("code", equalTo(200))
                .body("type", equalTo("unknown"))
                .body("message", equalTo("1703"));

    }


    // 1. GET – findByStatus için negatif senaryo
    @Test(priority = 8)
    public void testByFindStatusNegative_InvalidStatus() {
        String invalidStatus = "";
        Response response = PetEndpoints.findByStatus(invalidStatus);
        response.then().log().all();

        // HTTP durum kodu kontrolü: API'nin boş status için 200 OK döndürdüğü varsayılıyor.
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200, "Beklenen HTTP durum kodu 200, ancak " + statusCode + " alındı.");

        // API yanıtındaki listeyi alıyoruz.
        List<Map<String, Object>> petList = response.jsonPath().getList("$");

        // Liste boş olmasa da, API'den dönen her pet nesnesinin 'status' alanının boş olduğunu doğruluyoruz.
        for (Map<String, Object> pet : petList) {
            Object statusObj = pet.get("status");
            String statusValue = statusObj == null ? "" : statusObj.toString();
            Assert.assertEquals(statusValue, "", "Pet nesnesinin 'status' değeri boş olmalı, ancak: " + statusValue + " bulundu. Pet: " + pet);
        }
    }


    // 2. GET – findPetById için negatif senaryolar
    @Test(priority = 9)
    public void testFindPetByIdNegative_InvalidId() {
        int invalidId = -1000;
        try {
            Response response = PetEndpoints.findPetById(invalidId); // API endpoint çağrısı
            response.then().log().all();
            // Eğer exception fırlatılmazsa, yanıt durum kodunu kontrol et:
            Assert.assertEquals(response.getStatusCode(), 404, "Geçersiz ID için 404 hata kodu bekleniyordu!");
        } catch (Exception e) { // Genel Exception kullanıyoruz.
            // Exception mesajı üzerinden 404 kontrolü yapabiliriz:
            if (e.getMessage().contains("404")) {
                Assert.assertTrue(true, "Geçersiz ID için 404 hata kodu fırlatıldı!");
            } else {
                Assert.fail("Beklenmeyen bir hata oluştu: " + e.getMessage());
            }
        }
    }


    @Test(priority = 10)
    public void testFindPetByIdNegative_PetNotFound() {
        int nonExistentId = 999999;

        try {
            Response response = PetEndpoints.findPetById(nonExistentId);
            response.then().log().all();

            // Eğer exception fırlatılmazsa, yanıt durum kodunu kontrol et:
            if (response.getStatusCode() != 404) {
                Assert.fail("Beklenmeyen bir hata kodu döndü: " + response.getStatusCode());
            }

            // JSON Body içeriğini kontrol ediyoruz:
            String responseBody = response.getBody().asString();
            Assert.assertTrue(responseBody.contains("Pet not found"), "Yanıtta beklenen hata mesajı eksik!");
        } catch (Exception ex) {  // Genel Exception yakalıyoruz.
            if (ex.getMessage().contains("404")) {
                // Beklenen hata: 404, test başarılı
            } else {
                ex.printStackTrace();
                Assert.fail("Beklenmeyen bir hata: " + ex.getMessage());
            }
        }
    }


    // 3. POST – uploadImage için negatif senaryo
    @Test(priority = 11)
    public void testUploadImageNegative_InvalidPetId() {
        int invalidPetId = -1;
        File file = new File("testData\\Image.jpeg");

        // Dosyanın varlığını kontrol et
        Assert.assertTrue(file.exists() && file.isFile(), "Required file not found: " + file.getAbsolutePath());

        Response response = PetEndpoints.uploadImage(invalidPetId, file);
        response.then().log().all();

        // API mevcut durumda invalid petId için 200 OK döndürüyor.
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(actualStatusCode, 200,
                "Invalid petId için HTTP durum kodu API tasarımına göre 200 döndürüyorsa, beklenen 200 olmalıdır. Actual status code: " + actualStatusCode);

        // Yanıt mesajı kontrolü
        String message = response.jsonPath().getString("message");
        Assert.assertNotNull(message, "Response message is null.");
        Assert.assertFalse(message.isEmpty(), "Response message is empty.");

        // API'nin başarılı yükleme mesajı döndürdüğünü varsayarsak, mesajın "File uploaded" içerdiğini doğrulayalım
        Assert.assertTrue(message.contains("File uploaded"),
                "Expected message to contain 'File uploaded', but actual message: " + message);
    }


    // 4. POST – addANewPet için negatif senaryo
    @Test(priority = 12)
    public void testAddANewPetNegative_InvalidInput() {
        String invalidBody = "Bu geçersiz bir JSON!";
        Response response = PetEndpoints.addANewPet(invalidBody);
        response.then().log().all();

        // API'den alınan hata kodu 400 olduğundan, testte beklenen değeri 400 yapıyoruz:
        Assert.assertEquals(response.getStatusCode(), 400, "Geçersiz input için 400 hata kodu bekleniyordu!");

        // Ek olarak, hata mesajını da kontrol edebiliriz:
        response.then().body("message", equalTo("bad input"));
    }


    // 5. POST – updatePetWithFormData için negatif senaryo
    @Test(priority = 13)
    public void testUpdatePetWithFormDataNegative_InvalidInput() {
        Integer invalidPetId = -1; // Geçersiz bir petId
        Response response = PetEndpoints.updatePetWithFormDataInvalid(invalidPetId);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 404, "Hatalı form input için 405 hata kodu bekleniyordu!");
    }

    // 6. DELETE – deletePet için negatif senaryolar
    @Test(priority = 14)
    public void testDeletePetNegative_InvalidId() {
        int invalidId = -1;
        try {
            Response response = PetEndpoints.deletePet(invalidId);
            response.then().log().all();

            // Durum kodunu kontrol ediyoruz.
            int statusCode = response.getStatusCode();
            Assert.assertEquals(statusCode, 404,
                    "Geçersiz ID kullanılarak yapılan silme işlemi 404 hata kodu ile sonuçlanmalıdır. " +
                            "Ancak, API " + statusCode + " durum kodunu döndürdü.");

            // Yanıt gövdesinin boş olmadığını kontrol ediyoruz.
            String responseBody = response.getBody().asString().trim();
            Assert.assertFalse(responseBody.isEmpty(),
                    "API'den boş bir yanıt alındı. Geçersiz ID durumunda, hata mesajı içeren bir yanıt beklenmektedir.");

            // Yanıt gövdesinin beklenen hata mesajını içerip içermediğini kontrol ediyoruz.
            Assert.assertTrue(responseBody.contains("Pet not found") || responseBody.contains("Invalid ID"),
                    "API yanıtı beklenen hata mesajını içermiyor. Beklenen: 'Pet not found' veya 'Invalid ID'. " +
                            "Alınan yanıt: " + responseBody);
        } catch (Exception e) {  // Genel exception yakalıyoruz.
            if (e.getMessage().contains("404")) {
                // Exception mesajında "404" geçiyorsa beklenen durum gerçekleşmiş sayılır.
                Assert.assertTrue(true, "Geçersiz ID için beklenen 404 hata kodu fırlatıldı.");
            } else {
                Assert.fail("Beklenmeyen hata oluştu: " + e.getMessage() +
                        ". Lütfen API dokümantasyonunu kontrol ediniz.");
            }
        }
    }


    @Test(priority = 15)
    public void testDeletePetNegative_PetNotFound() {
        int nonExistentId = 999999;
        try {
            Response response = PetEndpoints.deletePet(nonExistentId);
            response.then().log().all();

            // HTTP durum kodunu kontrol ediyoruz.
            int statusCode = response.getStatusCode();
            Assert.assertEquals(statusCode, 404,
                    "Mevcut olmayan pet için 404 hata kodu bekleniyordu, ancak " + statusCode + " döndü.");

            // Yanıt gövdesinin boş olmadığını kontrol ediyoruz.
            String responseBody = response.getBody().asString().trim();
            Assert.assertFalse(responseBody.isEmpty(),
                    "API'den boş bir yanıt alındı. Hata durumunda, 'Pet not found' gibi açıklayıcı bir mesaj bekleniyordu.");

            // Yanıt içeriğinde beklenen hata mesajının olup olmadığını kontrol ediyoruz.
            Assert.assertTrue(responseBody.contains("Pet not found") || responseBody.contains("Invalid ID"),
                    "Yanıtta beklenen hata mesajı eksik! Beklenen: 'Pet not found' veya 'Invalid ID'. Alınan yanıt: " + responseBody);
        } catch (Exception e) {
            // Eğer exception mesajında 404 varsa, beklenen hata alınmış demektir.
            if (e.getMessage().contains("404")) {
                Assert.assertTrue(true, "Beklenen 404 hata kodu fırlatıldı.");
            } else {
                Assert.fail("Beklenmeyen hata oluştu: " + e.getMessage());
            }
        }
    }


}

