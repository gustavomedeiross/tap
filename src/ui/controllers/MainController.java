package ui.controllers;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class MainController {
	@FXML
	private Label dateLabel;

	@FXML
	private TabPane tabPane;

	public void initialize() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date data = new Date();
		 dateLabel.setText("Data Atual: " + df.format(data));
	 }

	@FXML
	void subjectPage() {
		openTab("Disciplinas", "/ui/pages/Subject.fxml");
	}

	@FXML
	void teacherPage() {
		openTab("Professores", "/ui/pages/Teacher.fxml");
	}

	@FXML
	void allocationPage() {
		openTab("Alocação", "/ui/pages/Allocation.fxml");
	}

	@FXML
	void studentPage() {
		openTab("Alunos", "/ui/pages/Student.fxml");
	}

	@FXML
	void enrollmentPage() {
		openTab("matrícula", "/ui/pages/Enrollment.fxml");
	}

	@FXML
	void gradeHistoryPage() {
		openTab("histórico de notas", "/ui/pages/GradeHistory.fxml");
	}

	@FXML
	void reportsPage() {
		openTab("Relatórios", "/ui/pages/Reports.fxml");
	}

	private void openTab(String title, String path) {
		try {
			Tab tab = openedTab(title);
			if (tab==null) {
				tab = new Tab(title);
				tab.setClosable(true);
				tabPane.getTabs().add(tab);
				FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
				Parent root = loader.load();
				tab.setContent((Node) root);
			}
			selectTab(tab);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private Tab openedTab(String title) {
		for (Tab tab : tabPane.getTabs()) {
			if(!(tab.getText()==null) && tab.getText().equals(title))
				return tab;
		}
		return null;
	}

	private void selectTab(Tab tab) {
		tabPane.getSelectionModel().select(tab);
	}
}
