package ru.lanit.at.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.gherkin.internal.com.eclipsesource.json.Json;
import io.cucumber.gherkin.internal.com.eclipsesource.json.JsonValue;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Пусть;
import io.qameta.allure.Allure;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.builder.RequestSpecBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import ru.lanit.at.api.ApiRequest;
import ru.lanit.at.api.models.RequestModel;
import ru.lanit.at.api.testcontext.ContextHolder;
import ru.lanit.at.utils.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static ru.lanit.at.api.testcontext.ContextHolder.replaceVarsIfPresent;
import static ru.lanit.at.utils.JsonUtil.getFieldFromJson;

public class ApiSteps {

	private static final Logger LOG = LoggerFactory.getLogger(ApiSteps.class);
	private ApiRequest apiRequest;
	private String newSpaceId;
	private String newSpaceBoardId;
	private String newCardId;
	private String newChecklistId;
	private String newItemChecklistId1;
	private String newItemChecklistId2;
	private String newColumnId;

	private final Map<String, String> queryParams = new HashMap<>();
	private String body;

	@И("создать запрос создания нового Space")
	public void createRequest(RequestModel requestModel) {
		apiRequest = new ApiRequest(requestModel);
	}

	@И("добавить header")
	public void addHeaders(DataTable dataTable) {
		Map<String, String> headers = new HashMap<>();
		dataTable.asLists().forEach(it -> headers.put(it.get(0), it.get(1)));
		apiRequest.setHeaders(headers);
	}

	@И("добавить query параметры")
	public void addQuery(DataTable dataTable) {
		Map<String, String> query = new HashMap<>();
		dataTable.asLists().forEach(it -> query.put(it.get(0), it.get(1)));
		apiRequest.setQuery(query);
	}

	@И("отправить запрос")
	public void send() {
		apiRequest.sendRequest();
	}

	@И("статус код {int}")
	public void expectStatusCode(int code) {
		int actualStatusCode = apiRequest.getResponse().statusCode();
		String responseBody = apiRequest.getResponse().body().asPrettyString();
		newSpaceId = VariableUtil.extractBrackets(getFieldFromJson(responseBody, "id"));
		ContextHolder.put("spaceID", newSpaceId);
		LOG.info("Сохранена переменная: {}={}", "spaceID", newSpaceId);
		Assert.assertEquals(actualStatusCode, code);
	}

	@И("извлечь данные")
	public void extractVariables(Map<String, String> vars) {
		String responseBody = apiRequest.getResponse().body().asPrettyString();
		vars.forEach((k, jsonPath) -> {
			jsonPath = replaceVarsIfPresent(jsonPath);
			String extractedValue = VariableUtil.extractBrackets(getFieldFromJson(responseBody, jsonPath));
			ContextHolder.put(k, extractedValue);
			Allure.addAttachment(k, "application/json", extractedValue, ".txt");
			LOG.info("Извлечены данные: {}={}", k, extractedValue);
		});
	}

	@И("сгенерировать переменные")
	public void generateVariables(Map<String, String> table) {
		table.forEach((k, v) -> {
			String value = DataGenerator.generateValueByMask(replaceVarsIfPresent(v));
			ContextHolder.put(k, value);
			Allure.addAttachment(k, "application/json", k + ": " + value, ".txt");
			LOG.info("Сгенерирована переменная: {}={}", k, value);
		});
	}

	@И("создать контекстные переменные")
	public void createContextVariables(Map<String, String> table) {
		table.forEach((k, v) -> {
			ContextHolder.put(k, v);
			LOG.info("Сохранена переменная: {}={}", k, v);
		});
	}

	@И("сравнить значения")
	public void compareVars(DataTable table) {
		table.asLists().forEach(it -> {
			String expect = replaceVarsIfPresent(it.get(0));
			String actual = replaceVarsIfPresent(it.get(2));
			boolean compareResult = CompareUtil.compare(expect, actual, it.get(1));
			Assert.assertTrue(compareResult, String.format("Ожидаемое: '%s'\nФактическое: '%s'\nОператор сравнения: '%s'\n", expect, actual, it.get(1)));
			Allure.addAttachment(expect, "application/json", expect + it.get(1) + actual, ".txt");
			LOG.info("Сравнение значений: {} {} {}", expect, it.get(1), actual);
		});
	}

	@И("подождать {int} сек")
	public void waitSeconds(int timeout) {
		Sleep.pauseSec(timeout);
	}


	@И("создать запрос создания новой Space Board")
	public void createRequestBoard(RequestModel requestModel) {
		apiRequest = new ApiRequest(requestModel);

	}

