package steps;

import actions.WebChecks;
import com.codeborne.selenide.SelenideElement;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.lanit.at.web.pagecontext.PageManager;

import java.util.List;

public class WebCheckSteps {

	private PageManager pageManager;
	private static final Logger LOGGER = LoggerFactory.getLogger(WebCheckSteps.class);

	public WebCheckSteps(PageManager pageManager) {
		this.pageManager = pageManager;
	}

	/**
	 * проверка присутствия текста на странице
	 *
	 * @param text текст
	 */
	@Когда("на странице присутствует текст {string}")
	public void textAppearOnThePage(String text) {
		WebChecks.textVisibleOnPage(text, null);
		LOGGER.info("на странице '{}' имеется текст '{}'", pageManager.getCurrentPage().name(), text);
	}

	/**
	 * проверка отсутствия текста на странице
	 *
	 * @param text текст
	 */
	@Когда("на странице отсутствует текст {string}")
	public void textVisibleOnPage(String text) {
		WebChecks.textAbsentOnPage(text, null);
		LOGGER.info("на странице '{}' отсутствует текст '{}'", pageManager.getCurrentPage().name(), text);
	}

	/**
	 * ожидание появления текста на странице в течении некоторого времени
	 *
	 * @param text           текст
	 * @param timeoutSeconds количество секунд
	 */
	@Когда("подождать появления текста {string} в течение {int} секунд")
	public void waitUntilTextAppearOnPage(String text, int timeoutSeconds) {
		WebChecks.textVisibleOnPage(text, timeoutSeconds);
		LOGGER.info("на странице '{}' имеется текст '{}'", pageManager.getCurrentPage().name(), text);
	}

	/**
	 * ожидание исчезновения текста на странице в течении некоторого времени
	 *
	 * @param text           текст
	 * @param timeoutSeconds количество секунд
	 */
	@Когда("подождать исчезновения текста {string} в течение {int} секунд")
	public void waitUntilTextAbsentOnPage(String text, int timeoutSeconds) {
		WebChecks.textAbsentOnPage(text, timeoutSeconds);
		LOGGER.info("на странице '{}' отсутствует текст '{}'", pageManager.getCurrentPage().name(), text);
	}

	/**
	 * ожидание элемента на странице в течении некоторого времени
	 *
	 * @param elementName    наименование элемента
	 * @param timeoutSeconds количество секунд
	 */
	@Когда("подождать появления элемента {string} в течение {int} секунд")
	public void waitUntilElementIsVisibleOnPage(String elementName, int timeoutSeconds) {
		SelenideElement element = pageManager
				.getCurrentPage()
				.getElement(elementName);
		WebChecks.elementVisibleOnPage(element, timeoutSeconds);
		LOGGER.info("на странице '{}' имеется элемент '{}'", pageManager.getCurrentPage().name(), elementName);
	}

	/**
	 * проверка что на странице отображен элемент
	 *
	 * @param elementName наименование элемента
	 */
	@Когда("на странице имеется элемент {string}")
	public void elementAppearOnThePage(String elementName) {
		SelenideElement element = pageManager
				.getCurrentPage()
				.getElement(elementName);
		WebChecks.elementVisibleOnPage(element, null);
		LOGGER.info("на странице '{}' имеется элемент '{}'", pageManager.getCurrentPage().name(), elementName);
	}

	@Когда("проверить отметку {string}")
	public void elementCheckedItemsInCheckbox(String elementName) {

		SelenideElement element = pageManager
				.getCurrentPage()
				.getElement(elementName);

		List<WebElement> selenideElementList = element.findElements(By.xpath("//div[@data-test='checklist-item']//input[@type='checkbox']"));
		for (WebElement e : selenideElementList) {
			if (e.isSelected()) {
				LOGGER.info("пункт чеклиста имеет отметку ");

			}
		}
	}


	/**
	 * проверка что на странице отсуствует элемент
	 *
	 * @param elementName наименование элемента
	 */
	@Когда("на странице отсутствует элемент {string}")
	public void elementAbsentOnPage(String elementName) {
		SelenideElement element = pageManager
				.getCurrentPage()
				.getElement(elementName);
		WebChecks.elementAbsentOnPage(element, null);
		LOGGER.info("на странице '{}' отсутствует элемент '{}'", pageManager.getCurrentPage().name(), elementName);
	}

	/**
	 * проверка текущего url
	 * <br/>можно начать написание url с переменной %{apiUrl}% или %{webUrl}%
	 *
	 * @param url часть или полный url (также может содержать переменные)
	 */
	@Тогда("проверить что текущий url соответствует {string}")
	public void currentUrlEqualsExpected(String url) {
		WebChecks.urlEquals(url);
	}

	/**
	 * проверка текущего url
	 * <br/>можно начать написание url с переменной %{apiUrl}% или %{webUrl}%
	 *
	 * @param url часть url (также может содержать переменные)
	 */
	@Тогда("проверить что текущий url содержит текст {string}")
	public void currentUrlContainsExpected(String url) {
		WebChecks.urlContains(url);
	}
}
