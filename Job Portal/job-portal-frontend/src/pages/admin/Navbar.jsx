import { NavLink } from "react-router-dom";

/* ---------------- NAVBAR ---------------- */
export function Navbar() {
  const linkClass = ({ isActive }) =>
    `px-3 py-2 rounded text-sm font-medium transition ${
      isActive ? "border border-white text-black" : "text-gray-700 hover:text-gray"
    }`;

  return (
    <div className="flex gap-2 flex-wrap bg-white border p-3 rounded mb-6" style={{backgroundColor:"#1a56a0"}}>
      <NavLink to="/admin/dashboard " className={linkClass}>
       <div className="text-white">Dashboard</div> 
      </NavLink>

      <NavLink to="/admin/users" className={linkClass}>
        <div className="text-white">Users</div>
      </NavLink>

      <NavLink to="/admin/jobs" className={linkClass}>
        <div className="text-white">Jobs</div>
      </NavLink>

      <NavLink to="/admin/applications" className={linkClass}>
        <div className="text-white">Applications</div>
      </NavLink>

      <NavLink to="/admin/categories" className={linkClass}>
        <div className="text-white">Categories</div>
      </NavLink>

      <NavLink to="/admin/login-activity" className={linkClass}>
        <div className="text-white">Login Activity</div>
      </NavLink>
      <NavLink to="/" className={linkClass}>
        <div className="text-white">Logout</div>
      </NavLink>
    </div>
  );
}