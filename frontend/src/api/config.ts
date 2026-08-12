// Backend base URL. Falls back to localhost:8080 (the backend's default port) if not set.
export const API_BASE_URL = import.meta.env.VITE_API_BASE_URL ?? "http://localhost:8080";
