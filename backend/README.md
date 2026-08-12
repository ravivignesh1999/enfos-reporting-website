# Backend API — Enfos Reporting Portal

Spring Boot REST API backing the reporting portal. Serves three reports (Users, Departments,
Projects) from an in-memory H2 database, seeded with mock data on startup.

For setup/run instructions covering the whole project (frontend + backend together), see the
[repository root README](../README.md). This document is the API reference.

**Base URL:** `http://localhost:8080` (when run via `docker compose up` or `./mvnw spring-boot:run`)

---

## Endpoints

| Method | Path | Description |
|---|---|---|
| GET | `/api/reports` | Catalog of available reports |
| GET | `/api/reports/users` | Users report, paginated |
| GET | `/api/reports/departments` | Departments report, paginated |
| GET | `/api/reports/projects` | Projects report, paginated |

---

### `GET /api/reports`

Returns metadata for all available reports. Row counts and last-updated timestamps are computed
live from the database on every call, not cached.

**Response:** `ReportMetadata[]`

| Field | Type | Description |
|---|---|---|
| `id` | string | Matches the report's URL slug (`"users"`, `"departments"`, `"projects"`) |
| `name` | string | Display name |
| `description` | string | Short description |
| `rowCount` | number | Live count of rows in that report |
| `lastUpdated` | string (ISO instant) | Most recent `updatedAt` across that report's rows |

<details>
<summary>Example response</summary>

```json
[
  {
    "id": "users",
    "name": "Users",
    "description": "People in the system, their roles, and account status.",
    "rowCount": 40,
    "lastUpdated": "2026-08-12T00:54:26.188061Z"
  },
  {
    "id": "departments",
    "name": "Departments",
    "description": "Org structure across teams, managers, and locations.",
    "rowCount": 40,
    "lastUpdated": "2026-08-12T00:54:26.163247Z"
  },
  {
    "id": "projects",
    "name": "Projects",
    "description": "Active and past work across departments.",
    "rowCount": 40,
    "lastUpdated": "2026-08-12T00:54:26.128491Z"
  }
]
```
</details>

---

### `GET /api/reports/{users|departments|projects}`

Returns a page of rows for the given report.

**Query parameters** (all optional):

| Param | Default | Notes |
|---|---|---|
| `page` | `0` | 0-indexed |
| `size` | `10` | Capped at `100` server-side, regardless of what's requested |
| `sort` | `id,asc` | Format: `field,asc` or `field,desc`. Only specific fields are sortable per report (below) — requesting any other field returns `400` |

**Sortable fields:**

| Report | Sortable fields |
|---|---|
| Users | `id`, `name`, `status`, `createdDate` |
| Departments | `id`, `name`, `employeeCount`, `location` |
| Projects | `id`, `name`, `status`, `startDate`, `endDate` |

**Response:** `PagedResponse<T>`

| Field | Type | Description |
|---|---|---|
| `content` | array | The rows for this page (shape depends on report — see below) |
| `page` | number | Current page (0-indexed) |
| `size` | number | Page size actually used |
| `totalElements` | number | Total rows across all pages |
| `totalPages` | number | Total number of pages |

#### Row shapes

**Users** (`UserResponse`):

| Field | Type | Nullable? |
|---|---|---|
| `id` | number | No |
| `name` | string | Yes |
| `email` | string | Yes |
| `role` | string | Yes |
| `status` | string | Yes — see "Status fields" below |
| `createdDate` | string (ISO date) | Yes |
| `createdAt` | string (ISO instant) | No |
| `updatedAt` | string (ISO instant) | No |

**Departments** (`DepartmentResponse`):

| Field | Type | Nullable? |
|---|---|---|
| `id` | number | No |
| `name` | string | Yes |
| `manager` | string | Yes |
| `employeeCount` | number | Yes |
| `location` | string | Yes |
| `createdAt` | string (ISO instant) | No |
| `updatedAt` | string (ISO instant) | No |

