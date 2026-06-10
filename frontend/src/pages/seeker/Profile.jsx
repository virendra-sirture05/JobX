import { useNavigate } from "react-router-dom";

function Profile() {
  const navigate = useNavigate();

  const skills = [
    { name: "React", level: 90 },
    { name: "JavaScript", level: 85 },
    { name: "Node.js", level: 80 },
    { name: "MongoDB", level: 75 },
  ];

  const experiences = [
    {
      company: "Veritas",
      role: "Software Engineer",
      duration: "Jan 2022 - Present",
    },
  ];

  const education = [
    {
      degree: "B.Tech Computer Engineering",
      duration: "2021 - 2025",
    },
  ];

  return (
    <div className="flex min-h-screen bg-gray-100">
      {/* Sidebar */}
      <div className="w-64 bg-white border-r p-4">
        <h3 className="text-lg font-bold mb-4">NAVIGATION</h3>

        <button
          onClick={() => navigate("/seeker/dashboard")}
          className="w-full text-left px-3 py-2 rounded hover:bg-gray-100 mb-2"
        >
          Dashboard
        </button>

        <button
          onClick={() => navigate("/seeker/profile")}
          className="w-full text-left px-3 py-2 rounded text-white mb-2"
          style={{ backgroundColor: "#1a56a0" }}
        >
          Profile
        </button>

        <button
          onClick={() => navigate("/seeker/jobs")}
          className="w-full text-left px-3 py-2 rounded hover:bg-gray-100 mb-2"
        >
          Search Jobs
        </button>

        <button
          onClick={() => navigate("/seeker/applied-jobs")}
          className="w-full text-left px-3 py-2 rounded hover:bg-gray-100"
        >
          Applied Jobs
        </button>
      </div>

      {/* Main Content */}
      <div className="flex-1 p-4">
        {/* Header */}
        <div
          className="flex justify-between items-center p-4 rounded text-white mb-4"
          style={{ backgroundColor: "#1a56a0" }}
        >
          <h2 className="text-2xl font-semibold">Profile & Skills</h2>

          <button
            className="bg-white px-4 py-2 rounded font-medium"
            style={{ color: "#1a56a0" }}
          >
            Edit Profile
          </button>
        </div>

        {/* Profile Card */}
        <div className="bg-white border rounded-lg p-6 mb-4 shadow-sm">
          <div className="flex justify-between items-start">
            <div className="flex gap-4">
              <div
                className="w-16 h-16 rounded-full flex items-center justify-center text-white font-bold text-xl"
                style={{ backgroundColor: "#1a56a0" }}
              >
                JD
              </div>

              <div>
                <h3 className="text-2xl font-bold">Aditi Inamdar</h3>

                <p className="text-gray-600">Software Developer</p>

                <p className="text-sm text-gray-500">
                  aditiinamdar6@gmail.com • +91 8856930560
                </p>

                <p className="text-sm text-gray-500">New York, USA</p>

                <div className="mt-3">
                  <p className="text-sm font-medium mb-1">
                    Profile Completion 85%
                  </p>

                  <div className="w-64 bg-gray-200 rounded-full h-2">
                    <div
                      className="h-2 rounded-full"
                      style={{
                        width: "85%",
                        backgroundColor: "#1a56a0",
                      }}
                    />
                  </div>
                </div>
              </div>
            </div>

            <button
              className="px-4 py-2 rounded text-white"
              style={{ backgroundColor: "#1a56a0" }}
            >
              Upload Resume
            </button>
          </div>
        </div>

        {/* Personal Info */}
        <div className="bg-white border rounded-lg p-5 mb-4 shadow-sm">
          <h3 className="text-lg font-semibold mb-4">Personal Information</h3>

          <div className="grid md:grid-cols-2 gap-4">
            <div>
              <label className="block text-sm text-gray-600 mb-1">
                Full Name
              </label>

              <input
                type="text"
                value="John Doe"
                readOnly
                className="w-full border rounded p-2"
              />
            </div>

            <div>
              <label className="block text-sm text-gray-600 mb-1">Email</label>

              <input
                type="email"
                value="aditiinamdar6@gmail.com"
                readOnly
                className="w-full border rounded p-2"
              />
            </div>

            <div>
              <label className="block text-sm text-gray-600 mb-1">Mobile</label>

              <input
                type="text"
                value="+91 8856930560"
                readOnly
                className="w-full border rounded p-2"
              />
            </div>

            <div>
              <label className="block text-sm text-gray-600 mb-1">
                LinkedIn
              </label>

              <input
                type="text"
                value="linkedin.com/in/aditiinamdar"
                readOnly
                className="w-full border rounded p-2"
              />
            </div>
          </div>

          <div className="mt-4">
            <label className="block text-sm text-gray-600 mb-1">Bio</label>

            <textarea
              rows="4"
              readOnly
              className="w-full border rounded p-2"
              value="Passionate software engineer with experience in React, Node.js and AI technologies."
            />
          </div>
        </div>

        {/* Skills */}
        <div className="bg-white border rounded-lg p-5 mb-4 shadow-sm">
          <div className="flex justify-between items-center mb-4">
            <h3 className="text-lg font-semibold">Skills</h3>

            <button className="font-medium" style={{ color: "#1a56a0" }}>
              + Add Skill
            </button>
          </div>

          <div className="space-y-4">
            {skills.map((skill, index) => (
              <div key={index}>
                <div className="flex justify-between text-sm mb-1">
                  <span>{skill.name}</span>
                  <span>{skill.level}%</span>
                </div>

                <div className="w-full bg-gray-200 rounded-full h-2">
                  <div
                    className="h-2 rounded-full"
                    style={{
                      width: `${skill.level}%`,
                      backgroundColor: "#1a56a0",
                    }}
                  />
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* Work Experience */}
        <div className="bg-white border rounded-lg p-5 mb-4 shadow-sm">
          <div className="flex justify-between items-center mb-4">
            <h3 className="text-lg font-semibold">Work Experience</h3>

            <button className="font-medium" style={{ color: "#1a56a0" }}>
              + Add Experience
            </button>
          </div>

          <div className="space-y-4">
            {experiences.map((exp, index) => (
              <div key={index} className="border rounded p-4">
                <h4 className="font-semibold">{exp.company}</h4>

                <p className="text-gray-700">{exp.role}</p>

                <p className="text-sm text-gray-500">{exp.duration}</p>
              </div>
            ))}
          </div>
        </div>

        {/* Education */}
        <div className="bg-white border rounded-lg p-5 shadow-sm">
          <div className="flex justify-between items-center mb-4">
            <h3 className="text-lg font-semibold">Education</h3>

            <button className="font-medium" style={{ color: "#1a56a0" }}>
              + Add Qualification
            </button>
          </div>

          <div className="space-y-4">
            {education.map((edu, index) => (
              <div key={index} className="border rounded p-4">
                <h4 className="font-semibold">{edu.degree}</h4>

                <p className="text-sm text-gray-500">{edu.duration}</p>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}

export default Profile;
