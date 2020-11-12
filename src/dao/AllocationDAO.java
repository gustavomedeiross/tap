package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import domain.Disciplina;
import domain.Pessoa;
import util.Conexao;

public class AllocationDAO {
	
	public static void create(Pessoa p, Disciplina d) {
		Connection c = Conexao.conn();

		try {
			String sql = "insert into disciplina_professor (id_professor, id_disciplina) values (?,?)";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, p.getId());
			ps.setInt(2, d.getId());
		
			ps.executeUpdate();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void delete(Pessoa p, Disciplina d) {
		Connection c = Conexao.conn();

		try {
			String sql = "delete from disciplina_professor where id_professor=? and id_disciplina=?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, p.getId());
			ps.setInt(2, d.getId());
		
			ps.executeUpdate();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static ArrayList<Disciplina> findByTeacher(Pessoa p){
		ArrayList<Disciplina> lista = new ArrayList<Disciplina>();
		Connection c = Conexao.conn();
		try {
			String sql = "select * from disciplina_professor where id_professor=?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, p.getId());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int idDisc = rs.getInt("id_disciplina");
				lista.add(SubjectDAO.buscaPorId(idDisc, true));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
}
