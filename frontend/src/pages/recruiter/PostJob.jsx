import { useState } from "react";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { addJob } from "../../pages/redux/jobSlice";

export default function PostJob() {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const [job, setJob] = useState({
    title: "",
    company: "",
    location: "",
    category: "Engineering",
    skills: "",
    description: "",
  });

  const handleChange = (e) => {
    setJob({
      ...job,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = () => {
    if (!job.title.trim() || !job.company.trim() || !job.location.trim()) {
      alert("Please fill out the job title, company, and location before posting.");
      return;
    }

    dispatch(
      addJob({
        id: Date.now(),
        title: job.title,
        company: job.company,
        location: job.location,
        category: job.category,
        skills: job.skills,
        description: job.description,
        tags: ["New"],
      })
    );

    alert("Job Posted Successfully");
    navigate("/seeker/jobs");

    setJob({
      title: "",
      company: "",
      location: "",
      category: "Engineering",
      skills: "",
      description: "",
    });
  };

  const handleCancel = () => {
    setJob({
      title: "",
      company: "",
      location: "",
      category: "Engineering",
      skills: "",
      description: "",
    });
  };

  return (
    <div className="min-h-screen bg-gray-100 flex justify-center items-center p-6">
      <div className="w-full max-w-4xl bg-white border shadow-sm p-8">

        <div
          className="inline-block px-3 py-1 text-sm mb-4 border"
          style={{ borderColor: "#1a56a0", color: "#1a56a0" }}
        >
          RECRUITER — Post New Job
        </div>

        <h2 className="text-2xl font-semibold mb-2" style={{ color: "#1a56a0" }}>
          Post New Job
        </h2>

        <div className="border-b-2 mb-6" style={{ borderColor: "#1a56a0" }}></div>

        <div className="grid grid-cols-2 gap-4 mb-4">
          <div>
            <label className="block mb-1">Job Title</label>
            <input
              type="text"
              name="title"
              value={job.title}
              onChange={handleChange}
              placeholder="e.g. AI Engineer"
              className="w-full border p-2"
            />
          </div>

          <div>
            <label className="block mb-1">Company Name</label>
            <input
              type="text"
              name="company"
              value={job.company}
              onChange={handleChange}
              placeholder="e.g. Google"
              className="w-full border p-2"
            />
          </div>
        </div>

        <div className="grid grid-cols-2 gap-4 mb-4">
          <div>
            <label className="block mb-1">Location</label>
            <input
              type="text"
              name="location"
              value={job.location}
              onChange={handleChange}
              placeholder="City / Remote"
              className="w-full border p-2"
            />
          </div>

          <div>
            <label className="block mb-1">Job Category</label>
            <select
              name="category"
              value={job.category}
              onChange={handleChange}
              className="w-full border p-2"
            >
              <option>Engineering</option>
              <option>Data Science</option>
              <option>Design</option>
              <option>Marketing</option>
            </select>
          </div>
        </div>

        <div className="mb-4">
          <label className="block mb-1">Skills Required</label>
          <input
            type="text"
            name="skills"
            value={job.skills}
            onChange={handleChange}
            placeholder="Python, React, SQL..."
            className="w-full border p-2"
          />
        </div>

        <div className="mb-6">
          <label className="block mb-1">Job Description</label>
          <textarea
            rows="5"
            name="description"
            value={job.description}
            onChange={handleChange}
            placeholder="Describe the role and responsibilities..."
            className="w-full border p-2"
          />
        </div>

        <div className="flex gap-3">
          <button
            onClick={handleSubmit}
            className="text-white px-5 py-2 rounded"
            style={{ backgroundColor: "#1a56a0" }}
          >
            Post Job
          </button>

          <button
            onClick={handleCancel}
            className="border px-5 py-2 rounded"
            style={{ borderColor: "#1a56a0", color: "#1a56a0" }}
          >
            Cancel
          </button>
        </div>
      </div>
    </div>
  );
}