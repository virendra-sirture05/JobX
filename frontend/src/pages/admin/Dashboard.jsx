import { NavLink } from "react-router-dom";
import { Navbar } from "./Navbar";

/* ---------------- DUMMY DATA ---------------- */
const users = [
  { id: 1, role: "jobseeker" },
  { id: 2, role: "recruiter" },
  { id: 3, role: "jobseeker" },
];

const jobs = [
  { id: 1, status: "Active" },
  { id: 2, status: "Closed" },
  { id: 3, status: "Active" },
];

const applications = [{ id: 1 }, { id: 2 }, { id: 3 }, { id: 4 }];

const loginActivity = [
  { id: 1, status: "Success" },
  { id: 2, status: "Failed" },
];

/* ---------------- CARD ---------------- */
function Card({ title, value, color }) {
  return (
    <div className="bg-white p-4 border rounded shadow">
      <p className="text-gray-500 text-sm">{title}</p>
      <h2 className="text-2xl font-bold" style={{ color }}>
        {value}
      </h2>
    </div>
  );
}



/* ---------------- DASHBOARD ---------------- */
export default function AdminDashboard() {
  return (
    <div className="p-4">

      {/* 🔥 NAVBAR (TOP) */}
      <Navbar />

      {/* TITLE */}
      <h1 className="text-2xl font-bold mb-6">Dashboard</h1>

      {/* STATS */}
      <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
        <Card title="Users" value={users.length} color="#6d28d9" />

        <Card
          title="Active Jobs"
          value={jobs.filter((j) => j.status === "Active").length}
          color="#2563eb"
        />

        <Card
          title="Applications"
          value={applications.length}
          color="#059669"
        />

        <Card
          title="Failed Logins"
          value={loginActivity.filter((l) => l.status === "Failed").length}
          color="#dc2626"
        />
      </div>

      {/* INFO BOX */}
      <div className="mt-8 bg-white p-4 border rounded">
        <h2 className="font-semibold mb-2">Quick Overview</h2>
        <p className="text-gray-600 text-sm">
          Use the navigation bar above to manage users, jobs, applications,
          categories and login activity.
        </p>
      </div>
    </div>
  );
}