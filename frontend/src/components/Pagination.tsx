import styles from "./Pagination.module.css";

interface PaginationProps {
  page: number; // 0-indexed, matches the backend
  totalPages: number;
  totalElements: number;
  pageSize: number;
  onPageChange: (page: number) => void;
}

function Pagination({ page, totalPages, totalElements, pageSize, onPageChange }: PaginationProps) {
  const from = totalElements === 0 ? 0 : page * pageSize + 1;
  const to = Math.min((page + 1) * pageSize, totalElements);
  const canGoPrev = page > 0;
  const canGoNext = page < totalPages - 1;

  return (
    <div className={styles.wrapper}>
      <div className={styles.range}>
        Showing {from}–{to} of {totalElements}
      </div>
      <div className={styles.controls}>
        <button disabled={!canGoPrev} onClick={() => onPageChange(page - 1)} className={styles.button}>
          Previous
        </button>
        <span className={styles.pageLabel}>
          Page {page + 1} of {Math.max(totalPages, 1)}
        </span>
        <button disabled={!canGoNext} onClick={() => onPageChange(page + 1)} className={styles.button}>
          Next
        </button>
      </div>
    </div>
  );
}

export default Pagination;
