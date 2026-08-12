import type { ReactNode } from "react";
import styles from "./Placeholder.module.css";

interface PlaceholderProps {
  children: ReactNode;
}

function Placeholder({ children }: PlaceholderProps) {
  return <span className={styles.placeholder}>{children}</span>;
}

export default Placeholder;
