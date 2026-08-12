import { getReports } from "../api/reports";
import { useFetch } from "./useFetch";

export function useReports() {
  return useFetch(() => getReports(), []);
}
