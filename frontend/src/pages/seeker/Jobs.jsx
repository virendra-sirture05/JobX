import { useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { applyJob } from "../../redux/slices/jobSlice";

export default function Jobs() {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const jobs = useSelector((state) => state.jobs.jobs);

  const [search, setSearch] = useState("");
  const [location, setLocation] = useState("");
  const [category, setCategory] = useState("");

  const filteredJobs = jobs.filter((job) => {
    const matchesSearch =
      job.title.toLowerCase().includes(search.toLowerCase()) ||
      job.company.toLowerCase().includes(search.toLowerCase()) ||
      job.skills?.toLowerCase().includes(search.toLowerCase());

    const matchesLocation =
      location === "" ||
      job.location.toLowerCase().includes(location.toLowerCase());

    const matchesCategory = category === "" || job.category === category;

    return matchesSearch && matchesLocation && matchesCategory;
  });

  return (
    <div className="flex min-h-screen bg-gray-100">
      <div className="w-64 border-r bg-white p-4">
        <h3 className="text-lg font-bold mb-4">FILTERS</h3>

        <div className="mb-4">
          <label>Location</label>
          <input
            type="text"
            placeholder="City..."
            className="w-full border p-2"
            value={location}
            onChange={(e) => setLocation(e.target.value)}
          />
        </div>

        <div className="mb-4">
          <label>Category</label>
          <select
            className="w-full border p-2"
            value={category}
            onChange={(e) => setCategory(e.target.value)}
          >
            <option value="">All</option>
            <option>Engineering</option>
            <option>Data Science</option>
            <option>Design</option>
            <option>Marketing</option>
          </select>
        </div>

        <button
          className="w-full text-white p-2 rounded"
          style={{ backgroundColor: "#1a56a0" }}
          onClick={() => {}}
        >
          Apply Filters
        </button>

        <div className="border-t mt-6 pt-4">
          <h4 className="text-label mb-3">QUICK LINKS</h4>

          <button
            onClick={() => navigate("/seeker/dashboard")}
            className="w-full text-left px-2 py-2 hover:bg-gray-100 rounded text-sm"
          >
            Dashboard
          </button>

          <button
            onClick={() => navigate("/seeker/applied-jobs")}
            className="w-full text-left px-2 py-2 hover:bg-gray-100 rounded text-sm"
          >
            Applied Jobs
          </button>
        </div>
      </div>

      <div className="flex-1 p-4">
        <div
          className="text-white flex justify-between items-center p-3 mb-4 rounded"
          style={{ backgroundColor: "#1a56a0" }}
        >
          <h2>Job Board</h2>
          <span className="text-xs">{filteredJobs.length} Active</span>
        </div>

        <div className="mb-4">
          <input
            type="text"
            placeholder="Search title, company, skills..."
            className="w-full border p-2 rounded"
            value={search}
            onChange={(e) => setSearch(e.target.value)}
          />
        </div>

        <div className="bg-white border rounded overflow-x-auto">
          <table className="w-full border-collapse">
            <thead>
              <tr className="bg-gray-100">
                <th className="border p-3 text-left text-xs font-semibold text-gray-800">
                  ID
                </th>
                <th className="border p-3 text-left text-xs font-semibold text-gray-800">
                  Title
                </th>
                <th className="border p-3 text-left text-xs font-semibold text-gray-800">
                  Company
                </th>
                <th className="border p-3 text-left text-xs font-semibold text-gray-800">
                  Location
                </th>
                <th className="border p-3 text-left text-xs font-semibold text-gray-800">
                  Category
                </th>
                <th className="border p-3 text-left text-xs font-semibold text-gray-800">
                  Actions
                </th>
              </tr>
            </thead>

            <tbody>
              {filteredJobs.length === 0 ? (
                <tr>
                  <td
                    colSpan="6"
                    className="border p-4 text-center text-gray-600"
                  >
                    No jobs match the current filters. Please clear the filters
                    or add a new job.
                  </td>
                </tr>
              ) : (
                filteredJobs.map((job) => (
                  <tr key={job.id} className="hover:bg-gray-50 border-b">
                    <td className="border-r p-3">{job.id}</td>

                    <td className="border-r p-3 font-medium">
                      <div className="flex items-center gap-2">
                        <span>{job.title}</span>

                        {job.tags && job.tags.includes("New") && (
                          <span
                            className="text-xs px-2 py-0.5 rounded text-white"
                            style={{ backgroundColor: "#1a56a0" }}
                          >
                            New
                          </span>
                        )}
                      </div>
                    </td>

                    <td className="border-r p-3">{job.company}</td>
                    <td className="border-r p-3">{job.location}</td>
                    <td className="border-r p-3">{job.category}</td>

                    <td className="p-3">
                      <div className="flex gap-2 flex-wrap">
                        <button
                          className="rounded text-white px-3 py-1 text-xs"
                          style={{ backgroundColor: "#1a56a0" }}
                          onClick={() => {
                            dispatch(applyJob(job.id));
                            navigate("/seeker/applied-jobs");
                          }}
                        >
                          Apply
                        </button>

                        <button className="border rounded px-3 py-1 text-xs hover:bg-gray-100">
                          Save
                        </button>
                      </div>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}
