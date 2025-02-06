package apiTest.test;

import com.github.javafaker.Faker;
import apiTest.endpoints.UserEndPoints;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import apiTest.payload.User;



public class UserTests {

    Faker faker;
    User userPayload;
    public Logger logger;

    @BeforeClass()
    public void setUserPayload(){
        faker=new Faker();
        userPayload=new User();

        userPayload.setId(faker.idNumber().hashCode());
        userPayload.setUsername(faker.name().username());
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());
        userPayload.setPassword(faker.internet().password(5,10));
        userPayload.setPhone(faker.phoneNumber().cellPhone());
        

        // for logs

        logger= LogManager.getLogger(this.getClass());
        logger.debug("***debugging***");

    }

    @Test(priority = 1)
    public void testCreateUser(){

        logger.info("********* create user **********");

        Response response=UserEndPoints.createUser(userPayload);
        response.then().log().all();
        // response.then().statusCode(200);  other way

        Assert.assertEquals(response.getStatusCode(),200);

        logger.info("******** user is created *******");

    }

    @Test(priority = 2)
    public void testReadUser(){
        Response response=UserEndPoints.readUser(userPayload.getUsername());
        //response.then().statusCode(200); other way
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200);

    }

    @Test(priority = 3)
    public void testUpdateUser(){
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());

        Response response=UserEndPoints.updateUser(userPayload.getUsername(), userPayload);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200);

        // check data after update
        Response responseAfterUpdate=UserEndPoints.readUser(userPayload.getUsername());
        responseAfterUpdate.then().log().all();

        Assert.assertEquals(responseAfterUpdate.getStatusCode(),200);


    }

    @Test(priority = 4)
    public void testDeleteUser(){

        Response response=UserEndPoints.deleteUser(userPayload.getUsername());
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200);

    }





}
