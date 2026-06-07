import { useState } from "react";
import { useSelector } from "react-redux";

export default function Jobs() {
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

    const matchesCategory =
      category === "" || job.category === category;

    return matchesSearch && matchesLocation && matchesCategory;
  });

  return (
    <div className="flex min-h-screen bg-gray-100">

      {/* LEFT FILTER PANEL */}
      <div className="w-64 border-r bg-white p-4">
        <h3 className="font-bold mb-4">FILTERS</h3>

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
          className="w-full text-white p-2"
          style={{ backgroundColor: "#1a56a0" }}
          onClick={() => {}}
        >
          Apply
        </button>
      </div>

      {/* RIGHT SECTION */}
      <div className="flex-1 p-4">

        <div className="text-white flex justify-end p-3 mb-4" style={{ backgroundColor: "#1a56a0" }}>
          Job Board
        </div>

        <div className="flex gap-2 mb-4">
          <input
            type="text"
            placeholder="Search title, company, skills..."
            className="flex-1 border p-2"
            value={search}
            onChange={(e) => setSearch(e.target.value)}
          />

          <button className="text-white px-6" style={{ backgroundColor: "#1a56a0" }}>
            Search
          </button>

          <span className="text-blue-700">
            {filteredJobs.length} Active
          </span>
        </div>

        <table className="w-full border border-collapse">
          <thead>
            <tr className="bg-gray-100">
              <th className="border p-2">ID</th>
              <th className="border p-2">Title</th>
              <th className="border p-2">Company</th>
              <th className="border p-2">Location</th>
              <th className="border p-2">Category</th>
              <th className="border p-2">Actions</th>
            </tr>
          </thead>

          <tbody>
            {filteredJobs.length === 0 ? (
              <tr>
                <td colSpan="6" className="border p-4 text-center text-gray-600">
                  No jobs match the current filters. Please clear the filters or add a new job.
                </td>
              </tr>
            ) : (
              filteredJobs.map((job) => (
                <tr key={job.id}>
                  <td className="border p-2">{job.id}</td>
                  <td className="border p-2">
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
                  <td className="border p-2">{job.company}</td>
                  <td className="border p-2">{job.location}</td>
                  <td className="border p-2">{job.category}</td>

                  <td className="border p-2">
                    <button
                      className="bg-blue-700 text-white px-2 py-1 mr-2"
                      onClick={() => {}}
                    >
                      Apply
                    </button>

                    <button className="border px-2 py-1 mr-2">Save</button>

                    <button className="border px-2 py-1">Refer</button>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>

      </div>
    </div>
  );
}