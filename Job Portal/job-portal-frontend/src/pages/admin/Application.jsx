import { useState } from "react";
import { Navbar } from "./Navbar";

const applicationsData = [
  {
    id: 1,
    candidate: "Aditi",
    email: "aditi@example.com",
    job: "AI Engineer",
    company: "Google",
    applied: "May 10, 2026",
    status: "Applied",
  },
  {
    id: 2,
    candidate: "Rahul",
    email: "rahul@example.com",
    job: "Data Analyst",
    company: "Microsoft",
    applied: "May 9, 2026",
    status: "In Review",
  },
  {
    id: 3,
    candidate: "Bhavika",
    email: "bhavika@example.com",
    job: "Backend Developer",
    company: "Amazon",
    applied: "May 8, 2026",
    status: "Shortlisted",
  },
  {
    id: 4,
    candidate: "Priya",
    email: "priya@example.com",
    job: "DevOps Engineer",
    company: "Docker",
    applied: "May 6, 2026",
    status: "Rejected",
  },
];

function Badge({ status }) {
  return (
    <span
      className={`px-2 py-1 rounded text-xs font-bold
      ${
        status === "Applied"
          ? "bg-green-100 text-green-700"
          : status === "In Review"
          ? "bg-purple-100 text-purple-700"
          : status === "Shortlisted"
          ? "bg-blue-100 text-blue-700"
          : "bg-red-100 text-red-700"
      }`}
    >
      {status}
    </span>
  );
}

export default function Applications() {
  const [search, setSearch] = useState("");

  const filteredApplications = applicationsData.filter(
    (app) =>
      app.candidate
        .toLowerCase()
        .includes(search.toLowerCase()) ||
      app.job
        .toLowerCase()
        .includes(search.toLowerCase()) ||
      app.company
        .toLowerCase()
        .includes(search.toLowerCase())
  );

  return (
    <div className="p-4">
      <Navbar />

      <h1 className="text-2xl font-bold mb-4">
        Applications
      </h1>

      <input
        type="text"
        placeholder="Search Candidate, Job or Company..."
        value={search}
        onChange={(e) =>
          setSearch(e.target.value)
        }
        className="border rounded px-3 py-2 w-full max-w-md mb-4"
      />

      <div className="bg-white border rounded overflow-x-auto">
        <table className="w-full text-sm">
          <thead className="bg-gray-100">
            <tr>
              <th className="p-3 text-left">ID</th>
              <th className="p-3 text-left">Candidate</th>
              <th className="p-3 text-left">Email</th>
              <th className="p-3 text-left">Job</th>
              <th className="p-3 text-left">Company</th>
              <th className="p-3 text-left">Applied</th>
              <th className="p-3 text-left">Status</th>
            </tr>
          </thead>

          <tbody>
            {filteredApplications.map((app) => (
              <tr
                key={app.id}
                className="border-t"
              >
                <td className="p-3">{app.id}</td>
                <td className="p-3">
                  {app.candidate}
                </td>
                <td className="p-3">
                  {app.email}
                </td>
                <td className="p-3">{app.job}</td>
                <td className="p-3">
                  {app.company}
                </td>
                <td className="p-3">
                  {app.applied}
                </td>
                <td className="p-3">
                  <Badge
                    status={app.status}
                  />
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        <div className="p-3 text-xs text-gray-500 border-t">
          Total Applications:{" "}
          {filteredApplications.length}
        </div>
      </div>
    </div>
  );
}