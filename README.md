# Enfos Reporting Portal

A full-stack internal reporting portal: a React frontend backed by a Spring Boot API. Users
browse three available reports (Users, Departments, Projects) from a landing page and open each
one to explore its data in a sortable, paginated table.

Built for the Enfos Engineering coding take-home assessment.

---

## Quick start

**Prerequisite:** [Docker Desktop](https://www.docker.com/products/docker-desktop/) (with Docker
Compose, included by default).

From the repository root:
```
docker compose up --build
```

That's it — one command builds and starts both services:
- Frontend: **http://localhost:5173**
- Backend API: **http://localhost:8080**

No local Java, Maven, or Node installation is required — everything runs inside containers. The
backend seeds its own in-memory database with mock data on startup, so there's nothing else to
configure.

To stop everything: `Ctrl+C`, then `docker compose down`.

---

## Running without Docker

Useful for active development (hot reload, debugging).

**Backend** (requires Java 17+):
```
cd backend
./mvnw spring-boot:run
```
Starts on port 8080.

**Frontend** (requires Node 20+):
```
cd frontend
npm install
npm run dev
```
Starts on port 5173. Must be run on this exact port — the backend's CORS configuration only
allows `http://localhost:5173` as an origin. If that port is already in use by something else,
free it first rather than letting Vite fall back to a different port.

Run the two in separate terminals; the frontend needs the backend running to load any data.

---

## Tech stack

| | |
|---|---|
| Backend | Java 21, Spring Boot 4.1, Spring Data JPA, H2 (in-memory) |
| Frontend | React 19, TypeScript, Vite, React Router |
| Backend tests | JUnit 5, MockMvc (17 tests) |
| Containerization | Docker (multi-stage builds), Docker Compose, nginx (serves the built frontend) |

---

## API reference

| Method | Endpoint | Returns |
|---|---|---|
| GET | `/api/reports` | Catalog of the 3 reports — id, name, description, live row count, live last-updated timestamp |
| GET | `/api/reports/users` | Paginated Users rows |
| GET | `/api/reports/departments` | Paginated Departments rows |
| GET | `/api/reports/projects` | Paginated Projects rows |

The three row endpoints share the same query parameters:
- `page` — 0-indexed, defaults to `0`
- `size` — defaults to `10`, capped at `100`
- `sort` — `field,asc` or `field,desc`; only specific fields are sortable per report (see below).
  An unsupported field returns `400`.

Sortable fields:
- **Users**: `name`, `status`, `createdDate`
- **Departments**: `name`, `employeeCount`, `location`
- **Projects**: `name`, `status`, `startDate`, `endDate`

Every error response (400s and 500s) has the same shape: `{ status, message, path, timestamp }`.

---

## Reports

| Report | Columns |
|---|---|
| **Users** | User ID, Name, Email, Role, Status, Created Date, Updated |
| **Departments** | Department ID, Department Name, Manager, Employee Count, Location, Updated |
| **Projects** | Project ID, Project Name, Department, Owner, Status, Start Date, End Date, Updated |

Each report is seeded with 40 mock rows on backend startup, deliberately mixed in quality: ~70%
complete/realistic, ~15% with missing optional fields, ~10% malformed/edge-case values (long
names, invalid emails, unicode/emoji, out-of-range statuses, extreme dates), and ~5% nearly-empty
rows. This is intentional — the point is to demonstrate the frontend handling real-world-messy
data gracefully rather than assuming it's always clean.

---

## Project structure

```
backend/    Spring Boot API (controller -> service -> repository -> H2)
frontend/   React + TypeScript SPA (pages -> components, typed API client)
docker-compose.yml   Runs both together
```

Each side has its own `Dockerfile`. See `backend/IMPLEMENTATION_NOTES.md` for detailed backend
design decisions, tradeoffs, and bugs encountered/resolved during development.

---

## Assumptions & tradeoffs

- **Database is in-memory (H2), not persistent.** The spec allows this explicitly. Real Spring
  Data JPA is used (not a hand-rolled in-memory store), so swapping in a real database later is a
  datasource configuration change, not a rewrite — the `postgresql` driver is already on the
  backend's classpath, unused, for exactly this reason.
- **Report search is client-side.** The spec requires searching/filtering reports by name on the
  landing page; with only 3 hardcoded reports, filtering them in the browser is simplest. If the
  report catalog needs to scale up significantly, that search should move to a real backend
  endpoint instead.
- **Three "nice-to-have" UI features from the original design reference were intentionally left
  out**: an in-table free-text search box, a per-column show/hide toggle, and CSV export. None
  are in the spec, and none are backed by the API (which only supports pagination and a fixed
  sort whitelist, no free-text search). Building partial/fake versions of them seemed worse than
  not building them.
- **No authentication.** Not required by the spec; this is a read-only internal reporting demo.
- **Report row endpoints return the underlying data directly** (via response DTOs matching the
  API contract) rather than routing through a separate business-rules layer, since these are
  simple read-only reports with no computed/derived fields beyond what's already stored.

---

## Screenshots / demo
