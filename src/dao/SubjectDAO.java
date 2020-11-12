package dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import domain.Disciplina;
import util.Conexao;

public class SubjectDAO {

	public static void create(Disciplina d) {
		Connection c = Conexao.conn();

		try {
			String sql = "insert into disciplina (nome , carga_horaria , ativo) values (?,?,?)";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, d.getNome());
			ps.setInt(2, d.getCargaHoraria());
			ps.setString(3, d.isAtivo()?"S":"N");
			ps.executeUpdate();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public static void update(Disciplina d) {
		Connection c = Conexao.conn();

		try {
			String sql = "update disciplina set nome=?, carga_horaria=?, ativo=? where id=?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, d.getNome());
			ps.setInt(2, d.getCargaHoraria());
			ps.setString(3, d.isAtivo()?"S":"N");
			ps.setInt(4, d.getId());
			ps.executeUpdate();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static ArrayList<Disciplina> all(){
		return all(false);
	}

	public static ArrayList<Disciplina> all(boolean isActive){
		Connection c = Conexao.conn();
		ArrayList<Disciplina> lista = new ArrayList<Disciplina>();
		try {
			String sql = "select * from disciplina order by nome";
			if( isActive)
				sql = "select * from disciplina where ativo = 'S' order by nome";

			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Disciplina d = new Disciplina();
				d.setId(rs.getInt("id"));
				d.setNome(rs.getString("nome"));
				d.setCargaHoraria(rs.getInt("carga_horaria"));
				d.setAtivo(rs.getString("isActive").equals("S")?true:false);
				lista.add(d);
			}
			c.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	public static ArrayList<Disciplina> filter(String filtro) {
		Connection c = Conexao.conn();
		ArrayList<Disciplina> lista = new ArrayList<Disciplina>();
		try {
			String sql = "select * from disciplina where nome like "+filtro+" order by nome";
			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Disciplina d = new Disciplina();
				d.setId(rs.getInt("id"));
				d.setNome(rs.getString("nome"));
				d.setCargaHoraria(rs.getInt("carga_horaria"));
				d.setAtivo(rs.getString("ativo").equals("S")?true:false);
				lista.add(d);
			}
			c.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	public static Disciplina buscaPorId(int id, boolean ativo){
		Connection c = Conexao.conn();
		Disciplina d = new Disciplina();
		try {
			String sql = "select * from disciplina where id =?";
			if(ativo)
				 sql = "select * from disciplina where id =?and ativo = 'S'";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				d.setId(rs.getInt("id"));
				d.setNome(rs.getString("nome"));
				d.setCargaHoraria(rs.getInt("carga_horaria"));
				d.setAtivo(rs.getString("ativo").equals("S")?true:false);
			}
			c.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return d;
	}

}
