import React from "react";

const RecruiterResources = () => {
  const resources = [
    {
      id: 1,
      type: "PDF",
      title: "AI Interview Guide",
      size: "2.4 MB",
    },
    {
      id: 2,
      type: "PDF",
      title: "HR Policy Manual",
      size: "1.8 MB",
    },
    {
      id: 3,
      type: "DOC",
      title: "Onboarding Checklist",
      size: "950 KB",
    },
  ];

  return (
    <div className="min-h-screen bg-gray-100">
      {/* Header */}
      <div className="bg-[#1a56a0] text-white px-6 py-4 shadow-md">
        <h1 className="text-2xl font-bold">
          Job Referral Platform — Resources
        </h1>
      </div>

      <div className="max-w-6xl mx-auto p-6">
        {/* Breadcrumb */}
        <div className="inline-block border border-[#1a56a0] text-[#1a56a0] px-4 py-2 mb-6 bg-blue-50 rounded">
          RECRUITER — Resources Library
        </div>

        {/* Top Section */}
        <div className="flex justify-between items-center mb-6">
          <h2 className="text-3xl font-bold text-[#1a56a0]">
            Resources Library
          </h2>

          <button className="bg-[#1a56a0] hover:bg-[#15467f] text-white px-5 py-2 rounded transition">
            + Add Resource
          </button>
        </div>

        {/* Resource List */}
        <div className="space-y-4">
          {resources.map((resource) => (
            <div
              key={resource.id}
              className="bg-white border-l-4 border-[#1a56a0] rounded-lg p-4 flex justify-between items-center shadow-sm hover:shadow-md transition"
            >
              <div className="flex items-center gap-4">
                {/* File Type Badge */}
                <div className="bg-blue-100 text-[#1a56a0] font-bold px-3 py-2 rounded">
                  {resource.type}
                </div>

                <div>
                  <h3 className="font-semibold text-lg text-[#1a56a0]">
                    {resource.title}
                  </h3>

                  <p className="text-gray-500">
                    {resource.type} • {resource.size}
                  </p>
                </div>
              </div>

              {/* Action Buttons */}
              <div className="flex gap-3">
                <button className="border border-[#1a56a0] text-[#1a56a0] px-4 py-2 rounded hover:bg-blue-50 transition">
                  View
                </button>

                <button className="border border-[#1a56a0] text-[#1a56a0] px-4 py-2 rounded hover:bg-blue-50 transition">
                  Edit
                </button>

                <button className="border border-red-600 text-red-600 px-4 py-2 rounded hover:bg-red-50 transition">
                  Delete
                </button>
              </div>
            </div>
          ))}
        </div>

        {/* Database Note */}
        <div className="mt-8 bg-white p-4 rounded-lg shadow-sm border-l-4 border-[#1a56a0]">
          <p className="text-sm text-gray-600 italic">
            resources: resource_id, referrer_id INT, title VARCHAR(255),
            resource_url TEXT, is_premium TINYINT
          </p>
        </div>
      </div>
    </div>
  );
};

export default RecruiterResources;