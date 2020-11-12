package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import domain.Allocation;
import domain.Subject;
import domain.Person;

public class AllocationDAO {
	public static void create(Allocation allocation) {
		Connection c = ConnectionWrapper.conn();

		try {
			String sql = "insert into disciplina_professor (id_professor, id_disciplina) values (?,?)";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, allocation.getTeacher().getId());
			ps.setInt(2, allocation.getSubject().getId());
			ps.executeUpdate();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void delete(Allocation allocation) {
		java.sql.Connection c = ConnectionWrapper.conn();
		try {
			String sql = "delete from disciplina_professor where id_professor=? and id_disciplina=?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, allocation.getTeacher().getId());
			ps.setInt(2, allocation.getSubject().getId());
			ps.executeUpdate();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Subject> findByTeacher(Person p) {
		ArrayList<Subject> subjects = new ArrayList<Subject>();
		java.sql.Connection c = ConnectionWrapper.conn();
		try {
			String sql = "select * from disciplina_professor where id_professor=?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, p.getId());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int idDisc = rs.getInt("id_disciplina");
				subjects.add(SubjectDAO.findById(idDisc, true));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subjects;
	}
}
