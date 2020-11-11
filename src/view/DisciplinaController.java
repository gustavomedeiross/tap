package view;

import java.util.ArrayList;

import dao.DisciplinaDAO;
import domain.Disciplina;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class DisciplinaController {

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtCarga;

	@FXML
	private CheckBox ckAtiva;

	@FXML
	private TextField txtFiltrar;

	@FXML
	private TableView<Disciplina> tbl;

	@FXML
	private TableColumn<Disciplina, String> colNome;

	@FXML
	private TableColumn<Disciplina, Number> colCarga;

	@FXML
	private TableColumn<Disciplina, Boolean> colAtiva;
	private ArrayList<Disciplina> listaDisciplinas;
	private Disciplina d;



	@FXML
	public void initialize() {
		colNome.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
		colCarga.setCellValueFactory(cellData -> cellData.getValue().cargaHorariaProperty());
		colAtiva.setCellValueFactory(cellData -> cellData.getValue().ativoProperty());
		mostraTodasDisciplinas();
	}

	@FXML
	void Excluir() {

	}

	@FXML
	void Gravar() {
		if(d == null) {
			Disciplina d = new Disciplina();
			d.setNome(txtNome.getText());
			d.setCargaHoraria(Integer.parseInt(txtCarga.getText()));
			d.setAtivo(ckAtiva.isSelected());
			DisciplinaDAO.novaDisciplina(d);
			d = null;
			mostraTodasDisciplinas();
			limpaTela();
		}else {
			Disciplina altera = new Disciplina();
			altera.setId(d.getId());
			altera.setNome(txtNome.getText());
			altera.setCargaHoraria(Integer.parseInt(txtCarga.getText()));
			altera.setAtivo(ckAtiva.isSelected());
			DisciplinaDAO.alteraDisciplina(altera);
			d = null;
			mostraTodasDisciplinas();
			limpaTela();
		}

	}

	@FXML
	void clicouTbl() {
		d = tbl.getSelectionModel().getSelectedItem();
		if(d != null) {
			txtNome.setText(d.getNome());
			txtCarga.setText(d.getCargaHoraria()+"");
			ckAtiva.setSelected(d.isAtivo());

		}

	}

	@FXML
	void filtrar() {
		listaDisciplinas = DisciplinaDAO.filtra(txtFiltrar.getText());
		tbl.setItems(FXCollections.observableArrayList(listaDisciplinas));

	}

	private void limpaTela() {
		txtNome.setText("");
		txtCarga.setText("");
		ckAtiva.setSelected(false);
	}

	private void mostraTodasDisciplinas() {
		listaDisciplinas = DisciplinaDAO.listaTodas();
		tbl.setItems(FXCollections.observableArrayList(listaDisciplinas));
	}



}