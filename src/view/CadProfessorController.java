package view;




import java.util.ArrayList;
import dao.PessoaDAO;
import domain.Pessoa;
import domain.Professor;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CadProfessorController {

	@FXML TextField txtNome;
	@FXML TextField txtEmail;
	@FXML TextField txtTelefone;
	@FXML TextField txtFiltro;
	@FXML TextField txtNascimento;
	@FXML RadioButton rdMasc;
	@FXML RadioButton rdFemi;
	@FXML CheckBox ckAtivo;
	@FXML TableView<Pessoa> tbl;
	@FXML TableColumn<Pessoa, String> colNome;
	@FXML TableColumn<Pessoa, String> colTelefone;
	@FXML TableColumn<Pessoa, String> colEmail;

	private ArrayList<Pessoa> listaProfessores;
	private Pessoa p;

	@FXML
	public void initialize() {
		colNome.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
		colTelefone.setCellValueFactory(cellData -> cellData.getValue().telefoneProperty());
		colEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
		mostraTodosProfessores();
	}

	@FXML
	public void gravar() {
		if(p == null) {
			p = new Professor();
			p.setNome(txtNome.getText());
			p.setNascimento(txtNascimento.getText());
			p.setSexo(rdMasc.isSelected()?"M":"F");
			p.setEmail(txtEmail.getText());
			p.setTelefone(txtTelefone.getText());
			p.setAtivo(ckAtivo.isSelected());
			PessoaDAO.novaPessoa(p);
			p = null;
			mostraTodosProfessores();;
		}else {
			Professor altera = new Professor();
			altera.setId(p.getId());
			altera.setNome(txtNome.getText());
			p.setNascimento(txtNascimento.getText());
			altera.setSexo(rdMasc.isSelected()?"M":"F");
			altera.setEmail(txtEmail.getText());
			altera.setTelefone(txtTelefone.getText());
			p.setNascimento(txtNascimento.getPromptText());
			altera.setAtivo(ckAtivo.isSelected());
			PessoaDAO.alteraPessoa(altera);
			p = null;
			mostraTodosProfessores();
		}

	}


	@FXML
	public void clicouTbl() {
		p = tbl.getSelectionModel().getSelectedItem();
		if(p != null) {
			txtNome.setText(p.getNome());
			txtEmail.setText(p.getEmail());
			txtTelefone.setText(p.getTelefone());
			txtNascimento.setPromptText(p.getNascimento());
			if(p.getSexo().equals("M")) {
				rdMasc.setSelected(true);
				rdFemi.setSelected(false);
			}else {
				rdMasc.setSelected(false);
				rdFemi.setSelected(true);
			}
			ckAtivo.setSelected(p.isAtivo());

		}
	}

	private void mostraTodosProfessores() {
		listaProfessores = PessoaDAO.listaTodas("P");
		tbl.setItems(FXCollections.observableArrayList(listaProfessores));
	}

	@FXML
	public void filtrar() {
		listaProfessores = PessoaDAO.filtra("P",txtFiltro.getText());
		tbl.setItems(FXCollections.observableArrayList(listaProfessores));
	}






}
    