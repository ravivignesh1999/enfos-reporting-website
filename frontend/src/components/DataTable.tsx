import type { ReactNode } from "react";
import styles from "./DataTable.module.css";

export interface Column<T> {
  key: string; // backend field name — must match a sortable field for sortable columns
  label: string;
  sortable?: boolean;
  align?: "left" | "right";
  render: (row: T) => ReactNode;
}

interface DataTableProps<T extends { id: number }> {
  columns: Column<T>[];
  rows: T[];
  sortKey: string;
  sortDir: "asc" | "desc";
  onSort: (key: string) => void;
}

function DataTable<T extends { id: number }>({ columns, rows, sortKey, sortDir, onSort }: DataTableProps<T>) {
  return (
    <table className={styles.table}>
      <thead>
        <tr>
          {columns.map((col) => {
            const active = sortKey === col.key;
            return (
              <th
                key={col.key}
                onClick={col.sortable ? () => onSort(col.key) : undefined}
                className={col.sortable ? styles.sortable : undefined}
                style={{ textAlign: col.align ?? "left" }}
              >
                <span className={styles.headLabel}>
                  {col.label}
                  {col.sortable && (
                    <span className={active ? styles.arrowActive : styles.arrow}>
                      {active ? (sortDir === "asc" ? "↑" : "↓") : "⇅"}
                    </span>
                  )}
                </span>
              </th>
            );
          })}
        </tr>
      </thead>
      <tbody>
        {rows.map((row) => (
          <tr key={row.id} className={styles.row}>
            {columns.map((col) => (
              <td key={col.key} style={{ textAlign: col.align ?? "left" }}>
                <span className={styles.cellContent}>{col.render(row)}</span>
              </td>
            ))}
          </tr>
        ))}
      </tbody>
    </table>
  );
}

export default DataTable;
