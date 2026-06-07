import React from "react";
import { Link } from "react-router-dom";

export default function Dashboard() {
  const recentApplications = [
    {
      id: 1,
      title: "AI Engineer",
      company: "Google",
      date: "May 10, 2024",
      status: "Applied",
    },
    {
      id: 2,
      title: "Data Analyst",
      company: "Microsoft",
      date: "May 9, 2024",
      status: "In Review",
    },
    {
      id: 3,
      title: "Backend Dev",
      company: "Amazon",
      date: "May 8, 2024",
      status: "Shortlisted",
    },
    {
      id: 4,
      title: "UX Designer",
      company: "Adobe",
      date: "May 7, 2024",
      status: "Rejected",
    },
  ];

  return (
    <div className="flex min-h-screen bg-[#f3f3f3]">
      {/* Sidebar */}
      <div className="w-64 bg-white border-r">
        <div
          className="text-white p-3 font-bold"
          style={{ backgroundColor: "#1a56a0" }}
        >
          Job Referral Platform
        </div>

        <div className="text-xs text-gray-500 p-2 font-bold">MAIN</div>

        <ul>
          <li
            className="p-3 border-b hover:bg-gray-200"
            style={{
              backgroundColor: "#dce8f5",
              color: "#1a56a0",
              fontWeight: "600",
            }}
          >
            <Link className="block w-full" to="/seeker/dashboard">
              Dashboard
            </Link>
          </li>

          <li className="p-3 border-b hover:bg-gray-100">
            <Link className="block w-full" to="/seeker/jobs">
              Search Jobs
            </Link>
          </li>

          <li className="p-3 border-b hover:bg-gray-100">
            <Link className="block w-full" to="/seeker/applied-jobs">
              Applied Jobs
            </Link>
          </li>

          <li className="p-3 border-b hover:bg-gray-100">
            <Link className="block w-full" to="/seeker/saved-jobs">
              Saved Jobs
            </Link>
          </li>
        </ul>

        <div className="text-xs text-gray-500 p-2 font-bold">PROFILE</div>

        <ul>
          <li className="p-3 border-b hover:bg-gray-100">
            <Link to="/seeker/profile">My Profile</Link>
          </li>

          <li className="p-3 border-b hover:bg-gray-100">
            <Link to="/seeker/skills">Skills</Link>
          </li>

          <li className="p-3 border-b hover:bg-gray-100">
            <Link to="/seeker/resume">Resume Upload</Link>
          </li>

          <li className="p-3 border-b hover:bg-gray-100">
            <Link to="/seeker/work-experience">Work Experience</Link>
          </li>

          <li className="p-3 border-b hover:bg-gray-100">
            <Link to="/seeker/qualifications">Qualifications</Link>
          </li>
        </ul>

        <div className="text-xs text-gray-500 p-2 font-bold">PREMIUM</div>

        <ul>
          <li className="p-3 border-b hover:bg-gray-100">
            <Link to="/seeker/ai-match">AI Match</Link>
          </li>
        </ul>

        <div className="text-xs text-gray-500 p-2 font-bold">MORE</div>

        <ul>
          <li className="p-3 border-b hover:bg-gray-100">
            <Link to="/seeker/messages">Messages</Link>
          </li>

          <li className="p-3 border-b hover:bg-gray-100">
            <Link to="/seeker/resources">Resources</Link>
          </li>

          <li className="p-3 border-b hover:bg-gray-100">
            <Link to="/login">Logout</Link>
          </li>
        </ul>
      </div>

      {/* Main Content */}
      <div className="flex-1">
        {/* Header */}
        <div
          className="text-white flex justify-end p-3"
          style={{ backgroundColor: "#1a56a0" }}
        >
          Welcome, John Doe
        </div>

        <div className="p-4">
          <div
            className="inline-block px-3 py-1 text-xs mb-4"
            style={{
              border: "1px solid #1a56a0",
              color: "#1a56a0",
            }}
          >
            JOB SEEKER — Dashboard
          </div>

          {/* Stats */}
          <div className="grid grid-cols-4 gap-4 mb-6">
            <div className="border bg-white p-4">
              <p className="text-sm">My Applications</p>
              <h2
                className="text-3xl font-bold"
                style={{ color: "#1a56a0" }}
              >
                12
              </h2>
              <p className="text-xs text-gray-500">All time</p>
            </div>

            <div className="border bg-white p-4">
              <p className="text-sm">Saved Jobs</p>
              <h2
                className="text-3xl font-bold"
                style={{ color: "#1a56a0" }}
              >
                5
              </h2>
              <p className="text-xs text-gray-500">Active</p>
            </div>

            <div className="border bg-white p-4">
              <p className="text-sm">Referrals Sent</p>
              <h2
                className="text-3xl font-bold"
                style={{ color: "#1a56a0" }}
              >
                3
              </h2>
              <p className="text-xs text-gray-500">This month</p>
            </div>

            <div className="border bg-white p-4">
              <p className="text-sm">Unread Messages</p>
              <h2
                className="text-3xl font-bold"
                style={{ color: "#1a56a0" }}
              >
                2
              </h2>
              <p className="text-xs text-gray-500">Inbox</p>
            </div>
          </div>

          {/* Recent Applications */}
          <div>
            <div className="flex justify-between mb-2">
              <h2 className="font-bold">Recent Applications</h2>

              <Link
                to="/seeker/applied-jobs"
                style={{ color: "#1a56a0" }}
              >
                View All
              </Link>
            </div>

            <table className="w-full border-collapse border bg-white">
              <thead>
                <tr className="bg-gray-100">
                  <th className="border p-2 text-left">ID</th>
                  <th className="border p-2 text-left">Job Title</th>
                  <th className="border p-2 text-left">Company</th>
                  <th className="border p-2 text-left">Applied Date</th>
                  <th className="border p-2 text-left">Status</th>
                </tr>
              </thead>

              <tbody>
                {recentApplications.map((app) => (
                  <tr key={app.id}>
                    <td className="border p-2">{app.id}</td>
                    <td className="border p-2">{app.title}</td>
                    <td className="border p-2">{app.company}</td>
                    <td className="border p-2">{app.date}</td>
                    <td className="border p-2">
                      <span
                        className={`px-2 py-1 text-xs rounded ${
                          app.status === "Applied"
                            ? "bg-green-100 text-green-700"
                            : app.status === "In Review"
                            ? "bg-purple-100 text-purple-700"
                            : app.status === "Shortlisted"
                            ? "bg-blue-100 text-blue-700"
                            : "bg-red-100 text-red-700"
                        }`}
                      >
                        {app.status}
                      </span>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
}