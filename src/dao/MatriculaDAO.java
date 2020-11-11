package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import domain.Disciplina;
import domain.Pessoa;
import util.Conexao;

public class MatriculaDAO {
	public static void novaMatricula(Pessoa a,Disciplina d) {
		Connection c = Conexao.conn();

		try {
			String sql = "insert into matricula (id_aluno, id_disciplina) values (?,?)";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, a.getId());
			ps.setInt(2, d.getId());
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

	public static ArrayList<Disciplina> buscaPorAluno(Pessoa p){
		ArrayList<Disciplina> lista = new ArrayList<Disciplina>();
		Connection c = Conexao.conn();

		try {
			String sql = "select * from matricula where id_aluno=?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, p.getId());
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				int idDisc = rs.getInt("id_disciplina");
				lista.add(DisciplinaDAO.buscaPorId(idDisc, true));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

}
