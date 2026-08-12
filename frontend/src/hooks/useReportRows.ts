import type { ReportId } from "../api/types";
import { getReportRows } from "../api/reports";
import { useFetch } from "./useFetch";

export function useReportRows<K extends ReportId>(reportId: K, page: number, sort: string) {
  return useFetch(() => getReportRows(reportId, { page, sort }), [reportId, page, sort]);
}
