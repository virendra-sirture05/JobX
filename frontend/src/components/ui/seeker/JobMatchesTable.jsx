import { Button } from "@/components/ui/button";

const jobs = [
  {
    title: "AI Engineer",
    company: "Google",
    match: 92,
    skills: "-",
    credibility: "High",
  },
  {
    title: "ML Engineer",
    company: "Microsoft",
    match: 85,
    skills: "Kubernetes",
    credibility: "High",
  },
  {
    title: "Data Analyst",
    company: "Amazon",
    match: 72,
    skills: "Tableau",
    credibility: "Medium",
  },
  {
    title: "Backend Developer",
    company: "Adobe",
    match: 45,
    skills: "Java, Spring",
    credibility: "Low",
  },
];

const badgeColor = {
  High: "bg-green-100 text-green-700",
  Medium: "bg-yellow-100 text-yellow-700",
  Low: "bg-red-100 text-red-700",
};

export default function JobMatchesTable() {
  return (
    <div className="bg-white border rounded-2xl shadow-sm">
      <div className="p-5 border-b flex justify-between">
        <h2 className="font-bold text-lg">
          Top Job Matches
        </h2>

        <Button variant="ghost">
          View All
        </Button>
      </div>

      <table className="w-full">
        <thead>
          <tr className="bg-slate-50">
            <th className="p-4 text-left">Job</th>
            <th className="p-4 text-left">Company</th>
            <th className="p-4 text-left">Match %</th>
            <th className="p-4 text-left">Missing Skills</th>
            <th className="p-4 text-left">Credibility</th>
          </tr>
        </thead>

        <tbody>
          {jobs.map((job) => (
            <tr
              key={job.title}
              className="border-t"
            >
              <td className="p-4">{job.title}</td>

              <td className="p-4">{job.company}</td>

              <td className="p-4 font-bold text-green-700">
                {job.match}%
              </td>

              <td className="p-4">
                {job.skills}
              </td>

              <td className="p-4">
                <span
                  className={`
                    px-3 py-1 rounded-full text-xs
                    ${badgeColor[job.credibility]}
                  `}
                >
                  {job.credibility}
                </span>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}