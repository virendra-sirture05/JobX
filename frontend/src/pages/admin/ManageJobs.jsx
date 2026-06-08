// src/pages/admin/ManageJobs.jsx

import { useState } from "react";
import { useSelector, useDispatch } from "react-redux";

import {
  deleteJob,
  toggleJobStatus,
} from "../../redux/slices/jobSlice";
import { Navbar } from "./Navbar";

// ── Badge Component ─────────────────────────────
function Badge({ text }) {
  const map = {
    Active: "bg-green-100 text-green-700",
    Closed: "bg-red-100 text-red-700",
  };

  return (
    <span
      className={`px-2 py-1 text-[10px] font-bold rounded ${
        map[text] || "bg-gray-200 text-gray-700"
      }`}
    >
      {text}
    </span>
  );
}

export default function ManageJobs() {
  const dispatch = useDispatch();

  const jobs = useSelector((state) => state.jobs.jobs);

  const [search, setSearch] = useState("");
  const [catFilter, setCatFilter] = useState("all");
  const [confirmId, setConfirmId] = useState(null);

  // Categories
  const categories = [
    "all",
    ...new Set(jobs.map((job) => job.category)),
  ];

  // Search + Filter
  const filteredJobs = jobs.filter((job) => {
    const matchSearch =
      job.title.toLowerCase().includes(search.toLowerCase()) ||
      job.company.toLowerCase().includes(search.toLowerCase());

    const matchCategory =
      catFilter === "all" ||
      job.category === catFilter;

    return matchSearch && matchCategory;
  });

  // Toggle Status
  const handleToggleStatus = (id) => {
    dispatch(toggleJobStatus(id));
  };

  // Delete Job
  const handleDeleteJob = (id) => {
    dispatch(deleteJob(id));
    setConfirmId(null);
  };

  return (
    <div className="p-5">
      <Navbar/>
      {/* HEADER */}
      <div className="flex flex-col md:flex-row md:justify-between gap-4 mb-5">

        <div>
          <h1 className="text-2xl font-bold">
            Manage Jobs
          </h1>

          <p className="text-sm text-gray-500">
            {jobs.length} Total Jobs •{" "}
            {
              jobs.filter(
                (job) => job.status === "Active"
              ).length
            }{" "}
            Active Jobs
          </p>
        </div>

        {/* Category Filter */}
        <div className="flex flex-wrap gap-2">
          {categories.map((cat) => (
            <button
              key={cat}
              onClick={() => setCatFilter(cat)}
              className={`px-3 py-1 rounded text-sm border transition
              ${
                catFilter === cat
                  ? "bg-purple-600 text-white border-purple-600"
                  : "bg-white text-gray-700 border-gray-300"
              }`}
            >
              {cat === "all"
                ? `All (${jobs.length})`
                : cat}
            </button>
          ))}
        </div>
      </div>

      {/* SEARCH */}
      <input
        type="text"
        placeholder="Search by title or company..."
        value={search}
        onChange={(e) =>
          setSearch(e.target.value)
        }
        className="w-full md:w-80 border rounded px-3 py-2 mb-4"
      />

      {/* TABLE */}
      <div className="bg-white border rounded-lg overflow-hidden">

        <div className="overflow-x-auto">

          <table className="w-full text-sm">

            <thead className="bg-gray-100">
              <tr>
                <th className="p-3 text-left">#</th>
                <th className="p-3 text-left">
                  Job Title
                </th>
                <th className="p-3 text-left">
                  Company
                </th>
                <th className="p-3 text-left">
                  Category
                </th>
                <th className="p-3 text-left">
                  Referrer
                </th>
                <th className="p-3 text-left">
                  Applications
                </th>
                <th className="p-3 text-left">
                  Status
                </th>
                <th className="p-3 text-left">
                  Posted
                </th>
                <th className="p-3 text-left">
                  Actions
                </th>
              </tr>
            </thead>

            <tbody>

              {filteredJobs.length === 0 && (
                <tr>
                  <td
                    colSpan="9"
                    className="text-center p-6 text-gray-500"
                  >
                    No Jobs Found
                  </td>
                </tr>
              )}

              {filteredJobs.map((job) => (
                <tr
                  key={job.id}
                  className="border-t hover:bg-gray-50"
                >
                  <td className="p-3">
                    {job.id}
                  </td>

                  <td className="p-3 font-semibold">
                    {job.title}
                  </td>

                  <td className="p-3">
                    {job.company}
                  </td>

                  <td className="p-3">
                    {job.category}
                  </td>

                  <td className="p-3">
                    {job.referrer}
                  </td>

                  <td className="p-3">
                    <span className="bg-blue-100 text-blue-700 px-2 py-1 rounded text-xs font-bold">
                      {job.applications}
                    </span>
                  </td>

                  <td className="p-3">
                    <Badge
                      text={job.status}
                    />
                  </td>

                  <td className="p-3">
                    {job.posted}
                  </td>

                  <td className="p-3">

                    <div className="flex gap-2">

                      {/* Toggle Status */}
                      <button
                        onClick={() =>
                          handleToggleStatus(job.id)
                        }
                        className={`px-2 py-1 rounded text-xs border
                        ${
                          job.status === "Active"
                            ? "border-yellow-500 text-yellow-600"
                            : "border-green-500 text-green-600"
                        }`}
                      >
                        {job.status === "Active"
                          ? "Close"
                          : "Reopen"}
                      </button>

                      {/* Delete */}
                      {confirmId === job.id ? (
                        <>
                          <button
                            onClick={() =>
                              handleDeleteJob(job.id)
                            }
                            className="px-2 py-1 rounded text-xs bg-red-600 text-white"
                          >
                            Confirm
                          </button>

                          <button
                            onClick={() =>
                              setConfirmId(null)
                            }
                            className="px-2 py-1 rounded text-xs border"
                          >
                            Cancel
                          </button>
                        </>
                      ) : (
                        <button
                          onClick={() =>
                            setConfirmId(job.id)
                          }
                          className="px-2 py-1 rounded text-xs border border-red-500 text-red-600"
                        >
                          Delete
                        </button>
                      )}
                    </div>

                  </td>
                </tr>
              ))}
            </tbody>
          </table>

        </div>

        <div className="border-t p-3 text-xs text-gray-500">
          Showing {filteredJobs.length} of{" "}
          {jobs.length} Jobs
        </div>

      </div>
    </div>
  );
}