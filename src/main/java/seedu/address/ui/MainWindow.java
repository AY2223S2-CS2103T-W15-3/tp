package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ReviewCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    private PersonListPanel personListPanel;
    private TaskListPanel taskListPanel;
    private PersonStatsListPanel personStatsListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane listPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private Label panelLabel;

    @FXML
    private CheckMenuItem light;

    @FXML CheckMenuItem dark;

    @FXML
    private Menu menuButton;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    public void setTheme() {
        dark.setSelected(true);
        light.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            String currentStyleSheet = primaryStage.getScene().getStylesheets().get(0);
            String newStyleSheet;
            if (isSelected) {
                newStyleSheet = "view/LightTheme.css";
                dark.setSelected(false);
            } else {
                newStyleSheet = currentStyleSheet;
            }
            primaryStage.getScene().getStylesheets().remove(currentStyleSheet);
            primaryStage.getScene().getStylesheets().add(newStyleSheet);
        });
        dark.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            String currentStyleSheet = primaryStage.getScene().getStylesheets().get(0);
            String newStyleSheet;
            if (isSelected) {
                newStyleSheet = "view/" + "DarkTheme.css";
                light.setSelected(false);
            } else {
                newStyleSheet = currentStyleSheet;
            }
            primaryStage.getScene().getStylesheets().remove(currentStyleSheet);
            primaryStage.getScene().getStylesheets().add(newStyleSheet);
        });
    }

    /**
     * Fills up all the tasks placeholders of this window.
     */
    void fillInnerParts() {
        taskListPanel = new TaskListPanel(logic.getFilteredTaskList());
        listPanelPlaceholder.getChildren().add(taskListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getTaskBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Replicates view task command
     * @throws CommandException
     * @throws ParseException
     */
    @FXML
    public void tasksClicked() throws CommandException, ParseException {
        CommandResult commandResult = executeCommand("view");
        show();
    }

    /**
     * Replicates list command
     * @throws CommandException
     * @throws ParseException
     */
    @FXML
    public void personsClicked() throws CommandException, ParseException {
        CommandResult commandResult = executeCommand("list");
        show();
    }

    /**
     * Replicates review command
     * @throws CommandException
     * @throws ParseException
     */
    @FXML
    public void reviewClicked() throws CommandException, ParseException {
        CommandResult commandResult = executeCommand("review");
        show();
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }

    public TaskListPanel getTaskListPanel() {
        return taskListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            // TODO: refine later
            if (commandResult.getFeedbackToUser().equals(ListCommand.MESSAGE_SUCCESS)) {
                personListPanel = new PersonListPanel(logic.getFilteredPersonList());
                listPanelPlaceholder.getChildren().clear();
                listPanelPlaceholder.getChildren().add(personListPanel.getRoot());
                StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
                statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());
                panelLabel.setText("Persons");
            }

            if (commandResult.getFeedbackToUser().equals(ViewCommand.MESSAGE_SUCCESS)) {
                taskListPanel = new TaskListPanel(logic.getFilteredTaskList());
                listPanelPlaceholder.getChildren().clear();
                listPanelPlaceholder.getChildren().add(taskListPanel.getRoot());
                StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getTaskBookFilePath());
                statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());
                panelLabel.setText("Tasks");
            }

            if (commandResult.getFeedbackToUser().equals(ReviewCommand.MESSAGE_SUCCESS)) {
                personStatsListPanel = new PersonStatsListPanel(logic);
                listPanelPlaceholder.getChildren().clear();
                listPanelPlaceholder.getChildren().add(personStatsListPanel.getRoot());
                StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
                statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());
                panelLabel.setText("Review");
            }

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