	@И("Новая Space Board статус код {int}")
	public void expectStatusCodeNewSpaceBoard(int code) {
		int actualStatusCode = apiRequest.getResponse().statusCode();
		String responseBody = apiRequest.getResponse().body().asPrettyString();
		newSpaceBoardId = VariableUtil.extractBrackets(getFieldFromJson(responseBody, "id"));
		ContextHolder.put("spaceBoardID", newSpaceBoardId);
		LOG.info("Сохранена переменная: {}={}", "spaceBoardID", newSpaceBoardId);
		Assert.assertEquals(actualStatusCode, code);
	}

	@И("создать запрос создания новой Board Card")
	public void createRequestCard(RequestModel requestModel) {
		try {
			//Read from file
			String jsonn = new String(Files.readAllBytes(Paths.get("E:/ANTARA04/SCHOOL2/06/autotest-rest/src/test/resources/json/addNewCard.json")));
			//Read from String
			JsonValue value = Json.parse(jsonn);
			value.asObject().set("board_id", Integer.valueOf(newSpaceBoardId));

			//Debug
			System.out.println(value.toString());
			//Write to file
			PrintWriter writer = new PrintWriter("E:/ANTARA04/SCHOOL2/06/autotest-rest/src/test/resources/json/addNewCard.json", "UTF-8");
			writer.println(value.toString());
			writer.close();
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
		apiRequest = new ApiRequest(requestModel);

	}

	@И("Новая Board Card статус код {int}")
	public void expectStatusCodeNewCard(int code) {
		int actualStatusCode = apiRequest.getResponse().statusCode();
		String responseBody = apiRequest.getResponse().body().asPrettyString();
		newCardId = VariableUtil.extractBrackets(getFieldFromJson(responseBody, "id"));
		ContextHolder.put("cardID", newCardId);
		LOG.info("Сохранена переменная: {}={}", "cardID", newCardId);
		Assert.assertEquals(actualStatusCode, code);
	}

	@И("создать запрос добавления файла в Card")
	public void createRequestAddFileToCard(RequestModel requestModel) {

		apiRequest = new ApiRequest(requestModel);
		System.out.println("requestModel = " + requestModel);
	}

	@И("добавление файла в Card статус код {int}")
	public void expectStatusCodeAddFileToCard(int code) {
		int actualStatusCode = apiRequest.getResponse().statusCode();
		Assert.assertEquals(actualStatusCode, code);
	}

	@И("создать запрос назначения срока выполения Card")
	public void createRequestSetDataDeadLineToCard(RequestModel requestModel) {
		apiRequest = new ApiRequest(requestModel);
	}

	@И("новый срок выполнения Card статус код {int}")
	public void expectStatusCodeSetDataDeadLineToCard(int code) {

		int actualStatusCode = apiRequest.getResponse().statusCode();
		Assert.assertEquals(actualStatusCode, code);
	}

	@И("добавлено описание Card статус код {int}")
	public void expectStatusCodeSetDescriptionToCard(int code) {
	}

	@И("создать запрос создания чеклиста в Card")
	public void createRequestAddCheckListToCard(RequestModel requestModel) {
		apiRequest = new ApiRequest(requestModel);
	}

	@И("создание чеклиста в Card статус код {int}")
	public void expectStatusCodeAddCheckListToCard(int code)  {
		int actualStatusCode = apiRequest.getResponse().statusCode();
		String responseBody = apiRequest.getResponse().body().asPrettyString();
		newChecklistId = VariableUtil.extractBrackets(getFieldFromJson(responseBody, "id"));
		ContextHolder.put("checklistID", newChecklistId);
		LOG.info("Сохранена переменная: {}={}", "checklistID", newChecklistId);
		Assert.assertEquals(actualStatusCode, code);
	}

	@Пусть("в чеклист добавлен пункт1")
	public void setItemCheckListCard() {

		try {
			//Read from file
			String jsonn = new String(Files.readAllBytes(Paths.get("E:/ANTARA04/SCHOOL2/06/autotest-rest/src/test/resources/json/addItemCheckListCard.json")));
			//Read from String
			JsonValue value = Json.parse(jsonn);
			value.asObject().set("text", "Понять протокол HTTP");

			//Debug
			System.out.println(value.toString());
			//Write to file
			PrintWriter writer = new PrintWriter("E:/ANTARA04/SCHOOL2/06/autotest-rest/src/test/resources/json/addItemCheckListCard.json", "UTF-8");
			writer.println(value.toString());
			writer.close();
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}

	}


	@И("создать запрос добавления пункта в чеклист Card")
	public void createRequestAddItemCheckListCard(RequestModel requestModel) {
		apiRequest = new ApiRequest(requestModel);
	}

	@И("добавление первого пункта в чеклист Card статус код {int}")
	public void  expectStatusCodeAddItem1CheckListCard(int code) {
		int actualStatusCode = apiRequest.getResponse().statusCode();
		String responseBody = apiRequest.getResponse().body().asPrettyString();
		newItemChecklistId1 = VariableUtil.extractBrackets(getFieldFromJson(responseBody, "id"));
		ContextHolder.put("newItemCheckListID1", newItemChecklistId1);
		LOG.info("Сохранена переменная: {}={}", "newItemCheckListID1", newItemChecklistId1);
		Assert.assertEquals(actualStatusCode, code);
	}

	@Пусть("в чеклист добавлен пункт2")
	public void setItem2CheckListCard() {
		try {
			//Read from file
			String jsonn = new String(Files.readAllBytes(Paths.get("E:/ANTARA04/SCHOOL2/06/autotest-rest/src/test/resources/json/addItemCheckListCard.json")));
			//Read from String
			JsonValue value = Json.parse(jsonn);
			value.asObject().set("text", "Выучить методы запросов");
			//Debug
			System.out.println(value.toString());
			//Write to file
			PrintWriter writer = new PrintWriter("E:/ANTARA04/SCHOOL2/06/autotest-rest/src/test/resources/json/addItemCheckListCard.json", "UTF-8");
			writer.println(value.toString());
			writer.close();
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
	}

	@И("добавление второго пункта в чеклист Card статус код {int}")
	public void expectStatusCodeAddItem2CheckListCard(int code) {

		int actualStatusCode = apiRequest.getResponse().statusCode();
		String responseBody = apiRequest.getResponse().body().asPrettyString();
		newItemChecklistId2 = VariableUtil.extractBrackets(getFieldFromJson(responseBody, "id"));
		ContextHolder.put("newItemCheckListID2", newItemChecklistId2);
		LOG.info("Сохранена переменная: {}={}", "newItemCheckListID2", newItemChecklistId2);
		Assert.assertEquals(actualStatusCode, code);
	}



	@И("создать запрос отметки пункта чеклиста Card")
	public void createRequestCheckedItemCheckListCard(RequestModel requestModel) {
		apiRequest = new ApiRequest(requestModel);
	}

	@И("отметка  пункта чеклиста Card статус код {int}")
	public void expectStatusCodeCheckedItemCheckListCard(int code) {

		int actualStatusCode = apiRequest.getResponse().statusCode();
		Assert.assertEquals(actualStatusCode, code);
	}

	@И("создать запрос добавления колонки Card")
	public void createRequestAddNewColumnCard(RequestModel requestModel) {
		apiRequest = new ApiRequest(requestModel);
	}

	@И("создание новой колонки Card статус код {int}")
	public void expectStatusCodeAddNewColumnCard(int code) {
		int actualStatusCode = apiRequest.getResponse().statusCode();
		String responseBody = apiRequest.getResponse().body().asPrettyString();
		newColumnId = VariableUtil.extractBrackets(getFieldFromJson(responseBody, "id"));
		ContextHolder.put("newColumnId", newColumnId);
		LOG.info("Сохранена переменная: {}={}", "newColumnId", newColumnId);
		Assert.assertEquals(actualStatusCode, code);
	}

	@И("создать запрос перемещение карточки в новую колонку Board")
	public void createRequestMoveCardToNewColumn(RequestModel requestModel) {
		try {
			//Read from file
			String jsonn = new String(Files.readAllBytes(Paths.get("E:/ANTARA04/SCHOOL2/06/autotest-rest/src/test/resources/json/moveCardToColumn.json")));
			//Read from String
			JsonValue value = Json.parse(jsonn);
			value.asObject().set("column_id", newColumnId);
			//Debug
			System.out.println(value.toString());
			//Write to file
			PrintWriter writer = new PrintWriter("E:/ANTARA04/SCHOOL2/06/autotest-rest/src/test/resources/json/moveCardToColumn.json", "UTF-8");
			writer.println(value.toString());
			writer.close();
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}

		apiRequest = new ApiRequest(requestModel);
	}

	@И("перемещение карточки в новую колонку Board статус код {int}")
	public void expectStatusCodeMoveCardInNewColumn(int code) {

		int actualStatusCode = apiRequest.getResponse().statusCode();
		Assert.assertEquals(actualStatusCode, code);
	}

	@И("создать запрос перемещение карточки в архив Board")
	public void  createRequestMoveCardArchive(RequestModel requestModel) {
		apiRequest = new ApiRequest(requestModel);
	}

	@И("перемещение карточки в архив Board статус код {int}")
	public void expectStatusCodeMoveCardToArchive(int code) {

		int actualStatusCode = apiRequest.getResponse().statusCode();
		Assert.assertEquals(actualStatusCode, code);
	}

	@И("добавить файл в Card")
	public void addFileToCard(DataTable dataTable) {
		Set<String> files = new HashSet<>();
		dataTable.asLists().forEach(addFiles -> files.add(addFiles.get(0)));
		apiRequest.addFiles(files);

	}
}
