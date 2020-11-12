package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import domain.Subject;

public class SubjectDAO {
	public static void create(Subject d) {
		java.sql.Connection c = ConnectionWrapper.conn();

		try {
			String sql = "insert into disciplina (nome , carga_horaria , ativo) values (?,?,?)";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, d.getName());
			ps.setInt(2, d.getWorkload());
			ps.setString(3, d.getIsActive()?"S":"N");
			ps.executeUpdate();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void update(Subject d) {
		java.sql.Connection c = ConnectionWrapper.conn();
		try {
			String sql = "update disciplina set nome=?, carga_horaria=?, ativo=? where id=?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, d.getName());
			ps.setInt(2, d.getWorkload());
			ps.setString(3, d.getIsActive()?"S":"N");
			ps.setInt(4, d.getId());
			ps.executeUpdate();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Subject> all(){
		return all(false);
	}

	public static ArrayList<Subject> all(boolean isActive){
		java.sql.Connection c = ConnectionWrapper.conn();
		ArrayList<Subject> lista = new ArrayList<Subject>();
		try {
			String sql = "select * from disciplina order by nome";
			if( isActive)
				sql = "select * from disciplina where ativo = 'S' order by nome";

			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Subject d = new Subject();
				d.setId(rs.getInt("id"));
				d.setName(rs.getString("nome"));
				d.setWorkload(rs.getInt("carga_horaria"));
				d.setIsActive(rs.getString("isActive").equals("S")?true:false);
				lista.add(d);
			}
			c.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	public static ArrayList<Subject> filter(String filtro) {
		java.sql.Connection c = ConnectionWrapper.conn();
		ArrayList<Subject> subjects = new ArrayList<Subject>();
		try {
			String sql = "select * from disciplina where nome like "+filtro+" order by nome";
			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Subject d = new Subject();
				d.setId(rs.getInt("id"));
				d.setName(rs.getString("nome"));
				d.setWorkload(rs.getInt("carga_horaria"));
				d.setIsActive(rs.getString("ativo").equals("S"));
				subjects.add(d);
			}
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subjects;
	}

	public static Subject findById(int id, boolean isActive) {
		java.sql.Connection connection = ConnectionWrapper.conn();
		Subject subject = new Subject();
		try {
		    String sql;

			if (isActive) {
				sql = "select * from disciplina where id =?and ativo = 'S'";
			} else {
				sql = "select * from disciplina where id =?";
			}

			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				subject.setId(rs.getInt("id"));
				subject.setName(rs.getString("nome"));
				subject.setWorkload(rs.getInt("carga_horaria"));
				subject.setIsActive(rs.getString("isActive").equals("S"));
			}
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subject;
	}
}
