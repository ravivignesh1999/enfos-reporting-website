import styles from "./StateMessage.module.css";

interface StateMessageProps {
  variant: "empty" | "error";
  title: string;
  message: string;
  actionLabel?: string;
  onAction?: () => void;
}

function StateMessage({ variant, title, message, actionLabel, onAction }: StateMessageProps) {
  return (
    <div className={styles.wrapper}>
      <div className={`${styles.iconCircle} ${styles[variant]}`}>
        {variant === "error" ? (
          <svg width="26" height="26" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.75" strokeLinecap="round">
            <path d="M12 8v5" />
            <path d="M12 17h.01" />
            <circle cx="12" cy="12" r="9" />
          </svg>
        ) : (
          <svg width="26" height="26" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.75" strokeLinecap="round">
            <path d="M4 6h16M4 12h10M4 18h6" />
          </svg>
        )}
      </div>
      <div className={styles.title}>{title}</div>
      <p className={styles.message}>{message}</p>
      {actionLabel && onAction && (
        <button className={styles.action} onClick={onAction}>
          {actionLabel}
        </button>
      )}
    </div>
  );
}

export default StateMessage;
