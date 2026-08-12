import styles from "./StatusBadge.module.css";

// Unrecognized values (e.g. "LEGACY_ACTIVE") fall back to a neutral badge instead of being hidden.
const KNOWN_TONES: Record<string, string> = {
  ACTIVE: "green",
  INACTIVE: "gray",
  PENDING: "amber",
  ON_HOLD: "amber",
  COMPLETED: "blue",
  CANCELLED: "gray",
};

interface StatusBadgeProps {
  value: string | null;
}

function StatusBadge({ value }: StatusBadgeProps) {
  if (!value) {
    return <span className={styles.empty}>—</span>;
  }

  const tone = KNOWN_TONES[value.toUpperCase()] ?? "neutral";
  return <span className={`${styles.badge} ${styles[tone]}`}>{value}</span>;
}

export default StatusBadge;
