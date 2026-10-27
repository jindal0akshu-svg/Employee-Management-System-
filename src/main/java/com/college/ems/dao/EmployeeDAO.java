package com.college.ems.dao;

import com.college.ems.model.Employee;
import com.college.ems.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    public List<Employee> findAll() throws SQLException {
        String sql = "SELECT id,name,email,department,designation,salary,joining_date FROM employees ORDER BY id DESC";
        List<Employee> employees = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) employees.add(map(rs));
        }
        return employees;
    }

    public Employee findById(int id) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT * FROM employees WHERE id=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    public void insert(Employee e) throws SQLException {
        String sql = "INSERT INTO employees(name,email,department,designation,salary,joining_date) VALUES(?,?,?,?,?,?)";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            bind(ps, e); ps.executeUpdate();
        }
    }

    public boolean update(Employee e) throws SQLException {
        String sql = "UPDATE employees SET name=?,email=?,department=?,designation=?,salary=?,joining_date=? WHERE id=?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            bind(ps, e); ps.setInt(7, e.getId()); return ps.executeUpdate() == 1;
        }
    }

    public boolean delete(int id) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM employees WHERE id=?")) {
            ps.setInt(1, id); return ps.executeUpdate() == 1;
        }
    }

    private void bind(PreparedStatement ps, Employee e) throws SQLException {
        ps.setString(1, e.getName());
        ps.setString(2, e.getEmail());
        ps.setString(3, e.getDepartment());
        ps.setString(4, e.getDesignation());
        ps.setBigDecimal(5, e.getSalary());
        ps.setDate(6, e.getJoiningDate());
    }

    private Employee map(ResultSet rs) throws SQLException {
        Employee e = new Employee();
        e.setId(rs.getInt("id"));
        e.setName(rs.getString("name"));
        e.setEmail(rs.getString("email"));
        e.setDepartment(rs.getString("department"));
        e.setDesignation(rs.getString("designation"));
        e.setSalary(rs.getBigDecimal("salary"));
        e.setJoiningDate(rs.getDate("joining_date"));
        return e;
    }
}
