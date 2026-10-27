<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.college.ems.model.Employee" %>
<%!
  private String esc(Object value) {
    if (value == null) return "";
    return value.toString().replace("&","&amp;").replace("<","&lt;").replace(">","&gt;").replace("\"","&quot;").replace("'","&#39;");
  }
%>
<%
  Employee e = (Employee) request.getAttribute("employee");
  if (e == null) e = new Employee();
  boolean editing = e.getId() > 0;
%>
<!DOCTYPE html><html lang="en"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width,initial-scale=1">
<title><%=editing ? "Edit" : "Add"%> Employee</title><link rel="stylesheet" href="<%=request.getContextPath()%>/style.css"></head>
<body><main class="container narrow">
<header class="top"><div><p class="eyebrow">EMPLOYEE DIRECTORY</p><h1><%=editing ? "Edit employee" : "Add employee"%></h1><p class="muted">Enter the employee details below.</p></div></header>
<% if (request.getAttribute("error") != null) { %><div class="error"><%=esc(request.getAttribute("error"))%></div><% } %>
<section class="panel form-panel"><form method="post" action="<%=request.getContextPath()%>/employees">
<input type="hidden" name="action" value="<%=editing ? "update" : "insert"%>">
<% if (editing) { %><input type="hidden" name="id" value="<%=e.getId()%>"><% } %>
<div class="form-grid">
<label>Full name<input name="name" maxlength="100" required value="<%=esc(e.getName())%>" placeholder="e.g. Riya Sharma"></label>
<label>Email address<input type="email" name="email" maxlength="150" required value="<%=esc(e.getEmail())%>" placeholder="name@example.com"></label>
<label>Department<input name="department" maxlength="100" required value="<%=esc(e.getDepartment())%>" placeholder="e.g. Engineering"></label>
<label>Designation<input name="designation" maxlength="100" required value="<%=esc(e.getDesignation())%>" placeholder="e.g. Software Engineer"></label>
<label>Monthly salary (INR)<input type="number" name="salary" min="0" step="0.01" required value="<%=e.getSalary() == null ? "" : esc(e.getSalary())%>" placeholder="45000.00"></label>
<label>Joining date<input type="date" name="joiningDate" max="<%=java.time.LocalDate.now()%>" required value="<%=e.getJoiningDate() == null ? "" : esc(e.getJoiningDate())%>"></label>
</div><div class="form-actions"><a class="button secondary" href="<%=request.getContextPath()%>/employees">Cancel</a><button class="button" type="submit"><%=editing ? "Save changes" : "Add employee"%></button></div>
</form></section><footer>Fields marked required must be completed.</footer></main></body></html>
