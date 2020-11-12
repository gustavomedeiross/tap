package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import domain.Person;

public class PersonDAO {
	public static void create(Person p) {
		java.sql.Connection c = ConnectionWrapper.conn();

		try {
			String sql = "insert into Pessoa (nome , sexo , email , telefone , nascimento , tipo , ativo) "
					+ "values (?,?,?,?,?,?,?)";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, p.getName());
			ps.setString(2, p.getGender());
			ps.setString(3, p.getEmail());
			ps.setString(4, p.getPhone());
			ps.setString(5, p.getBirthDate());
			ps.setString(6, p.getType());
			ps.setString(7, p.getIsActive()?"S": "N");
			ps.executeUpdate();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static void update(Person p) {
		java.sql.Connection c = ConnectionWrapper.conn();
		
		try {
			String sql = "update Pessoa set nome=?, sexo=?, email=?, telefone=?, "
					+ "nascimento=?, tipo=? ativo=? where id=?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, p.getName());
			ps.setString(2, p.getGender());
			ps.setString(3, p.getEmail());
			ps.setString(4, p.getPhone());
			ps.setString(5, p.getBirthDate());
			ps.setString(6, p.getType());
			ps.setString(7, p.getIsActive()?"S": "N");
			ps.setInt(8, p.getId());
			ps.executeUpdate();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static ArrayList<Person> all(String tipo){
		return all(tipo,false);
		
	}
	public static ArrayList<Person> all(String type, Boolean isActive){
		java.sql.Connection c = ConnectionWrapper.conn();
		ArrayList<Person> lista = new ArrayList<Person>();
		try {
			String sql = "select * from Pessoa where tipo=?  order by nome";
			if(isActive)
				sql = "select * from Pessoa where tipo=? and ativo ='S'  order by nome";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, type);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Person p = new Person();
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("nome"));
				p.setGender(rs.getString("Sexo"));
				p.setEmail(rs.getString("email"));
				p.setPhone(rs.getString("telefone"));
				p.setBirthDate(rs.getString("nascimento"));
				p.setType(type);
				p.setIsActive(rs.getString("isActive").equals("S")?true:false);
				lista.add(p);
			}
			c.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	public static ArrayList<Person> filter(String type, String filter) {
		java.sql.Connection c = ConnectionWrapper.conn();
		ArrayList<Person> lista = new ArrayList<Person>();
		try {
			String sql = "select * from Pessoa where tipo=? and nome like '"+filter+"' order by nome";
			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Person p = new Person();
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("nome"));
				p.setGender(rs.getString("Sexo"));
				p.setEmail(rs.getString("email"));
				p.setPhone(rs.getString("telefone"));
				p.setBirthDate(rs.getString("nascimento"));
				p.setType(type);
				p.setIsActive(rs.getString("ativo").equals("S")?true:false);
				lista.add(p);
			}
			c.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	public static Person findById(int id){
		Person p = new Person();
		java.sql.Connection c = ConnectionWrapper.conn();
		try {
			String sql = "select * from pessoa where id = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if(rs.next()) {
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("nome"));
				p.setGender(rs.getString("Sexo"));
				p.setEmail(rs.getString("email"));
				p.setPhone(rs.getString("telefone"));
				p.setBirthDate(rs.getString("nascimento"));
				p.setType(rs.getString("tipo"));
				p.setIsActive(rs.getString("ativo").equals("S"));
			}
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}
}
