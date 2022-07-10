package pages;

import com.codeborne.selenide.SelenideElement;
import ru.lanit.at.web.annotations.Name;
import ru.lanit.at.web.pagecontext.WebPage;

import static com.codeborne.selenide.Selenide.$x;

@Name(value = "Kaiten")
public class KaitenPage extends WebPage {

    @Name("поле ввода email")
    private SelenideElement emailField = $x("//input[@id='email_username']");

    @Name("выбор варианта входа")
    private SelenideElement choiceEnterBtn = $x("//p[@class='MuiTypography-root MuiTypography-body2']");

    @Name("поле ввода password")
    private SelenideElement passField = $x("//input[@id='password']");

    @Name("кнопка войти")
    private SelenideElement signinBtn = $x("//button[@type='submit']");

    @Name("колонка Done")
    private SelenideElement doneColumn = $x("//div[@data-test='column-title-text' and @title='Done']");

    @Name("карточка Карточка для изучения API")
    private SelenideElement workCard = $x("//a[@href='/space/67412/card/5631890']");

    @Name("чекбокс")
    private SelenideElement checkboxBtn = $x("//div[@data-test='facade-checklist-item']");

    @Name("пункты чекбокса")
    private SelenideElement itemscheckboxBtn = $x("//div[@data-test='checklist-item']//input[@type='checkbox']");

    @Name("контекстное меню")
    private SelenideElement contextMenu = $x("//div[@class='context_menu_control_container']");

    @Name("кнопка контекстного меню")
    private SelenideElement contextMenuButton = $x("//button[@data-test='card-context-menu']");

    @Name("добавить метки")
    private SelenideElement addNoteBtn = $x("//span[.='Добавить метки']/ancestor::" +
            "div[@class='MuiButtonBase-root MuiListItem-root MuiListItem-dense MuiListItem-gutters MuiListItem-button']");

    @Name("новой метки")
    private SelenideElement newNoteField = $x("//div[@class='MuiFormControl-root MuiTextField-root MuiFormControl-marginDense MuiFormControl-fullWidth']//input[@placeholder='Найти..']");

    @Name("создать метку")
    private SelenideElement addNewNoteBtn = $x("//h6[.='Создать метку']");

    @Name("добавить пространство")
    private SelenideElement addNewSpaceBtn = $x("//svg[@class='MuiSvgIcon-root MuiSvgIcon-fontSizeSmall']");

    @Name("главное меню")
    private SelenideElement mainMenu = $x("//button[@data-testid='menu-button']");

    @Name("зафиксировать панель")
    private SelenideElement fixPanel = $x("//span[@title='Зафиксировать панель']");

    @Name("лого значек")
    private SelenideElement logoPanel = $x("//h6[.='Kaiten']");

    @Name("меню Администрирование")
    private SelenideElement adminItemMenu = $x("//span[.='Администрирование']");

    @Name("меню Пространства")
    private SelenideElement spaceItemMenu = $x("//span[.='Пространства']");

    @Name("меню пространства ADM")
    private SelenideElement spaceItemAdmMenu = $x("//a[@href='/admin-spaces']//span[.='Пространства']");

    @Name("создать новое пространство")
    private SelenideElement addNewSpaceButton = $x("//span[.='Создать новое пространство']");

    @Name("новое пространство")
    private SelenideElement addNewSpaceField = $x("//input[@type='text' and @maxlength='256']");

    @Name("создать")
    private SelenideElement createNewSpaceButton = $x("//span[.='Создать']/ancestor::button");

    @Name("пространство Образование")
    private SelenideElement itemMenuSpace = $x("//span[.='Образование']");

    @Name("создать доску")
    private SelenideElement addNewBoardButton = $x("//span[.='Создать доску']");

    @Name("Простая доска")
    private SelenideElement typeNewBoardButton = $x("//span[.='Простая доска']");

    @Name("имя доски")
    private SelenideElement titleNewBoardField = $x("//div[@data-test='board-title']");

    @Name("три точки")
    private SelenideElement contextMenuNewBoard = $x("//button[@data-test='board-context-menu-button']");

    @Name("Переименовать")
    private SelenideElement renameMenuNewBoard = $x("//span[.='Переименовать']");

    @Name("название доски")
    private SelenideElement renameMenuNewBoardField = $x("//input[@class='MuiInputBase-input MuiInput-input MuiInputBase-inputAdornedEnd MuiInputBase-inputMarginDense MuiInput-inputMarginDense']");

    @Name("сохранить")
    private SelenideElement saveNameNewBoard = $x("//button[@data-testid='save-title']");
}
