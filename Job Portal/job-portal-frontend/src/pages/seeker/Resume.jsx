import {useState} from "react";
import {useNavigate} from "react-router-dom";

export default function Resume(){
  const navigate=useNavigate();
  const [fileName,setFileName]=useState("");

  const handleFileChange=(e)=>{
    const file=e.target.files?.[0];
    setFileName(file?.name||"");
  };

  const handleSubmit=(e)=>{
    e.preventDefault();

    if(!fileName){
      alert("Please select a file before uploading.");
      return;
    }

    alert(`Resume ready to upload: ${fileName}`);
  };

  return(
    <div className="flex bg-gray-100">
      <div className="w-64 bg-white border-r p-3">
        <h3 className="text-lg font-bold mb-3">NAVIGATION</h3>

        <button
          onClick={()=>navigate("/seeker/dashboard")}
          className="w-full text-left px-2 py-2 rounded hover:bg-gray-100 mb-1"
        >
          Dashboard
        </button>

        <button
          onClick={()=>navigate("/seeker/jobs")}
          className="w-full text-left px-2 py-2 rounded hover:bg-gray-100 mb-1"
        >
          Search Jobs
        </button>

        <button
          onClick={()=>navigate("/seeker/applied-jobs")}
          className="w-full text-left px-2 py-2 rounded hover:bg-gray-100"
        >
          Applied Jobs
        </button>
      </div>

      <div className="flex-1 p-3">
        <div
          className="flex items-center justify-between p-3 mb-3 text-white rounded"
          style={{backgroundColor:"#1a56a0"}}
        >
          <h2 className="text-xl font-semibold">Resume Upload</h2>

          <button
            onClick={()=>navigate("/seeker/jobs")}
            className="text-sm underline"
          >
            Back to Job Board
          </button>
        </div>

        <div className="max-w-2xl bg-white border rounded p-4">
          <p className="text-sm text-gray-600 mb-4">
            Select your resume file below.
          </p>

          <form onSubmit={handleSubmit}>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Resume File
            </label>

            <input
              type="file"
              accept=".pdf,.doc,.docx"
              onChange={handleFileChange}
              className="w-full border rounded p-2 text-sm text-gray-700"
            />

            {fileName&&(
              <p className="mt-2 text-sm text-gray-600">
                Selected File: <span className="font-medium">{fileName}</span>
              </p>
            )}

            <button
              type="submit"
              className="mt-4 px-4 py-2 rounded text-white font-medium"
              style={{backgroundColor:"#1a56a0"}}
            >
              Upload Resume
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}