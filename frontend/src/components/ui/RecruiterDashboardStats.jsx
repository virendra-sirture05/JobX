const stats = [
  {
    title: "Jobs Posted",
    value: 8,
  },
  {
    title: "Applicants",
    value: 34,
  },
  {
    title: "Referrals",
    value: 12,
  },
  {
    title: "Messages",
    value: 5,
  },
];

export default function DashboardStats() {
  return (
    <div className="grid md:grid-cols-4 gap-6">
      {stats.map((stat) => (
        <div
          key={stat.title}
          className="
            bg-white
            rounded-2xl
            border
            p-6
            shadow-sm
          "
        >
          <p className="text-gray-500 text-sm">
            {stat.title}
          </p>

          <h2 className="text-3xl font-bold text-[#1a56a0] mt-2">
            {stat.value}
          </h2>
        </div>
      ))}
    </div>
  );
}