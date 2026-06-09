export default function ReferralTable() {
  const rows = [
    {
      name: "John Doe",
      role: "AI Engineer",
      status: "Pending",
    },
    {
      name: "Robert Brown",
      role: "Backend Developer",
      status: "Accepted",
    },
    {
      name: "Emily Davis",
      role: "UX Designer",
      status: "Rejected",
    },
  ];

  return (
    <div className="bg-white rounded-2xl border shadow-sm mt-8">
      <div className="p-6 border-b">
        <h2 className="font-semibold text-lg">
          Referral Requests
        </h2>
      </div>

      <table className="w-full">
        <thead>
          <tr className="bg-gray-50">
            <th className="text-left p-4">
              Candidate
            </th>
            <th className="text-left p-4">
              Job
            </th>
            <th className="text-left p-4">
              Status
            </th>
            <th className="text-left p-4">
              Action
            </th>
          </tr>
        </thead>

        <tbody>
          {rows.map((row) => (
            <tr
              key={row.name}
              className="border-t"
            >
              <td className="p-4">
                {row.name}
              </td>

              <td className="p-4">
                {row.role}
              </td>

              <td className="p-4">
                <span
                  className={`
                    px-3 py-1 rounded-full text-xs

                    ${
                      row.status === "Accepted"
                        ? "bg-green-100 text-green-700"
                        : row.status === "Rejected"
                        ? "bg-red-100 text-red-700"
                        : "bg-yellow-100 text-yellow-700"
                    }
                  `}
                >
                  {row.status}
                </span>
              </td>

              <td className="p-4 flex gap-2">
                <button
                  className="
                    bg-green-600
                    text-white
                    px-3
                    py-1
                    rounded-lg
                  "
                >
                  Accept
                </button>

                <button
                  className="
                    bg-red-600
                    text-white
                    px-3
                    py-1
                    rounded-lg
                  "
                >
                  Reject
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
} 