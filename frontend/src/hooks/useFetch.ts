import { useEffect, useState } from "react";

interface FetchState<T> {
  data: T | null;
  loading: boolean;
  error: string | null;
}

const MIN_LOADING_MS = 500;

function waitForRemaining(startedAt: number): Promise<void> {
  const remaining = MIN_LOADING_MS - (Date.now() - startedAt);
  return remaining > 0 ? new Promise((resolve) => setTimeout(resolve, remaining)) : Promise.resolve();
}

export function useFetch<T>(fetchFn: () => Promise<T>, deps: unknown[]) {
  const [state, setState] = useState<FetchState<T>>({ data: null, loading: true, error: null });
  const [reloadToken, setReloadToken] = useState(0);

  useEffect(() => {
    let cancelled = false;
    setState((prev) => ({ ...prev, loading: true, error: null }));
    const startedAt = Date.now();

    fetchFn()
      .then(async (data) => {
        await waitForRemaining(startedAt);
        if (!cancelled) setState({ data, loading: false, error: null });
      })
      .catch(async (err: unknown) => {
        await waitForRemaining(startedAt);
        if (!cancelled) {
          const message = err instanceof Error ? err.message : "Something went wrong.";
          setState({ data: null, loading: false, error: message });
        }
      });

    return () => {
      cancelled = true;
    };
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [...deps, reloadToken]);

  return { ...state, reload: () => setReloadToken((k) => k + 1) };
}
