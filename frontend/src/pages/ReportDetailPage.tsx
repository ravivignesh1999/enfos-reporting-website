import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useReports } from "../hooks/useReports";
import { useReportRows } from "../hooks/useReportRows";
import DataTable from "../components/DataTable";
import Pagination from "../components/Pagination";
import TableSkeleton from "../components/TableSkeleton";
import StateMessage from "../components/StateMessage";
import { userColumns, departmentColumns, projectColumns } from "./reportColumns";
import type { DepartmentResponse, ProjectResponse, ReportId, UserResponse } from "../api/types";
import styles from "./ReportDetailPage.module.css";

const VALID_REPORT_IDS: readonly string[] = ["users", "departments", "projects"];

function ReportDetailPage() {
  const { reportId } = useParams<{ reportId: string }>();
  const navigate = useNavigate();
  const { data: reports } = useReports();

  const [page, setPage] = useState(0);
  const [sortKey, setSortKey] = useState("");
  const [sortDir, setSortDir] = useState<"asc" | "desc">("asc");

  const isValidReportId = !!reportId && VALID_REPORT_IDS.includes(reportId);
  // Falls back so the hook below can still be called unconditionally; invalid ids are handled below.
  const safeReportId = (isValidReportId ? reportId : "users") as ReportId;
  const sort = sortKey ? `${sortKey},${sortDir}` : "";

  const { data, loading, error, reload } = useReportRows(safeReportId, page, sort);

  if (!isValidReportId) {
    return (
      <div className={styles.page}>
        <main className={styles.main}>
          <StateMessage
            variant="error"
            title="Unknown report"
            message={`"${reportId}" isn't a report we know about.`}
            actionLabel="Back to reports"
            onAction={() => navigate("/")}
          />
        </main>
      </div>
    );
  }

  const meta = reports?.find((r) => r.id === safeReportId);

  function handleSort(key: string) {
    if (sortKey === key) {
      setSortDir((d) => (d === "asc" ? "desc" : "asc"));
    } else {
      setSortKey(key);
      setSortDir("asc");
    }
    setPage(0);
  }

  return (
    <div className={styles.page}>
      <main className={styles.main}>
        <button className={styles.backButton} onClick={() => navigate("/")}>
          <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.75" strokeLinecap="round" strokeLinejoin="round">
            <path d="M19 12H6" />
            <path d="m12 19-7-7 7-7" />
          </svg>
          All reports
        </button>

        <div className={styles.titleRow}>
          <h1 className={styles.title}>{meta?.name ?? safeReportId}</h1>
          {meta && <p className={styles.subtitle}>{meta.description}</p>}
        </div>

        <div className={styles.card}>
          {loading && <TableSkeleton />}

          {!loading && error && (
            <StateMessage variant="error" title="We couldn't load this report" message={error} actionLabel="Try again" onAction={reload} />
          )}

          {!loading && !error && data && data.content.length === 0 && (
            <StateMessage variant="empty" title="Nothing to show yet" message="No records were found for this report." />
          )}

          {!loading && !error && data && data.content.length > 0 && (
            <>
              <div className={styles.tableScroll}>
                {safeReportId === "users" && (
                  <DataTable columns={userColumns} rows={data.content as UserResponse[]} sortKey={sortKey} sortDir={sortDir} onSort={handleSort} />
                )}
                {safeReportId === "departments" && (
                  <DataTable columns={departmentColumns} rows={data.content as DepartmentResponse[]} sortKey={sortKey} sortDir={sortDir} onSort={handleSort} />
                )}
                {safeReportId === "projects" && (
                  <DataTable columns={projectColumns} rows={data.content as ProjectResponse[]} sortKey={sortKey} sortDir={sortDir} onSort={handleSort} />
                )}
              </div>

              <Pagination page={data.page} totalPages={data.totalPages} totalElements={data.totalElements} pageSize={data.size} onPageChange={setPage} />
            </>
          )}
        </div>
      </main>
    </div>
  );
}

export default ReportDetailPage;
