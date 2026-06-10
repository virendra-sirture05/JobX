import React, { useState } from "react";

const ViewApplications = () => {
  const [applications, setApplications] = useState([
    {
      id: 1,
      name: "Rahul Sharma",
      email: "rahul@gmail.com",
      phone: "9876543210",
      skills: "React, JavaScript, Tailwind",
      status: "Pending",
    },
    {
      id: 2,
      name: "Priya Singh",
      email: "priya@gmail.com",
      phone: "9123456780",
      skills: "Java, Spring Boot, MySQL",
      status: "Shortlisted",
    },
    {
      id: 3,
      name: "Amit Verma",
      email: "amit@gmail.com",
      phone: "9988776655",
      skills: "Node.js, Express, MongoDB",
      status: "Rejected",
    },
  ]);

  const [selectedResume, setSelectedResume] = useState(null);

  // View Resume
  const handleViewResume = (app) => {
    setSelectedResume(app);
  };

  // Shortlist Candidate
  const handleShortlist = (id) => {
    setApplications((prev) =>
      prev.map((app) =>
        app.id === id ? { ...app, status: "Shortlisted" } : app
      )
    );
  };

  // Reject Candidate
  const handleReject = (id) => {
    setApplications((prev) =>
      prev.map((app) =>
        app.id === id ? { ...app, status: "Rejected" } : app
      )
    );
  };

  return (
    <div className="min-h-screen bg-gray-100 p-6">
      <div className="max-w-6xl mx-auto">
        {/* Heading */}
        <h1 className="text-3xl font-bold mb-6 text-[#1a56a0]">
          View Applications
        </h1>

        {/* Application Cards */}
        <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-3">
          {applications.map((app) => (
            <div
              key={app.id}
              className="bg-white rounded-xl shadow-md p-5 border-t-4 border-[#1a56a0]"
            >
              <h2 className="text-xl font-semibold mb-3 text-[#1a56a0]">
                {app.name}
              </h2>

              <p className="mb-2">
                <span className="font-medium">Email:</span> {app.email}
              </p>

              <p className="mb-2">
                <span className="font-medium">Phone:</span> {app.phone}
              </p>

              <p className="mb-2">
                <span className="font-medium">Skills:</span> {app.skills}
              </p>

              <p className="mt-3">
                <span className="font-medium">Status:</span>{" "}
                <span
                  className={`px-3 py-1 rounded-full text-sm font-medium ${
                    app.status === "Pending"
                      ? "bg-yellow-100 text-yellow-700"
                      : app.status === "Shortlisted"
                      ? "bg-green-100 text-green-700"
                      : "bg-red-100 text-red-700"
                  }`}
                >
                  {app.status}
                </span>
              </p>

              {/* Buttons */}
              <div className="flex flex-wrap gap-2 mt-5">
                <button
                  onClick={() => handleViewResume(app)}
                  className="bg-[#1a56a0] text-white px-4 py-2 rounded hover:bg-[#15467f] transition"
                >
                  View Resume
                </button>

                <button
                  onClick={() => handleShortlist(app.id)}
                  className="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700 transition"
                >
                  Shortlist
                </button>

                <button
                  onClick={() => handleReject(app.id)}
                  className="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600 transition"
                >
                  Reject
                </button>
              </div>
            </div>
          ))}
        </div>
      </div>

      {/* Resume Modal */}
      {selectedResume && (
        <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
          <div className="bg-white w-full max-w-2xl rounded-xl shadow-xl p-6 mx-4">
            <div className="flex justify-between items-center border-b pb-3">
              <h2 className="text-2xl font-bold text-[#1a56a0]">
                Resume Preview
              </h2>

              <button
                onClick={() => setSelectedResume(null)}
                className="text-red-500 text-xl font-bold"
              >
                ✕
              </button>
            </div>

            <div className="mt-5 space-y-4">
              <div>
                <h3 className="text-xl font-semibold">
                  {selectedResume.name}
                </h3>
                <p>{selectedResume.email}</p>
                <p>{selectedResume.phone}</p>
              </div>

              <div>
                <h4 className="font-semibold text-lg">Professional Summary</h4>
                <p className="text-gray-700">
                  Passionate software developer with experience in modern web
                  technologies and problem-solving skills.
                </p>
              </div>

              <div>
                <h4 className="font-semibold text-lg">Skills</h4>
                <p>{selectedResume.skills}</p>
              </div>

              <div>
                <h4 className="font-semibold text-lg">Education</h4>
                <p>B.Tech in Computer Science Engineering</p>
                <p>XYZ Institute of Technology (2021 - 2025)</p>
              </div>

              <div>
                <h4 className="font-semibold text-lg">Experience</h4>
                <p>Frontend Developer Intern</p>
                <p>ABC Technologies (6 Months)</p>
              </div>

              <div>
                <h4 className="font-semibold text-lg">Projects</h4>
                <ul className="list-disc ml-6">
                  <li>Job Portal Application using React & Tailwind CSS</li>
                  <li>E-Commerce Website with Payment Integration</li>
                  <li>Employee Management System using MERN Stack</li>
                </ul>
              </div>

              <div>
                <h4 className="font-semibold text-lg">Certifications</h4>
                <ul className="list-disc ml-6">
                  <li>Java Full Stack Development</li>
                  <li>React.js Certification</li>
                  <li>Data Structures & Algorithms</li>
                </ul>
              </div>
            </div>

            <div className="mt-6 text-right">
              <button
                onClick={() => setSelectedResume(null)}
                className="bg-[#1a56a0] text-white px-5 py-2 rounded hover:bg-[#15467f]"
              >
                Close
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};
export default ViewApplications;