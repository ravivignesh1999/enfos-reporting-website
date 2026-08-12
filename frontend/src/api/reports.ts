import { apiFetch } from "./client";
import type {
  DepartmentResponse,
  PagedResponse,
  ProjectResponse,
  ReportId,
  ReportMetadata,
  UserResponse,
} from "./types";

export function getReports(): Promise<ReportMetadata[]> {
  return apiFetch<ReportMetadata[]>("/api/reports");
}

interface RowsByReportId {
  users: UserResponse;
  departments: DepartmentResponse;
  projects: ProjectResponse;
}

export interface FetchRowsParams {
  page: number;
  size?: number;
  sort?: string; // e.g. "name,asc"
}

export function getReportRows<K extends ReportId>(
  reportId: K,
  params: FetchRowsParams,
): Promise<PagedResponse<RowsByReportId[K]>> {
  return apiFetch(`/api/reports/${reportId}`, {
    page: params.page,
    size: params.size,
    sort: params.sort,
  });
}
