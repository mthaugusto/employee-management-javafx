package com.matheus.leite;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import com.matheus.leite.data.EmployeeDao;
import com.matheus.leite.model.Employee;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class DashboardController {

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button addEmployee_addBtn;

    @FXML
    private Button addEmployee_btn;

    @FXML
    private Button addEmployee_clearBtn;

    @FXML
    private TableColumn<Employee, Date> addEmployee_col_date;

    @FXML
    private TableColumn<Employee, Integer> addEmployee_col_employeeID;

    @FXML
    private TableColumn<Employee, String> addEmployee_col_firstName;

    @FXML
    private TableColumn<Employee, String> addEmployee_col_gender;

    @FXML
    private TableColumn<Employee, String> addEmployee_col_lastName;

    @FXML
    private TableColumn<Employee, String> addEmployee_col_phoneNum;

    @FXML
    private TableColumn<Employee, String> addEmployee_col_position;

    @FXML
    private Button addEmployee_deleteBtn;

    @FXML
    private TextField addEmployee_employeeID;

    @FXML
    private TextField addEmployee_firstName;

    @FXML
    private AnchorPane addEmployee_form;

    @FXML
    private ComboBox<?> addEmployee_gender;

    @FXML
    private ImageView addEmployee_image;

    @FXML
    private Button addEmployee_importBtn;

    @FXML
    private TextField addEmployee_lastName;

    @FXML
    private TextField addEmployee_phoneNum;

    @FXML
    private ComboBox<?> addEmployee_position;

    @FXML
    private TextField addEmployee_search;

    @FXML
    private TableView<Employee> addEmployee_tableView;

    @FXML
    private Button addEmployee_updateBtn;

    @FXML
    private Button home_btn;

    @FXML
    private BarChart<?, ?> home_chart;

    @FXML
    private AnchorPane home_form;

    @FXML
    private Label home_totalEmployees;

    @FXML
    private Label home_totalInactiveEm;

    @FXML
    private Label home_totalPresents;

    @FXML
    private Button logout;

    @FXML
    private Button salary_btn;

    @FXML
    private Button salary_clearBtn;

    @FXML
    private TableColumn<?, ?> salary_col_employeeID;

    @FXML
    private TableColumn<?, ?> salary_col_firstName;

    @FXML
    private TableColumn<?, ?> salary_col_lastName;

    @FXML
    private TableColumn<?, ?> salary_col_position;

    @FXML
    private TableColumn<?, ?> salary_col_salary;

    @FXML
    private TextField salary_employeeID;

    @FXML
    private Label salary_firstName;

    @FXML
    private AnchorPane salary_form;

    @FXML
    private Label salary_lastName;

    @FXML
    private Label salary_position;

    @FXML
    private TextField salary_salary;

    @FXML
    private TableView<?> salary_tableView;

    @FXML
    private Button salary_updateBtn;

    @FXML
    private Label usernameLabel;

    private ObservableList<Employee> employeeList;

    private Image image;

    private EmployeeDao employeeDao;

    public void initialize() throws SQLException {
        addEmployeeShowListData();
        employeeDao = new EmployeeDao();
    }

    public void closeWindow() {
        System.exit(0);
    }

    public void logout() throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setContentText("Are you sure you want to logout?");
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK) {
            App.setRoot((Stage) logout.getScene().getWindow(), "primary", 600, 400);
        }
    }

    public void displayUsername(String user) {
        usernameLabel.setText(user);
    }

    public void addEmployeeShowListData() throws SQLException {
        var employeeDao = new EmployeeDao();
        employeeList = employeeDao.listEmployees();

        addEmployee_col_employeeID.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        addEmployee_col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        addEmployee_col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        addEmployee_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        addEmployee_col_phoneNum.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        addEmployee_col_position.setCellValueFactory(new PropertyValueFactory<>("position"));
        addEmployee_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        addEmployee_tableView.setItems(employeeList);
    }

    // TO DO
    // public void addEmployeeInsertImage() {

    // FileChooser open = new FileChooser();
    // File file = open.showOpenDialog(main_form.getScene().getWindow());

    // if (file != null) {
    // //getData.path = file.getAbsolutePath();

    // image = new Image(file.toURI().toString(), 106, 139, false, true);
    // addEmployee_image.setImage(image);

    // }
    // }

    public void addEmployee() {

        String firstName = addEmployee_firstName.getText();
        String lastName = addEmployee_lastName.getText();
        String gender = (String) addEmployee_gender.getSelectionModel().getSelectedItem();
        String phoneNum = addEmployee_phoneNum.getText();
        String position = (String) addEmployee_position.getSelectionModel().getSelectedItem();
        String image = null;
        LocalDate currentDate = LocalDate.now();
        Date date = Date.valueOf(currentDate);

        try {
            employeeDao.createEmployee(firstName, lastName, gender, phoneNum, position, image, date);
            addEmployeeShowListData();

            // Role para o último item adicionado
            int lastIndex = addEmployee_tableView.getItems().size() - 1;
            addEmployee_tableView.scrollTo(lastIndex);
        } catch (SQLException e) {
            e.printStackTrace(); // Lide com o erro apropriadamente
        }
    }

    public void addEmployeeSelect() {
        Employee employee = addEmployee_tableView.getSelectionModel().getSelectedItem();
        int num = addEmployee_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        addEmployee_employeeID.setText(String.valueOf(employee.getEmployeeId()));
        addEmployee_firstName.setText(employee.getFirstName());
        addEmployee_lastName.setText(employee.getLastName());
        addEmployee_phoneNum.setText(employee.getPhoneNum());

        String uri = "file:" + employee.getImage();
        image = new Image(uri, 106, 139, false, true);
        addEmployee_image.setImage(image);

    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == home_btn) {
            home_form.setVisible(true);
            addEmployee_form.setVisible(false);
            salary_form.setVisible(false);

            home_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #3a4368, #525968);");
            addEmployee_btn.setStyle("-fx-background-color: transparent;");
            salary_btn.setStyle("-fx-background-color: transparent;");

        } else if (event.getSource() == addEmployee_btn) {
            home_form.setVisible(false);
            addEmployee_form.setVisible(true);
            salary_form.setVisible(false);

            addEmployee_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #3a4368, #525968);");
            home_btn.setStyle("-fx-background-color: transparent;");
            salary_btn.setStyle("-fx-background-color: transparent;");

        } else if (event.getSource() == salary_btn) {
            home_form.setVisible(false);
            addEmployee_form.setVisible(false);
            salary_form.setVisible(true);

            salary_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #3a4368, #525968);");
            addEmployee_btn.setStyle("-fx-background-color: transparent;");
            home_btn.setStyle("-fx-background-color: transparent;");
        }
    }

}
