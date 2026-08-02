import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const SearchApplications = () => {
  const navigate = useNavigate();
  const [searchTerm, setSearchTerm] = useState("");

  const applications = [
    {
      id: 1,
      candidateName: "Rahul Sharma",
      jobTitle: "React Developer",
      experience: "2 Years",
      status: "Pending",
    },
    {
      id: 2,
      candidateName: "Priya Verma",
      jobTitle: "Java Developer",
      experience: "3 Years",
      status: "Accepted",
    },
    {
      id: 3,
      candidateName: "Amit Kumar",
      jobTitle: "Frontend Developer",
      experience: "1 Year",
      status: "Pending",
    },
    {
      id: 4,
      candidateName: "Sneha Patil",
      jobTitle: "React Developer",
      experience: "4 Years",
      status: "Rejected",
    },
  ];

  const filteredApplications = applications.filter(
    (app) =>
      app.candidateName
        .toLowerCase()
        .includes(searchTerm.toLowerCase()) ||
      app.jobTitle
        .toLowerCase()
        .includes(searchTerm.toLowerCase())
  );

  return (
    <div className="min-h-screen bg-gray-100 p-6">
      <div className="bg-white rounded-xl shadow-md p-6">
        <h1 className="text-3xl font-bold mb-6 text-[#1a56a0]">
          Search Applications
        </h1>

        {/* Search Input */}
        <input
          type="text"
          placeholder="Search candidate or job title..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className="w-full border border-gray-300 p-3 rounded-lg mb-6 focus:outline-none focus:ring-2 focus:ring-[#1a56a0]"
        />

        {/* Applications Table */}
        <div className="overflow-x-auto">
          <table className="w-full border border-gray-300">
            <thead className="bg-[#1a56a0] text-white">
              <tr>
                <th className="p-3">Candidate</th>
                <th className="p-3">Job Title</th>
                <th className="p-3">Experience</th>
                <th className="p-3">Status</th>
                <th className="p-3">Action</th>
              </tr>
            </thead>

            <tbody>
              {filteredApplications.length > 0 ? (
                filteredApplications.map((app) => (
                  <tr
                    key={app.id}
                    className="border-b text-center hover:bg-gray-50"
                  >
                    <td className="p-3">{app.candidateName}</td>
                    <td className="p-3">{app.jobTitle}</td>
                    <td className="p-3">{app.experience}</td>

                    <td className="p-3">
                      <span
                        className={`px-3 py-1 rounded-full text-sm font-medium ${
                          app.status === "Accepted"
                            ? "bg-green-100 text-green-700"
                            : app.status === "Rejected"
                            ? "bg-red-100 text-red-700"
                            : "bg-yellow-100 text-yellow-700"
                        }`}
                      >
                        {app.status}
                      </span>
                    </td>

                    <td className="p-3">
                      <button
                        onClick={() =>
                          navigate(
                            `/recruiter/applications/${app.id}`
                          )
                        }
                        className="bg-[#1a56a0] text-white px-4 py-2 rounded hover:bg-[#15467f] transition"
                      >
                        View Details
                      </button>
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td
                    colSpan="5"
                    className="p-5 text-center text-gray-500"
                  >
                    No applications found.
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default SearchApplications;