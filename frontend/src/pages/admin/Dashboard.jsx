import { Navbar } from "./Navbar";

import {
  PieChart,
  Pie,
  Cell,
  Tooltip,
  BarChart,
  Bar,
  XAxis,
  YAxis,
  
  ResponsiveContainer,
} from "recharts";

/* ---------------- DUMMY DATA ---------------- */

const users = [
  { id: 1, role: "jobseeker" },
  { id: 2, role: "recruiter" },
  { id: 3, role: "jobseeker" },
  { id: 4, role: "admin" },
];

const jobs = [
  { id: 1, status: "Active" },
  { id: 2, status: "Closed" },
  { id: 3, status: "Active" },
  { id: 4, status: "Active" },
];

const applications = [
  { month: "Jan", count: 10 },
  { month: "Feb", count: 20 },
  { month: "Mar", count: 15 },
  { month: "Apr", count: 30 },
  { month: "May", count: 45 },
  { month: "Jun", count: 60 },
];

const loginActivity = [
  { id: 1, status: "Success" },
  { id: 2, status: "Failed" },
  { id: 3, status: "Failed" },
];

/* ---------------- CARD ---------------- */

function Card({ title, value, color }) {
  return (
    <div className="bg-white p-4 border rounded shadow">
      <p className="text-gray-500 text-sm">
        {title}
      </p>

      <h2
        className="text-2xl font-bold"
        style={{ color }}
      >
        {value}
      </h2>
    </div>
  );
}

export default function AdminDashboard() {
  /* ---------- PIE CHART ---------- */

  const roleData = [
    {
      name: "Job Seekers",
      value: users.filter(
        (u) => u.role === "jobseeker"
      ).length,
    },
    {
      name: "Recruiters",
      value: users.filter(
        (u) => u.role === "recruiter"
      ).length,
    },
    {
      name: "Admins",
      value: users.filter(
        (u) => u.role === "admin"
      ).length,
    },
  ];

  const COLORS = [
    "#3b82f6",
    "#10b981",
    "#8b5cf6",
  ];

  /* ---------- BAR CHART ---------- */

  const jobData = [
    {
      name: "Active",
      count: jobs.filter(
        (j) => j.status === "Active"
      ).length,
    },
    {
      name: "Closed",
      count: jobs.filter(
        (j) => j.status === "Closed"
      ).length,
    },
  ];

  return (
    <div className="p-4 bg-gray-100 min-h-screen">

      <Navbar />

      {/* TITLE */}

      <h1 className="text-2xl font-bold mb-6">
        Admin Dashboard
      </h1>

      {/* CARDS */}

      <div className="grid grid-cols-2 md:grid-cols-4 gap-4 mb-8">

        <Card
          title="Users"
          value={users.length}
          color="#7c3aed"
        />

        <Card
          title="Active Jobs"
          value={
            jobs.filter(
              (j) => j.status === "Active"
            ).length
          }
          color="#2563eb"
        />

        <Card
          title="Applications"
          value={applications.length}
          color="#059669"
        />

        <Card
          title="Failed Logins"
          value={
            loginActivity.filter(
              (l) => l.status === "Failed"
            ).length
          }
          color="#dc2626"
        />

      </div>

      {/* CHARTS */}

      <div className="grid md:grid-cols-2 gap-6">

        {/* PIE CHART */}

        <div className="bg-white p-4 rounded shadow">

          <h2 className="font-semibold mb-4">
            Users By Role
          </h2>

          <ResponsiveContainer
            width="100%"
            height={300}
          >
            <PieChart>

              <Pie
                data={roleData}
                dataKey="value"
                outerRadius={100}
                label
              >
                {roleData.map(
                  (entry, index) => (
                    <Cell
                      key={index}
                      fill={COLORS[index]}
                    />
                  )
                )}
              </Pie>

              <Tooltip />

            </PieChart>
          </ResponsiveContainer>

        </div>

        {/* BAR CHART */}

        <div className="bg-white p-4 rounded shadow">

          <h2 className="font-semibold mb-4">
            Jobs Status
          </h2>

          <ResponsiveContainer
            width="100%"
            height={300}
          >
            <BarChart data={jobData}>

              <XAxis dataKey="name" />

              <YAxis />

              <Tooltip />

              <Bar
                dataKey="count"
                fill="#2563eb"
              />

            </BarChart>
          </ResponsiveContainer>

        </div>

        

      </div>

    </div>
  );
}