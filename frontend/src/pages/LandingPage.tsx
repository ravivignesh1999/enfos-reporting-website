import { useMemo, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useReports } from "../hooks/useReports";
import ReportCard from "../components/ReportCard";
import TableSkeleton from "../components/TableSkeleton";
import StateMessage from "../components/StateMessage";
import styles from "./LandingPage.module.css";

function LandingPage() {
  const navigate = useNavigate();
  const { data: reports, loading, error, reload } = useReports();
  const [query, setQuery] = useState("");

  const filtered = useMemo(() => {
    if (!reports) return [];
    const q = query.trim().toLowerCase();
    if (!q) return reports;
    return reports.filter((r) => r.name.toLowerCase().includes(q));
  }, [reports, query]);

  return (
    <div className={styles.page}>
      <header className={styles.header}>
        <div className={styles.brand}>
          <div className={styles.logo}>E</div>
          <div className={styles.brandName}>ENFOS</div>
          <div className={styles.brandTag}>Internal</div>
        </div>
      </header>

      <main className={styles.main}>
        <div className={styles.intro}>
          <div className={styles.eyebrow}>Reports</div>
          <h1 className={styles.heading}>Everything the org runs on, in one place.</h1>
          <p className={styles.subheading}>
            Browse the reports available to your team and open one to explore the data.
          </p>
        </div>

        <label className={styles.searchBox}>
          <svg width="17" height="17" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.75" strokeLinecap="round">
            <circle cx="11" cy="11" r="7" />
            <path d="m20 20-3.5-3.5" />
          </svg>
          <input
            value={query}
            onChange={(e) => setQuery(e.target.value)}
            placeholder="Search reports"
            className={styles.searchInput}
          />
        </label>

        {loading && <TableSkeleton rows={3} />}

        {error && (
          <StateMessage
            variant="error"
            title="We couldn't load the reports"
            message={error}
            actionLabel="Try again"
            onAction={reload}
          />
        )}

        {!loading && !error && reports && filtered.length === 0 && (
          <StateMessage
            variant="empty"
            title="No reports match that search"
            message="Try a different term, or clear the search to see all reports."
            actionLabel="Clear search"
            onAction={() => setQuery("")}
          />
        )}

        {!loading && !error && filtered.length > 0 && (
          <div className={styles.grid}>
            {filtered.map((report) => (
              <ReportCard key={report.id} report={report} onOpen={() => navigate(`/reports/${report.id}`)} />
            ))}
          </div>
        )}
      </main>
    </div>
  );
}

export default LandingPage;
