// src/pages/admin/LoginActivity.jsx
import { useState } from "react";
import { Navbar } from "./Navbar";

// ── Dummy Data ─────────────────────────────
const initialLoginActivity = [
  {
    id: 1,
    username: "admin",
    time: "May 10, 09:15 AM",
    ip: "192.168.1.1",
    role: "Admin",
    status: "Success",
  },
  {
    id: 2,
    username: "ritesh@example.com",
    time: "May 10, 09:30 AM",
    ip: "192.168.1.2",
    role: "Job Seeker",
    status: "Success",
  },
  {
    id: 3,
    username: "aditi@example.com",
    time: "May 10, 10:00 AM",
    ip: "192.168.1.4",
    role: "Job Seeker",
    status: "Failed",
  },
  {
    id: 4,
    username: "rahul@example.com",
    time: "May 10, 10:30 AM",
    ip: "192.168.1.6",
    role: "Job Seeker",
    status: "Failed",
  },
];

// ── Badge ─────────────────────────────
function Badge({ text }) {
  const map = {
    Success: "bg-green-100 text-green-700",
    Failed: "bg-red-100 text-red-700",
  };

  return (
    <span className={`px-2 py-1 text-[10px] font-bold rounded ${map[text]}`}>
      {text}
    </span>
  );
}

export default function LoginActivity() {
  const [logs] = useState(initialLoginActivity);
  const [search, setSearch] = useState("");
  const [statusFilter, setStatusFilter] = useState("all");

  // ── filter ─────────────────────────────
  const filtered = logs.filter((l) => {
    const matchSearch =
      l.username.toLowerCase().includes(search.toLowerCase()) ||
      l.ip.includes(search) ||
      l.role.toLowerCase().includes(search.toLowerCase());

    const matchStatus =
      statusFilter === "all" || l.status === statusFilter;

    return matchSearch && matchStatus;
  });

  const successCount = logs.filter((l) => l.status === "Success").length;
  const failedCount = logs.filter((l) => l.status === "Failed").length;

  return (
    <div className="p-4">
        <Navbar/>
      {/* HEADER */}
      <div className="mb-4">
        <h1 className="text-xl font-bold">Login Activity</h1>
        <p className="text-sm text-gray-500">
          Monitor all login events
        </p>
      </div>

      {/* STATS */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-3 mb-5">

        <div className="p-4 border rounded bg-white border-green-200">
          <p className="text-xs text-green-600 font-bold uppercase">
            Success
          </p>
          <p className="text-2xl font-bold text-green-700">
            {successCount}
          </p>
        </div>

        <div className="p-4 border rounded bg-white border-red-200">
          <p className="text-xs text-red-600 font-bold uppercase">
            Failed
          </p>
          <p className="text-2xl font-bold text-red-700">
            {failedCount}
          </p>
        </div>

        <div className="p-4 border rounded bg-white border-purple-200">
          <p className="text-xs text-purple-600 font-bold uppercase">
            Total
          </p>
          <p className="text-2xl font-bold text-purple-700">
            {logs.length}
          </p>
        </div>

      </div>

      {/* WARNING */}
      {failedCount > 0 && (
        <div className="mb-4 p-3 bg-yellow-50 border border-yellow-300 rounded text-sm">
          ⚠️ {failedCount} failed login attempt(s) detected
        </div>
      )}

      {/* SEARCH + FILTER */}
      <div className="flex gap-2 mb-4">
        <input
          placeholder="Search username, IP, role..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          className="w-full max-w-sm px-3 py-2 border rounded"
        />

        <select
          value={statusFilter}
          onChange={(e) => setStatusFilter(e.target.value)}
          className="px-3 py-2 border rounded"
        >
          <option value="all">All</option>
          <option value="Success">Success</option>
          <option value="Failed">Failed</option>
        </select>
      </div>

      {/* TABLE */}
      <div className="bg-white border rounded overflow-x-auto">
        <table className="w-full text-sm">

          <thead className="bg-gray-100 text-left">
            <tr>
              {[
                "#",
                "Username",
                "Time",
                "IP",
                "Role",
                "Status",
              ].map((h) => (
                <th key={h} className="p-3 text-gray-600">
                  {h}
                </th>
              ))}
            </tr>
          </thead>

          <tbody>
            {filtered.length === 0 && (
              <tr>
                <td
                  colSpan="6"
                  className="text-center p-6 text-gray-400"
                >
                  No records found
                </td>
              </tr>
            )}

            {filtered.map((l) => (
              <tr key={l.id} className="border-t hover:bg-gray-50">

                <td className="p-3 text-gray-400">{l.id}</td>
                <td className="p-3 font-semibold">{l.username}</td>
                <td className="p-3 text-gray-600">{l.time}</td>
                <td className="p-3 font-mono text-xs text-gray-500">
                  {l.ip}
                </td>
                <td className="p-3 text-gray-600">{l.role}</td>

                <td className="p-3">
                  <Badge text={l.status} />
                </td>

              </tr>
            ))}
          </tbody>

        </table>

        <div className="p-3 text-xs text-gray-400 border-t">
          Showing {filtered.length} of {logs.length}
        </div>
      </div>

    </div>
  );
}