// These types mirror the backend's DTOs exactly (see backend/src/main/java/.../dto and
// .../controller). Kept in sync by hand — if the backend response shape changes, update here.

export type ReportId = "users" | "departments" | "projects";

// GET /api/reports
export interface ReportMetadata {
  id: ReportId;
  name: string;
  description: string;
  rowCount: number;
  lastUpdated: string; // ISO instant, e.g. "2026-08-12T00:54:26.188061Z"
}

// Shared wrapper for the three paginated row endpoints.
export interface PagedResponse<T> {
  content: T[];
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
}

// GET /api/reports/users
export interface UserResponse {
  id: number;
  name: string | null;
  email: string | null;
  role: string | null;
  status: string | null;
  createdDate: string | null; // ISO date "YYYY-MM-DD"
  createdAt: string; // always present
  updatedAt: string; // always present
}

// GET /api/reports/departments
export interface DepartmentResponse {
  id: number;
  name: string | null;
  manager: string | null;
  employeeCount: number | null;
  location: string | null;
  createdAt: string;
  updatedAt: string;
}

// GET /api/reports/projects
export interface ProjectResponse {
  id: number;
  name: string | null;
  department: string | null;
  owner: string | null;
  status: string | null;
  startDate: string | null;
  endDate: string | null;
  createdAt: string;
  updatedAt: string;
}

// Shape returned for every error (400s and 500s alike).
export interface ErrorResponse {
  timestamp: string;
  status: number;
  message: string;
  path: string;
}

// Matches each controller's SORTABLE_FIELDS whitelist exactly — sorting by anything else
// is rejected by the backend with a 400.
export const SORTABLE_FIELDS: Record<ReportId, readonly string[]> = {
  users: ["id", "name", "status", "createdDate"],
  departments: ["id", "name", "employeeCount", "location"],
  projects: ["id", "name", "status", "startDate", "endDate"],
};
