package testscripts.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import com.relevantcodes.extentreports.LogStatus;
import config.FrameworkException;
import config.TestSetup;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ApiHelper extends TestSetup {

	public static String statusCode = "";

	Map<String, String> header = new HashMap<String, String>();

	HashMap<String, String> responseHeader = new HashMap<String, String>();

	JSONObject requestParam = new JSONObject();

	RequestSpecification request;

	public static String basePath = "";

	public static String UserId = "";

	Response response;

	public Response validateAssurityAPI() {

		try {
			basePath = "https://api.tmsandbox.co.nz/v1/Categories/6327/Details.json";

			header = new HashMap<String, String>();

			header.put("content-type", "application/json");

			HashMap<String, String> param = new HashMap<String, String>();

			param.put("catalogue", "false");

			request = RestAssured.given().queryParams(param).headers(header);

			response = request.request(Method.GET, basePath);

			statusCode = Integer.toString(response.getStatusCode());

			System.out.println(response.asString());

			if (statusCode.equals("200")) {

				loggerLogReportPass("Status code of the API is as expected as " + statusCode);

				if (response.jsonPath().getString("Name").equals("Carbon credits")) {

					loggerLogReportPass("As expected the name is :: Carbon credits");

				} else {

					loggerLogReportFail("Expected name is :: Carbon credits. Actual Value "

							+ response.jsonPath().getString("Name"));

				}

				if (response.jsonPath().getBoolean("CanRelist")) {

					loggerLogReportPass("As expected the field CanRelist has TRUE value ");

				} else {

					loggerLogReportFail("Expected value of field CanRelist is <<TRUE>>, actual value is  >> "

							+ response.jsonPath().getBoolean("CanRelist"));

				}

				List<Map<String, String>> promotionMapList = response.jsonPath().getList("Promotions");

				String actPromotionGalleryDesc = "";

				String expPromotionGalleryDesc = "Good position in category";

				for (int i = 0; i < promotionMapList.size(); i++) {

					if (promotionMapList.get(i).get("Name").equals("Gallery")) {

						actPromotionGalleryDesc = promotionMapList.get(i).get("Description");

						if (actPromotionGalleryDesc.trim().contentEquals(expPromotionGalleryDesc)) {

							loggerLogReportPass("As expected field Name > Gallery has proper description as "

									+ expPromotionGalleryDesc);

						} else {

							loggerLogReportFail("field Name > Gallery not having proper description exp "

									+ expPromotionGalleryDesc + " Actual value " + actPromotionGalleryDesc);

						}

						break;

					}

				}

			} else {

				logger.log(LogStatus.FAIL,

						"Expected Status Code of API is 200 and actual response code is  " + statusCode);

			}

		} catch (Exception e) {

			throw new FrameworkException("Exception occured while getting the" + e.getStackTrace()[1].getMethodName()

					+ " response :--" + e.getClass() + "--" + e.getMessage());

		}

		return response;

	}

}