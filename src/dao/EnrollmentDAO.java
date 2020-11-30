package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import domain.Subject;
import domain.Enrollment;
import domain.Person;

public class EnrollmentDAO {
	public static void create(Enrollment matricula) {
		java.sql.Connection c = ConnectionWrapper.conn();

		try {
			String sql = "insert into matricula (id_aluno, id_disciplina, semestre) values (?,?, ?)";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, matricula.getStudent().getId());
			ps.setInt(2, matricula.getSubject().getId());
			ps.setString(3, matricula.getSemester());
			ps.executeUpdate();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void delete(Enrollment m) {
		java.sql.Connection c = ConnectionWrapper.conn();

		try {
			String sql = "delete from matricula where id_aluno=? and id_disciplina=?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, m.getStudent().getId());
			ps.setInt(2, m.getSubject().getId());

			ps.executeUpdate();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Enrollment> findByStudent(Person p){
		ArrayList<Enrollment> lista = new ArrayList<Enrollment>();
		java.sql.Connection c = ConnectionWrapper.conn();

		try {
			String sql = "select * from matricula where id_aluno=?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, p.getId());
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
			    Enrollment m = new Enrollment();

				int idDisc = rs.getInt("id_disciplina");
				Subject d = SubjectDAO.findById(idDisc, true);

				m.setId(rs.getInt("id"));
				m.setSubject(d);
				m.setStudent(p);
				m.setSemester(rs.getString("semestre"));
				lista.add(m);
			}
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	public static ArrayList<Enrollment> findByStudentAndSemester(Person p, String semestre) {
		ArrayList<Enrollment> lista = new ArrayList<Enrollment>();
		java.sql.Connection c = ConnectionWrapper.conn();

		try {
			String sql = "select * from matricula where id_aluno=? AND semestre = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, p.getId());
			ps.setString(2, semestre);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				Enrollment m = new Enrollment();

				int idDisc = rs.getInt("id_disciplina");
				Subject d = SubjectDAO.findById(idDisc, true);

				m.setId(rs.getInt("id"));
				m.setSubject(d);
				m.setStudent(p);
				m.setSemester(rs.getString("semestre"));
				lista.add(m);
			}
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	public static boolean studentHasEnrollment(Person person, Subject subject, String semester) {
		java.sql.Connection c = ConnectionWrapper.conn();
		try {
			String sql = "select * from matricula where id_aluno = ? and id_disciplina = ? and semestre = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, person.getId());
			ps.setInt(2, subject.getId());
			ps.setString(3, semester);
			ResultSet rs = ps.executeQuery();

			if(rs.next()) {
				c.close();
			    return true;
			}
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
