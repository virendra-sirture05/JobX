import {useSelector} from "react-redux";
import {useNavigate} from "react-router-dom";

export default function AppliedJobs(){
  const appliedJobs=useSelector((state)=>state.jobs.appliedJobs);
  const navigate=useNavigate();

  return(
    <div className="flex min-h-screen bg-gray-100">
      <div className="w-64 border-r bg-white p-4">
        <h3 className="text-lg font-bold mb-4">NAVIGATION</h3>

        <div className="mb-4">
          <button
            onClick={()=>navigate("/seeker/jobs")}
            className="w-full text-left px-3 py-2 rounded hover:bg-gray-100"
            style={{color:"#1a56a0"}}
          >
            ← Back to Jobs
          </button>
        </div>

        <div className="border-t pt-4">
          <h4 className="text-label mb-3">QUICK LINKS</h4>

          <button
            onClick={()=>navigate("/seeker/dashboard")}
            className="w-full text-left px-2 py-2 hover:bg-gray-100 rounded"
          >
            Dashboard
          </button>

          <button
            onClick={()=>navigate("/seeker/jobs")}
            className="w-full text-left px-2 py-2 hover:bg-gray-100 rounded"
          >
            Search Jobs
          </button>
        </div>
      </div>

      <div className="flex-1 p-4">
        <div
          className="text-white flex justify-between items-center p-3 mb-4 rounded"
          style={{backgroundColor:"#1a56a0"}}
        >
          <h2>Applied Jobs</h2>
          <span className="text-xs">
            Total Applied: {appliedJobs.length}
          </span>
        </div>

        {appliedJobs.length===0?(
          <div className="bg-white border p-8 text-center text-gray-600 rounded">
            <p className="mb-4">No applied jobs yet.</p>

            <p>
              Apply to a role from the{" "}
              <button
                onClick={()=>navigate("/seeker/jobs")}
                style={{color:"#1a56a0"}}
                className="underline font-semibold hover:no-underline"
              >
                job board
              </button>.
            </p>
          </div>
        ):(
          <div className="bg-white border rounded overflow-x-auto">
            <table className="w-full border-collapse">
              <thead>
                <tr className="bg-gray-100">
                  <th className="border p-3 text-left text-xs font-semibold text-gray-800">ID</th>
                  <th className="border p-3 text-left text-xs font-semibold text-gray-800">Title</th>
                  <th className="border p-3 text-left text-xs font-semibold text-gray-800">Company</th>
                  <th className="border p-3 text-left text-xs font-semibold text-gray-800">Location</th>
                  <th className="border p-3 text-left text-xs font-semibold text-gray-800">Category</th>
                  <th className="border p-3 text-left text-xs font-semibold text-gray-800">Status</th>
                </tr>
              </thead>

              <tbody>
                {appliedJobs.map((job)=>(
                  <tr key={job.id} className="hover:bg-gray-50 border-b">
                    <td className="border-r p-3">{job.id}</td>
                    <td className="border-r p-3 font-medium">{job.title}</td>
                    <td className="border-r p-3">{job.company}</td>
                    <td className="border-r p-3">{job.location}</td>
                    <td className="border-r p-3">{job.category}</td>
                    <td className="p-3">
                      <span
                        className="inline-flex rounded-full px-3 py-1 text-xs font-semibold text-white"
                        style={{backgroundColor:"#1a56a0"}}
                      >
                        Applied
                      </span>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
}