import type { Column } from "../components/DataTable";
import StatusBadge from "../components/StatusBadge";
import type { DepartmentResponse, ProjectResponse, UserResponse } from "../api/types";
import { formatDate, formatDateTime } from "../utils/format";

export const userColumns: Column<UserResponse>[] = [
  { key: "id", label: "User ID", render: (u) => u.id },
  { key: "name", label: "Name", sortable: true, render: (u) => u.name ?? "—" },
  { key: "email", label: "Email", render: (u) => u.email ?? "—" },
  { key: "role", label: "Role", render: (u) => u.role ?? "—" },
  { key: "status", label: "Status", sortable: true, render: (u) => <StatusBadge value={u.status} /> },
  { key: "createdDate", label: "Created Date", sortable: true, align: "right", render: (u) => formatDate(u.createdDate) },
  { key: "updatedAt", label: "Updated", align: "right", render: (u) => formatDateTime(u.updatedAt) },
];

export const departmentColumns: Column<DepartmentResponse>[] = [
  { key: "id", label: "Department ID", render: (d) => d.id },
  { key: "name", label: "Department Name", sortable: true, render: (d) => d.name ?? "—" },
  { key: "manager", label: "Manager", render: (d) => d.manager ?? "—" },
  {
    key: "employeeCount",
    label: "Employee Count",
    sortable: true,
    align: "right",
    render: (d) => (d.employeeCount ?? "—"),
  },
  { key: "location", label: "Location", sortable: true, render: (d) => d.location ?? "—" },
  { key: "updatedAt", label: "Updated", align: "right", render: (d) => formatDateTime(d.updatedAt) },
];

export const projectColumns: Column<ProjectResponse>[] = [
  { key: "id", label: "Project ID", render: (p) => p.id },
  { key: "name", label: "Project Name", sortable: true, render: (p) => p.name ?? "—" },
  { key: "department", label: "Department", render: (p) => p.department ?? "—" },
  { key: "owner", label: "Owner", render: (p) => p.owner ?? "—" },
  { key: "status", label: "Status", sortable: true, render: (p) => <StatusBadge value={p.status} /> },
  { key: "startDate", label: "Start Date", sortable: true, render: (p) => formatDate(p.startDate) },
  { key: "endDate", label: "End Date", sortable: true, render: (p) => formatDate(p.endDate) },
  { key: "updatedAt", label: "Updated", align: "right", render: (p) => formatDateTime(p.updatedAt) },
];
