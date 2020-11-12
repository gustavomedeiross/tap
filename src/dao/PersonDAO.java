package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import domain.Pessoa;
import util.Conexao;

public class PersonDAO {
	public static void create(Pessoa p) {
		Connection c = Conexao.conn();

		try {
			String sql = "insert into Pessoa (nome , sexo , email , telefone , nascimento , tipo , ativo) "
					+ "values (?,?,?,?,?,?,?)";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, p.getNome());
			ps.setString(2, p.getSexo());
			ps.setString(3, p.getEmail());
			ps.setString(4, p.getTelefone());
			ps.setString(5, p.getNascimento());
			ps.setString(6, p.getTipo());
			ps.setString(7, p.isAtivo()?"S": "N");
			ps.executeUpdate();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static void update(Pessoa p) {
		Connection c = Conexao.conn();
		
		try {
			String sql = "update Pessoa set nome=?, sexo=?, email=?, telefone=?, "
					+ "nascimento=?, tipo=? ativo=? where id=?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, p.getNome());
			ps.setString(2, p.getSexo());
			ps.setString(3, p.getEmail());
			ps.setString(4, p.getTelefone());
			ps.setString(5, p.getNascimento());
			ps.setString(6, p.getTipo());
			ps.setString(7, p.isAtivo()?"S": "N");
			ps.setInt(8, p.getId());
			ps.executeUpdate();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static ArrayList<Pessoa> all(String tipo){
		return all(tipo,false);
		
	}
	public static ArrayList<Pessoa> all(String type, Boolean isActive){
		Connection c = Conexao.conn();
		ArrayList<Pessoa> lista = new ArrayList<Pessoa>();
		try {
			String sql = "select * from Pessoa where tipo=?  order by nome";
			if(isActive)
				sql = "select * from Pessoa where tipo=? and ativo ='S'  order by nome";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, type);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Pessoa p = new Pessoa();
				p.setId(rs.getInt("id"));
				p.setNome(rs.getString("nome"));
				p.setSexo(rs.getString("Sexo"));
				p.setEmail(rs.getString("email"));
				p.setTelefone(rs.getString("telefone"));
				p.setNascimento(rs.getString("nascimento"));
				p.setTipo(type);
				p.setAtivo(rs.getString("isActive").equals("S")?true:false);
				lista.add(p);
			}
			c.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	public static ArrayList<Pessoa> filter(String type, String filter) {
		Connection c = Conexao.conn();
		ArrayList<Pessoa> lista = new ArrayList<Pessoa>();
		try {
			String sql = "select * from Pessoa where tipo=? and nome like '"+filter+"' order by nome";
			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Pessoa p = new Pessoa();
				p.setId(rs.getInt("id"));
				p.setNome(rs.getString("nome"));
				p.setSexo(rs.getString("Sexo"));
				p.setEmail(rs.getString("email"));
				p.setTelefone(rs.getString("telefone"));
				p.setNascimento(rs.getString("nascimento"));
				p.setTipo(type);
				p.setAtivo(rs.getString("ativo").equals("S")?true:false);
				lista.add(p);
			}
			c.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	public static Pessoa findById(int id){
		Pessoa p = new Pessoa();
		Connection c = Conexao.conn();
		try {
			String sql = "select * from pessoa where id = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if(rs.next()) {
				p.setId(rs.getInt("id"));
				p.setNome(rs.getString("nome"));
				p.setSexo(rs.getString("Sexo"));
				p.setEmail(rs.getString("email"));
				p.setTelefone(rs.getString("telefone"));
				p.setNascimento(rs.getString("nascimento"));
				p.setTipo(rs.getString("tipo"));
				p.setAtivo(rs.getString("ativo").equals("S"));
			}
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}
}
