package testscripts.api;

import java.io.IOException;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonParseException;

import com.fasterxml.jackson.databind.JsonMappingException;

public class TestClassRestAssured extends config.TestSetup {

	@Test

	public void assurityApiTest() throws JsonParseException, JsonMappingException, IOException {

		{

			ApiHelper assured = new ApiHelper();

			assured.validateAssurityAPI();

		}

	}

}
