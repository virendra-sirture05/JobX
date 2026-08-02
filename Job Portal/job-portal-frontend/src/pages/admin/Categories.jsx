// src/pages/admin/Categories.jsx
import { useState } from "react";
import { Navbar } from "./Navbar";

// ── Dummy Data ─────────────────────────────
const initialCategories = [
  {
    id: 1,
    title: "Engineering",
    description: "Software & Hardware roles",
    totalJobs: 12,
  },
  {
    id: 2,
    title: "Data Science",
    description: "Data & Analytics roles",
    totalJobs: 8,
  },
  {
    id: 3,
    title: "Design",
    description: "UI/UX & Graphic roles",
    totalJobs: 6,
  },
  {
    id: 4,
    title: "DevOps",
    description: "Cloud & Infrastructure",
    totalJobs: 5,
  },
];

// ── Main Component ─────────────────────────────
export default function Categories() {
  const [categories, setCategories] = useState(initialCategories);

  const [showForm, setShowForm] = useState(false);
  const [editId, setEditId] = useState(null);
  const [title, setTitle] = useState("");
  const [desc, setDesc] = useState("");
  const [confirmId, setConfirmId] = useState(null);

  // ── Add / Update ─────────────────────────────
  const handleSubmit = () => {
    if (!title.trim()) return alert("Title required");

    if (editId) {
      setCategories((prev) =>
        prev.map((c) =>
          c.id === editId ? { ...c, title, description: desc } : c
        )
      );
    } else {
      const newCategory = {
        id: Date.now(),
        title,
        description: desc,
        totalJobs: 0,
      };
      setCategories((prev) => [newCategory, ...prev]);
    }

    setTitle("");
    setDesc("");
    setEditId(null);
    setShowForm(false);
  };

  // ── Edit ─────────────────────────────
  const handleEdit = (c) => {
    setEditId(c.id);
    setTitle(c.title);
    setDesc(c.description);
    setShowForm(true);
  };

  // ── Delete ─────────────────────────────
  const handleDelete = (id) => {
    setCategories((prev) => prev.filter((c) => c.id !== id));
    setConfirmId(null);
  };

  return (
    <div className="p-4">
        <Navbar/>
      {/* HEADER */}
      <div className="flex justify-between mb-5">
        <div>
          <h1 className="text-xl font-bold">Job Categories</h1>
          <p className="text-sm text-gray-500">
            {categories.length} categories available
          </p>
        </div>

        <button
          onClick={() => {
            setShowForm(!showForm);
            setEditId(null);
            setTitle("");
            setDesc("");
          }}
          className="bg-purple-600 text-white px-4 py-2 rounded text-sm"
        >
          {showForm && !editId ? "Cancel" : "+ Add Category"}
        </button>
      </div>

      {/* FORM */}
      {showForm && (
        <div className="bg-white border border-purple-400 p-4 rounded mb-5">

          <h2 className="font-bold text-purple-700 mb-3">
            {editId ? "Edit Category" : "Add Category"}
          </h2>

          <div className="grid md:grid-cols-2 gap-3 mb-3">
            <input
              className="border px-3 py-2 rounded"
              placeholder="Category Title"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
            />

            <input
              className="border px-3 py-2 rounded"
              placeholder="Description"
              value={desc}
              onChange={(e) => setDesc(e.target.value)}
            />
          </div>

          <div className="flex gap-2">
            <button
              onClick={handleSubmit}
              className="bg-purple-600 text-white px-4 py-2 rounded text-sm"
            >
              {editId ? "Save" : "Add"}
            </button>

            <button
              onClick={() => setShowForm(false)}
              className="border px-4 py-2 rounded text-sm"
            >
              Cancel
            </button>
          </div>
        </div>
      )}

      {/* GRID */}
      <div className="grid md:grid-cols-3 gap-4 mb-6">

        {categories.map((c) => (
          <div
            key={c.id}
            className="border rounded p-4 bg-white hover:shadow-md transition"
          >
            <div className="flex justify-between items-start">
              <h3 className="font-bold">{c.title}</h3>

              <span className="text-xs bg-purple-100 text-purple-700 px-2 py-1 rounded">
                {c.totalJobs} jobs
              </span>
            </div>

            <p className="text-sm text-gray-500 mt-2">
              {c.description}
            </p>

            <div className="flex gap-2 mt-4">

              <button
                onClick={() => handleEdit(c)}
                className="text-xs border px-2 py-1 rounded text-purple-600 border-purple-600"
              >
                Edit
              </button>

              {confirmId === c.id ? (
                <>
                  <button
                    onClick={() => handleDelete(c.id)}
                    className="text-xs bg-red-600 text-white px-2 py-1 rounded"
                  >
                    Confirm
                  </button>

                  <button
                    onClick={() => setConfirmId(null)}
                    className="text-xs border px-2 py-1 rounded"
                  >
                    Cancel
                  </button>
                </>
              ) : (
                <button
                  onClick={() => setConfirmId(c.id)}
                  className="text-xs border border-red-500 text-red-500 px-2 py-1 rounded"
                >
                  Delete
                </button>
              )}

            </div>
          </div>
        ))}

      </div>

      {/* TABLE VIEW */}
      <div className="bg-white border rounded overflow-x-auto">
        <div className="p-3 font-bold border-b">
          Categories Table View
        </div>

        <table className="w-full text-sm">
          <thead className="bg-gray-100 text-left">
            <tr>
              {["#", "Title", "Description", "Jobs", "Actions"].map((h) => (
                <th key={h} className="p-3 text-gray-600">
                  {h}
                </th>
              ))}
            </tr>
          </thead>

          <tbody>
            {categories.map((c) => (
              <tr key={c.id} className="border-t hover:bg-gray-50">

                <td className="p-3 text-gray-400">{c.id}</td>
                <td className="p-3 font-semibold">{c.title}</td>
                <td className="p-3 text-gray-600">{c.description}</td>

                <td className="p-3">
                  <span className="bg-purple-100 text-purple-700 px-2 py-1 text-xs rounded">
                    {c.totalJobs}
                  </span>
                </td>

                <td className="p-3 flex gap-2">
                  <button
                    onClick={() => handleEdit(c)}
                    className="text-xs border px-2 py-1 rounded text-purple-600"
                  >
                    Edit
                  </button>

                  <button
                    onClick={() => setConfirmId(c.id)}
                    className="text-xs border border-red-500 text-red-500 px-2 py-1 rounded"
                  >
                    Delete
                  </button>
                </td>

              </tr>
            ))}
          </tbody>

        </table>
      </div>

    </div>
  );
}