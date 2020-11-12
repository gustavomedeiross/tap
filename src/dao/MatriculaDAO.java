package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import domain.Aluno;
import domain.Disciplina;
import domain.Matricula;
import domain.Pessoa;
import util.Conexao;

public class MatriculaDAO {
	public static void novaMatricula(Matricula matricula) {
		Connection c = Conexao.conn();

		try {
			String sql = "insert into matricula (id_aluno, id_disciplina, semestre) values (?,?, ?)";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, matricula.getAluno().getId());
			ps.setInt(2, matricula.getDisciplina().getId());
			ps.setString(3, matricula.getSemestre());
			ps.executeUpdate();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static void excluirMatricula(Pessoa a,Disciplina d) {
		Connection c = Conexao.conn();

		try {
			String sql = "delete from matricula where id_aluno=? and id_disciplina=?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, a.getId());
			ps.setInt(2, d.getId());

			ps.executeUpdate();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Matricula> buscaPorAluno(Pessoa p){
		ArrayList<Matricula> lista = new ArrayList<Matricula>();
		Connection c = Conexao.conn();

		try {
			String sql = "select * from matricula where id_aluno=?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, p.getId());
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
			    Matricula m = new Matricula();

				int idDisc = rs.getInt("id_disciplina");
				Disciplina d = DisciplinaDAO.buscaPorId(idDisc, true);

				m.setDisciplina(d);
				m.setAluno(p);
				m.setSemestre(rs.getString("semestre"));
				lista.add(m);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	public static ArrayList<Matricula> buscaPorAlunoESemestre(Pessoa p, String semestre){
		ArrayList<Matricula> lista = new ArrayList<Matricula>();
		Connection c = Conexao.conn();

		try {
			String sql = "select * from matricula where id_aluno=? AND semestre = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, p.getId());
			ps.setString(2, semestre);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				Matricula m = new Matricula();

				int idDisc = rs.getInt("id_disciplina");
				Disciplina d = DisciplinaDAO.buscaPorId(idDisc, true);

				m.setDisciplina(d);
				m.setAluno(p);
				m.setSemestre(rs.getString("semestre"));
				lista.add(m);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
}
