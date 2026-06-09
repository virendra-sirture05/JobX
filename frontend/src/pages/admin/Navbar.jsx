import { NavLink } from "react-router-dom";

/* ---------------- NAVBAR ---------------- */
export function Navbar() {
  const linkClass = ({ isActive }) =>
    `px-3 py-2 rounded text-sm font-medium transition ${
      isActive ? "bg-purple-600 text-white" : "text-gray-700 hover:bg-gray-200"
    }`;

  return (
    <div className="flex gap-2 flex-wrap bg-white border p-3 rounded mb-6">
      <NavLink to="/admin/dashboard" className={linkClass}>
        Dashboard
      </NavLink>

      <NavLink to="/admin/users" className={linkClass}>
        Users
      </NavLink>

      <NavLink to="/admin/jobs" className={linkClass}>
        Jobs
      </NavLink>

      <NavLink to="/admin/applications" className={linkClass}>
        Applications
      </NavLink>

      <NavLink to="/admin/categories" className={linkClass}>
        Categories
      </NavLink>

      <NavLink to="/admin/login-activity" className={linkClass}>
        Login Activity
      </NavLink>
    </div>
  );
}