import React, { useState } from "react";

const SearchJobSeeker = () => {
  const [searchTerm, setSearchTerm] = useState("");

  const jobSeekers = [
    {
      id: 1,
      name: "Rahul Sharma",
      skills: "React, JavaScript, Node.js",
      experience: "2 Years",
      location: "Pune",
    },
    {
      id: 2,
      name: "Priya Verma",
      skills: "Java, Spring Boot, MySQL",
      experience: "3 Years",
      location: "Mumbai",
    },
    {
      id: 3,
      name: "Amit Kumar",
      skills: "HTML, CSS, React",
      experience: "1 Year",
      location: "Delhi",
    },
  ];

  const filteredJobSeekers = jobSeekers.filter(
    (seeker) =>
      seeker.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
      seeker.skills.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className="min-h-screen bg-gray-100 p-6">
      <div className="bg-white shadow-md rounded-lg p-6">
        {/* Heading */}
        <h1 className="text-3xl font-bold mb-6 text-[#1a56a0]">
          Search Job Seekers
        </h1>

        {/* Search Box */}
        <input
          type="text"
          placeholder="Search by name or skills..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className="w-full border border-gray-300 p-3 rounded-lg mb-6 focus:outline-none focus:ring-2 focus:ring-[#1a56a0]"
        />

        {/* Table */}
        <div className="overflow-x-auto">
          <table className="w-full border border-gray-300">
            <thead className="bg-[#1a56a0] text-white">
              <tr>
                <th className="p-3">Name</th>
                <th className="p-3">Skills</th>
                <th className="p-3">Experience</th>
                <th className="p-3">Location</th>
                <th className="p-3">Action</th>
              </tr>
            </thead>

            <tbody>
              {filteredJobSeekers.length > 0 ? (
                filteredJobSeekers.map((seeker) => (
                  <tr
                    key={seeker.id}
                    className="border-b text-center hover:bg-gray-50"
                  >
                    <td className="p-3">{seeker.name}</td>
                    <td className="p-3">{seeker.skills}</td>
                    <td className="p-3">{seeker.experience}</td>
                    <td className="p-3">{seeker.location}</td>

                    <td className="p-3">
                      <button className="bg-[#1a56a0] text-white px-4 py-2 rounded hover:bg-[#15467f] transition">
                        View Profile
                      </button>
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td
                    colSpan="5"
                    className="p-4 text-center text-gray-500"
                  >
                    No Job Seekers Found
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

export default SearchJobSeeker;