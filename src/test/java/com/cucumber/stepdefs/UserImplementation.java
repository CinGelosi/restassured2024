package com.cucumber.stepdefs;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertTrue;

public class UserImplementation {

    private RequestSpecification request;
    private ValidatableResponse json;

    private Response response;

    @Before
    public void before(){
        RestAssured.baseURI = "https://reqres.in/api/";
    }

    @Given("there are users loaded in the system")
    public void thereAreUsersLoadedInTheSystem(){
        request = given();
    }

    @When("a user retrieves the user list")
    public void aUserRetrievesTheUserList(){
        response = request.get("/users");
    }

    @Then("the status code is {int}")
    public void theStatusCodeIs(int statusCode){
        response.then().statusCode(statusCode);
    }

    @And("response includes {string}")
    public void responseIncludes(String user){
        JsonPath jsonPathUsers= new JsonPath(response.body().asString());
        List<String> jsonUserName = jsonPathUsers.getList("data.first_name");
        assertTrue("The value of the name field is not what is expected",
                jsonUserName.contains(user));
    }

    @Given("a user name {string} and a job {string}")
    public void aUserNameAndAJob(String name, String job){
        request = given().body("{\"name\": \""+name+"\",\"job\": \""+job+"\"}");
    }

    @When("the user is created")
    public void theUserIsCreated(){
        response = request.post("/users");
    }

    @Given("The user {string} with a new position {string}")
    public void aNewPosition(String name, String job){
        request = given()
                .header("Content-Type", "application/json")
                .body("{\"name\": \""+name+"\",\"job\": \""+job+"\"}");
    }
    @When("the user update the position")
    public void theUserUpdateThePosition(){
        response = request.put("/users/2");
    }
    @And("the body`s response is updated with {string} and {string}")
    public void bodyResponse(String name, String job){
        response.then()
                .body("name", equalTo(name))
                .body("job", equalTo(job));

    }

    @Given("an user")
    public void anUser(){
        request = given();
    }
    @When("the user is deleted")
    public void theUserIsDeleted(){
        response = request.delete("/users/2");
    }
}
