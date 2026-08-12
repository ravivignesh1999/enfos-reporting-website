import type { ReactNode } from "react";
import type { Column } from "../components/DataTable";
import Placeholder from "../components/Placeholder";
import StatusBadge from "../components/StatusBadge";
import type { DepartmentResponse, ProjectResponse, UserResponse } from "../api/types";
import { formatDate, formatDateTime, isBlank } from "../utils/format";

function renderText(value: string | null | undefined, label = "—"): ReactNode {
  return isBlank(value) ? <Placeholder>{label}</Placeholder> : value;
}

function renderDate(value: string | null, label = "—"): ReactNode {
  return isBlank(value) ? <Placeholder>{label}</Placeholder> : formatDate(value);
}

function renderNumber(value: number | null, label = "—"): ReactNode {
  return value == null ? <Placeholder>{label}</Placeholder> : value;
}

export const userColumns: Column<UserResponse>[] = [
  { key: "id", label: "User ID", render: (u) => u.id },
  { key: "name", label: "Name", sortable: true, render: (u) => renderText(u.name) },
  { key: "email", label: "Email", render: (u) => renderText(u.email) },
  { key: "role", label: "Role", render: (u) => renderText(u.role, "No role assigned") },
  { key: "status", label: "Status", sortable: true, render: (u) => <StatusBadge value={u.status} /> },
  { key: "createdDate", label: "Created Date", sortable: true, align: "right", render: (u) => renderDate(u.createdDate) },
  { key: "updatedAt", label: "Updated", align: "right", render: (u) => formatDateTime(u.updatedAt) },
];

export const departmentColumns: Column<DepartmentResponse>[] = [
  { key: "id", label: "Department ID", render: (d) => d.id },
  { key: "name", label: "Department Name", sortable: true, render: (d) => renderText(d.name) },
  { key: "manager", label: "Manager", render: (d) => renderText(d.manager, "Vacant") },
  { key: "employeeCount", label: "Employee Count", sortable: true, align: "right", render: (d) => renderNumber(d.employeeCount) },
  { key: "location", label: "Location", sortable: true, render: (d) => renderText(d.location) },
  { key: "updatedAt", label: "Updated", align: "right", render: (d) => formatDateTime(d.updatedAt) },
];

export const projectColumns: Column<ProjectResponse>[] = [
  { key: "id", label: "Project ID", render: (p) => p.id },
  { key: "name", label: "Project Name", sortable: true, render: (p) => renderText(p.name) },
  { key: "department", label: "Department", render: (p) => renderText(p.department) },
  { key: "owner", label: "Owner", render: (p) => renderText(p.owner, "Unassigned") },
  { key: "status", label: "Status", sortable: true, render: (p) => <StatusBadge value={p.status} /> },
  { key: "startDate", label: "Start Date", sortable: true, render: (p) => renderDate(p.startDate) },
  { key: "endDate", label: "End Date", sortable: true, render: (p) => renderDate(p.endDate, "Ongoing") },
  { key: "updatedAt", label: "Updated", align: "right", render: (p) => formatDateTime(p.updatedAt) },
];
