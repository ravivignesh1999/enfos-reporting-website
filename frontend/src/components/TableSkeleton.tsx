import styles from "./TableSkeleton.module.css";

interface TableSkeletonProps {
  rows?: number;
}

function TableSkeleton({ rows = 8 }: TableSkeletonProps) {
  return (
    <div className={styles.wrapper}>
      {Array.from({ length: rows }).map((_, i) => (
        <div key={i} className={styles.bar} style={{ width: `${100 - (i % 4) * 9}%` }} />
      ))}
    </div>
  );
}

export default TableSkeleton;
