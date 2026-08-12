import type { ReportMetadata } from "../api/types";
import { formatRelativeTime } from "../utils/format";
import styles from "./ReportCard.module.css";

interface ReportCardProps {
  report: ReportMetadata;
  onOpen: () => void;
}

function ReportCard({ report, onOpen }: ReportCardProps) {
  return (
    <article className={styles.card} onClick={onOpen}>
      <div className={styles.topRow}>
        <div className={styles.icon}>{report.name.charAt(0)}</div>
        <span className={styles.rowCount}>{report.rowCount} rows</span>
      </div>
      <div>
        <h3 className={styles.name}>{report.name}</h3>
        <p className={styles.description}>{report.description}</p>
      </div>
      <div className={styles.footer}>
        <span className={styles.updated}>Updated {formatRelativeTime(report.lastUpdated)}</span>
        <span className={styles.open}>
          Open
          <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.75" strokeLinecap="round" strokeLinejoin="round">
            <path d="M5 12h13" />
            <path d="m12 5 7 7-7 7" />
          </svg>
        </span>
      </div>
    </article>
  );
}

export default ReportCard;
