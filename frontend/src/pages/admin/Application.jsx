// src/pages/admin/Applications.jsx
import { useState } from "react";
import { Navbar } from "./Navbar";

// ── Dummy Data ─────────────────────────────
const initialApplications = [
  {
    id: 1,
    candidate: "John Doe",
    email: "john@example.com",
    job: "AI Engineer",
    company: "Google",
    applied: "May 10, 2024",
    status: "Applied",
  },
  {
    id: 2,
    candidate: "Rahul Mehta",
    email: "rahul@example.com",
    job: "Data Analyst",
    company: "Microsoft",
    applied: "May 9, 2024",
    status: "In Review",
  },
  {
    id: 3,
    candidate: "Robert Brown",
    email: "robert@example.com",
    job: "Backend Developer",
    company: "Amazon",
    applied: "May 8, 2024",
    status: "Shortlisted",
  },
  {
    id: 4,
    candidate: "Priya Sharma",
    email: "priya@example.com",
    job: "DevOps Engineer",
    company: "Docker",
    applied: "May 6, 2024",
    status: "Rejected",
  },
];

const STATUSES = ["Applied", "In Review", "Shortlisted", "Rejected"];

// ── Badge ─────────────────────────────
function Badge({ text }) {
  const map = {
    Applied: "bg-green-100 text-green-700",
    "In Review": "bg-purple-100 text-purple-700",
    Shortlisted: "bg-blue-100 text-blue-700",
    Rejected: "bg-red-100 text-red-700",
  };

  return (
    <span className={`px-2 py-1 text-[10px] font-bold rounded ${map[text] || "bg-gray-200 text-gray-700"}`}>
      {text}
    </span>
  );
}

export default function Applications() {
  const [applications, setApplications] = useState(initialApplications);
  const [search, setSearch] = useState("");
  const [statusFilter, setStatusFilter] = useState("all");

  // ── Filter ─────────────────────────────
  const filtered = applications.filter((a) => {
    const matchSearch =
      a.candidate.toLowerCase().includes(search.toLowerCase()) ||
      a.job.toLowerCase().includes(search.toLowerCase()) ||
      a.company.toLowerCase().includes(search.toLowerCase());

    const matchStatus =
      statusFilter === "all" || a.status === statusFilter;

    return matchSearch && matchStatus;
  });

  // ── Count ─────────────────────────────
  const countOf = (s) =>
    applications.filter((a) => a.status === s).length;

  // ── Update Status ─────────────────────────────
  const updateStatus = (id, status) => {
    setApplications((prev) =>
      prev.map((a) =>
        a.id === id ? { ...a, status } : a
      )
    );
  };

  return (
    <div className="p-4">
        <Navbar/>

      {/* HEADER */}
      <div className="mb-4">
        <h1 className="text-xl font-bold">All Applications</h1>
        <p className="text-sm text-gray-500">
          {applications.length} total applications
        </p>
      </div>

      {/* STATS */}
      <div className="grid grid-cols-2 md:grid-cols-4 gap-3 mb-5">
        {STATUSES.map((s) => (
          <div
            key={s}
            onClick={() =>
              setStatusFilter(statusFilter === s ? "all" : s)
            }
            className={`p-3 rounded border cursor-pointer transition
              ${statusFilter === s
                ? "bg-purple-50 border-purple-500"
                : "bg-white border-gray-200"
              }`}
          >
            <p className="text-xs font-bold text-gray-500">{s}</p>
            <p className="text-lg font-bold text-gray-800">
              {countOf(s)}
            </p>
          </div>
        ))}
      </div>

      {/* SEARCH + FILTER */}
      <div className="flex gap-2 mb-4">
        <input
          placeholder="Search candidate, job, company..."
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
          {STATUSES.map((s) => (
            <option key={s}>{s}</option>
          ))}
        </select>
      </div>

      {/* TABLE */}
      <div className="bg-white border rounded overflow-x-auto">
        <table className="w-full text-sm">
          <thead className="bg-gray-100 text-left">
            <tr>
              {[
                "#",
                "Candidate",
                "Email",
                "Job",
                "Company",
                "Applied",
                "Status",
                "Update",
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
                  colSpan="8"
                  className="text-center p-6 text-gray-400"
                >
                  No applications found
                </td>
              </tr>
            )}

            {filtered.map((a) => (
              <tr key={a.id} className="border-t hover:bg-gray-50">

                <td className="p-3 text-gray-400">{a.id}</td>
                <td className="p-3 font-semibold">{a.candidate}</td>
                <td className="p-3 text-gray-600">{a.email}</td>
                <td className="p-3 text-gray-600">{a.job}</td>
                <td className="p-3 text-gray-600">{a.company}</td>
                <td className="p-3 text-xs text-gray-500">
                  {a.applied}
                </td>

                <td className="p-3">
                  <Badge text={a.status} />
                </td>

                <td className="p-3">
                  <select
                    value={a.status}
                    onChange={(e) =>
                      updateStatus(a.id, e.target.value)
                    }
                    className="text-xs border rounded px-2 py-1"
                  >
                    {STATUSES.map((s) => (
                      <option key={s}>{s}</option>
                    ))}
                  </select>
                </td>

              </tr>
            ))}
          </tbody>
        </table>

        <div className="p-3 text-xs text-gray-400 border-t">
          Showing {filtered.length} of {applications.length}
        </div>
      </div>
    </div>
  );
}