**Projects** (`ProjectResponse`):

| Field | Type | Nullable? |
|---|---|---|
| `id` | number | No |
| `name` | string | Yes |
| `department` | string | Yes (display name, not a foreign key) |
| `owner` | string | Yes |
| `status` | string | Yes — see "Status fields" below |
| `startDate` | string (ISO date) | Yes |
| `endDate` | string (ISO date) | Yes — null commonly means "still active/ongoing" |
| `createdAt` | string (ISO instant) | No |
| `updatedAt` | string (ISO instant) | No |

Only `id`, `createdAt`, and `updatedAt` are guaranteed non-null on every row. Everything else can
be `null` — the seed data intentionally includes incomplete and malformed rows (see "Mock data"
below), and this API does not filter, fix, or hide any of it.

#### Status fields

`status` is typed as a plain string, not a restricted enum, on purpose — it's meant to reflect
whatever a real upstream system would report, which isn't always a known-good value. The
currently seeded mock data always uses one of the values below, but a client should not assume
`status` is limited to this set:

- Users: `ACTIVE`, `INACTIVE`, `PENDING`
- Projects: `ACTIVE`, `ON_HOLD`, `COMPLETED`, `CANCELLED`

<details>
<summary>Example response — GET /api/reports/users?size=2&sort=name,asc</summary>

```json
{
  "content": [
    {
      "id": 1,
      "name": "Olivia Bennett",
      "email": "olivia.bennett@enfos.com",
      "role": "Admin",
      "status": "ACTIVE",
      "createdDate": "2019-05-10",
      "createdAt": "2026-08-12T00:54:26.160445Z",
      "updatedAt": "2026-08-12T00:54:26.160447Z"
    },
    {
      "id": 33,
      "name": "Aisha Bello",
      "email": null,
      "role": "Analyst",
      "status": "ACTIVE",
      "createdDate": "2024-01-08",
      "createdAt": "2026-08-12T00:54:26.160860Z",
      "updatedAt": "2026-08-12T00:54:26.160862Z"
    }
  ],
  "page": 0,
  "size": 2,
  "totalElements": 40,
  "totalPages": 20
}
```
</details>

---

## Errors

Every error response — `400`, `404`, `500`, whatever the cause — has the same shape:

| Field | Type |
|---|---|
| `timestamp` | string (ISO instant) |
| `status` | number |
| `message` | string |
| `path` | string |

```json
{
  "status": 400,
  "message": "Cannot sort by 'email'. Allowed fields: [status, id, name, createdDate]",
  "path": "/api/reports/users",
  "timestamp": "2026-08-12T00:37:13.778024500Z"
}
```

**When each status is returned:**

| Status | When |
|---|---|
| `400` | `sort` param requests a field that isn't in that report's sortable list |
| `404` | Route doesn't exist |
| `500` | Unexpected server-side error |

---

## CORS

Only `http://localhost:5173` (the frontend dev server / container's published port) is allowed to
call this API from a browser, `GET` requests only. Configured in `config/CorsConfig.java`.

---

## Mock data

Each report is seeded with exactly 40 rows on startup, deliberately mixed in data quality:

| Quality | Rows | What it looks like |
|---|---|---|
| Good | 28 (~70%) | Complete, realistic values |
| Missing fields | 6 (~15%) | One or more optional-looking fields are `null` |
| Malformed/edge-case | 4 (~10%) | Long names, invalid emails, unicode/emoji, extreme dates — never invalid `status` values (see "Status fields" above) |
| Nearly empty | 2 (~5%) | Only `id` (and the always-present audit timestamps) are reliable |

`id` is always present and unique; nothing else is guaranteed. This is intentional — it's the
whole point of the mock data, so a client (this API's frontend, or anyone else calling it) has to
actually handle incomplete/messy real-world-shaped data rather than assuming a clean dataset.

Data resets to this same seed every time the app restarts — there's no persistence between runs.
