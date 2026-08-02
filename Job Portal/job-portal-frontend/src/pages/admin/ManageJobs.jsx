import { useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import { deleteJob, toggleJobStatus } from "../../redux/slices/jobSlice";
import { Navbar } from "./Navbar";

export default function ManageJobs() {
  const dispatch = useDispatch();

  const jobs = useSelector((state) => state.jobs.jobs);

  const [search, setSearch] = useState("");
  const [confirmId, setConfirmId] = useState(null);

  // Search Jobs
  const filteredJobs = jobs.filter(
    (job) =>
      job.title.toLowerCase().includes(search.toLowerCase()) ||
      job.company.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className="p-5">
      <Navbar />

      {/* Heading */}
      <h1 className="text-2xl font-bold mb-2">
        Manage Jobs
      </h1>

      <p className="text-gray-500 mb-4">
        Total Jobs : {jobs.length}
      </p>

      {/* Search */}
      <input
        type="text"
        placeholder="Search Job..."
        value={search}
        onChange={(e) => setSearch(e.target.value)}
        className="border p-2 rounded w-72 mb-4"
      />

      {/* Table */}
      <div className="bg-white border rounded">
        <table className="w-full">

          <thead className="bg-gray-100">
            <tr>
              <th className="p-3 text-left">ID</th>
              <th className="p-3 text-left">Title</th>
              <th className="p-3 text-left">Company</th>
              <th className="p-3 text-left">Category</th>
              <th className="p-3 text-left">Status</th>
              <th className="p-3 text-left">Action</th>
            </tr>
          </thead>

          <tbody>

            {filteredJobs.map((job) => (
              <tr
                key={job.id}
                className="border-t"
              >
                <td className="p-3">{job.id}</td>

                <td className="p-3">
                  {job.title}
                </td>

                <td className="p-3">
                  {job.company}
                </td>

                <td className="p-3">
                  {job.category}
                </td>

                <td className="p-3">
                  {job.status}
                </td>

                <td className="p-3 flex gap-2">

                  {/* Toggle Status */}
                  <button
                    onClick={() =>
                      dispatch(
                        toggleJobStatus(job.id)
                      )
                    }
                    className="px-2 py-1 border rounded text-sm"
                  >
                    {job.status === "Active"
                      ? "Close"
                      : "Open"}
                  </button>

                  {/* Delete */}
                  {confirmId === job.id ? (
                    <>
                      <button
                        onClick={() => {
                          dispatch(
                            deleteJob(job.id)
                          );
                          setConfirmId(null);
                        }}
                        className="bg-red-500 text-white px-2 py-1 rounded text-sm"
                      >
                        Confirm
                      </button>

                      <button
                        onClick={() =>
                          setConfirmId(null)
                        }
                        className="border px-2 py-1 rounded text-sm"
                      >
                        Cancel
                      </button>
                    </>
                  ) : (
                    <button
                      onClick={() =>
                        setConfirmId(job.id)
                      }
                      className="border border-red-500 text-red-500 px-2 py-1 rounded text-sm"
                    >
                      Delete
                    </button>
                  )}

                </td>
              </tr>
            ))}

          </tbody>

        </table>
      </div>
    </div>
  );
}