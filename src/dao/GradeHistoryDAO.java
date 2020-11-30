package dao;

import domain.GradeHistory;
import domain.Person;
import domain.Student;
import domain.Subject;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class GradeHistoryDAO {
    public static void create(GradeHistory gradeHistory) {
        java.sql.Connection c = ConnectionWrapper.conn();

        try {
            String sql = "insert into historico_notas (id_aluno, id_disciplina, semestre, nota) values (?,?, ?, ?)";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, gradeHistory.getStudent().getId());
            ps.setInt(2, gradeHistory.getSubject().getId());
            ps.setString(3, gradeHistory.getSemester());
            ps.setDouble(4, gradeHistory.getGrade());
            ps.executeUpdate();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void updateGrade(GradeHistory gradeHistory) {
        java.sql.Connection c = ConnectionWrapper.conn();

        try {
            String sql = "update historico_notas set nota=? where id=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setDouble(1, gradeHistory.getGrade());
            ps.setInt(2, gradeHistory.getId());
            ps.executeUpdate();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void delete(GradeHistory gradeHistory) {
        java.sql.Connection c = ConnectionWrapper.conn();

        try {
            String sql = "delete from historico_notas where id=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, gradeHistory.getId());
            ps.executeUpdate();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean studentHasGradeForSubjectInSemester(Person student, Subject subject, String semester) {
        Person p = new Person();
        java.sql.Connection c = ConnectionWrapper.conn();
        try {
            String sql = "select * from historico_notas where id_aluno = ? and id_disciplina = ? and semestre = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, student.getId());
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

    public static ArrayList<GradeHistory> getGradesByStudentAndSemester(Person student, String semester) {
        java.sql.Connection c = ConnectionWrapper.conn();
        ArrayList<GradeHistory> gradeHistories = new ArrayList<GradeHistory>();

        try {
            String sql = "SELECT * FROM historico_notas where semestre=? and id_aluno=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, semester);
            ps.setInt(2, student.getId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                GradeHistory gh = new GradeHistory();
                gh.setId(rs.getInt("id"));
                gh.setSemester(rs.getString("semestre"));
                gh.setSubject(SubjectDAO.findById(rs.getInt("id_disciplina"), false));
                gh.setStudent(PersonDAO.findById(rs.getInt("id_aluno")));
                gh.setGrade(rs.getDouble("nota"));
                gradeHistories.add(gh);
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gradeHistories;
    }
}
