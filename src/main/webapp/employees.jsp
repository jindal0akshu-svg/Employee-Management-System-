<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List,com.college.ems.model.Employee" %>
<%!
  private String esc(Object value) {
    if (value == null) return "";
    return value.toString().replace("&","&amp;").replace("<","&lt;").replace(">","&gt;").replace("\"","&quot;").replace("'","&#39;");
  }
%>
<%
  List<Employee> employees = (List<Employee>) request.getAttribute("employees");
  String message = request.getParameter("message");
  String notice = "added".equals(message) ? "Employee added successfully." :
                  "updated".equals(message) ? "Employee updated successfully." :
                  "deleted".equals(message) ? "Employee deleted successfully." : null;
%>
<!DOCTYPE html><html lang="en"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width,initial-scale=1">
<title>Employee Management System</title><link rel="stylesheet" href="<%=request.getContextPath()%>/style.css"></head>
<body><main class="container">
<header class="top"><div><p class="eyebrow">ADMINISTRATION</p><h1>Employee Management</h1><p class="muted">Manage your team's records in one place.</p></div>
<a class="button" href="<%=request.getContextPath()%>/employees?action=new">+ Add employee</a></header>
<% if (notice != null) { %><div class="notice"><%=notice%></div><% } %>
<section class="panel"><div class="panel-head"><div><h2>Employee directory</h2><p class="muted"><%= employees == null ? 0 : employees.size() %> employee record(s)</p></div></div>
<div class="table-wrap"><table><thead><tr><th>ID</th><th>Employee</th><th>Department</th><th>Designation</th><th>Salary</th><th>Joining date</th><th>Actions</th></tr></thead><tbody>
<% if (employees == null || employees.isEmpty()) { %><tr><td colspan="7" class="empty">No employees found. Add your first employee to get started.</td></tr><% } else { for (Employee e : employees) { %>
<tr><td>#<%=e.getId()%></td><td><strong><%=esc(e.getName())%></strong><span class="sub"><%=esc(e.getEmail())%></span></td>
<td><%=esc(e.getDepartment())%></td><td><%=esc(e.getDesignation())%></td><td>₹<%=esc(e.getSalary())%></td><td><%=esc(e.getJoiningDate())%></td>
<td class="actions"><a class="link" href="<%=request.getContextPath()%>/employees?action=edit&id=<%=e.getId()%>">Edit</a>
<form method="post" action="<%=request.getContextPath()%>/employees" onsubmit="return confirm('Delete this employee record?');">
<input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="<%=e.getId()%>"><button class="danger-link" type="submit">Delete</button></form></td></tr>
<% } } %>
</tbody></table></div></section><footer>Employee Management System · Web Application Development Project</footer></main></body></html>
