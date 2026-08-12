import { API_BASE_URL } from "./config";
import type { ErrorResponse } from "./types";

export class ApiError extends Error {
  status: number;

  constructor(message: string, status: number) {
    super(message);
    this.status = status;
  }
}

type QueryParams = Record<string, string | number | undefined>;

export async function apiFetch<T>(path: string, params?: QueryParams): Promise<T> {
  const url = new URL(path, API_BASE_URL);
  if (params) {
    for (const [key, value] of Object.entries(params)) {
      if (value !== undefined) url.searchParams.set(key, String(value));
    }
  }

  let response: Response;
  try {
    response = await fetch(url);
  } catch {
    throw new ApiError("Could not reach the server. Check your connection and try again.", 0);
  }

  if (!response.ok) {
    const body: ErrorResponse | null = await response.json().catch(() => null);
    throw new ApiError(body?.message ?? `Request failed (${response.status})`, response.status);
  }

  return response.json();
}
