package com.college.ems.servlet;

import com.college.ems.dao.EmployeeDAO;
import com.college.ems.model.Employee;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class EmployeeServlet extends HttpServlet {
    private EmployeeDAO dao;

    @Override public void init() { dao = new EmployeeDAO(); }

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        try {
            if ("new".equals(action)) {
                req.setAttribute("employee", new Employee());
                req.getRequestDispatcher("/employee-form.jsp").forward(req, resp);
            } else if ("edit".equals(action)) {
                Employee e = dao.findById(parseId(req.getParameter("id")));
                if (e == null) { resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Employee not found"); return; }
                req.setAttribute("employee", e);
                req.getRequestDispatcher("/employee-form.jsp").forward(req, resp);
            } else {
                req.setAttribute("employees", dao.findAll());
                req.getRequestDispatcher("/employees.jsp").forward(req, resp);
            }
        } catch (IllegalArgumentException ex) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid employee ID");
        } catch (SQLException ex) {
            throw new ServletException("Could not load employee records. Check database configuration.", ex);
        }
    }

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        try {
            if ("delete".equals(action)) {
                dao.delete(parseId(req.getParameter("id")));
                resp.sendRedirect(req.getContextPath() + "/employees?message=deleted");
                return;
            }
            Employee e = readEmployee(req);
            String error = validate(e);
            if (error != null) {
                req.setAttribute("error", error);
                req.setAttribute("employee", e);
                req.getRequestDispatcher("/employee-form.jsp").forward(req, resp);
                return;
            }
            if ("update".equals(action)) {
                e.setId(parseId(req.getParameter("id")));
                if (!dao.update(e)) { resp.sendError(404, "Employee not found"); return; }
                resp.sendRedirect(req.getContextPath() + "/employees?message=updated");
            } else if ("insert".equals(action)) {
                dao.insert(e);
                resp.sendRedirect(req.getContextPath() + "/employees?message=added");
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action");
            }
        } catch (IllegalArgumentException ex) {
            req.setAttribute("error", "Please enter valid employee details and a valid ID.");
            req.setAttribute("employee", new Employee());
            req.getRequestDispatcher("/employee-form.jsp").forward(req, resp);
        } catch (SQLException ex) {
            if ("insert".equals(action) || "update".equals(action)) {
                req.setAttribute("error", "Could not save the employee. Check that the email is unique and database settings are correct.");
                req.setAttribute("employee", safeReadEmployee(req));
                req.getRequestDispatcher("/employee-form.jsp").forward(req, resp);
            } else {
                throw new ServletException("Database operation failed.", ex);
            }
        }
    }

    private Employee safeReadEmployee(HttpServletRequest req) {
        try { return readEmployee(req); } catch (Exception ignored) { return new Employee(); }
    }

    private Employee readEmployee(HttpServletRequest req) {
        Employee e = new Employee();
        e.setName(required(req, "name"));
        e.setEmail(required(req, "email"));
        e.setDepartment(required(req, "department"));
        e.setDesignation(required(req, "designation"));
        e.setSalary(new BigDecimal(required(req, "salary")));
        e.setJoiningDate(Date.valueOf(LocalDate.parse(required(req, "joiningDate"))));
        return e;
    }

    private String required(HttpServletRequest req, String key) {
        String value = req.getParameter(key);
        if (value == null || value.trim().isEmpty()) throw new IllegalArgumentException("Missing " + key);
        return value.trim();
    }

    private String validate(Employee e) {
        if (e.getName().length() > 100 || e.getDepartment().length() > 100 ||
            e.getDesignation().length() > 100 || e.getEmail().length() > 150)
            return "One or more fields exceed the allowed length.";
        if (!e.getEmail().matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) return "Enter a valid email address.";
        if (e.getSalary().compareTo(BigDecimal.ZERO) < 0 || e.getSalary().scale() > 2)
            return "Salary must be non-negative and have at most two decimal places.";
        if (e.getJoiningDate().toLocalDate().isAfter(LocalDate.now())) return "Joining date cannot be in the future.";
        return null;
    }

    private int parseId(String value) {
        int id = Integer.parseInt(value);
        if (id < 1) throw new IllegalArgumentException("Invalid ID");
        return id;
    }
}
