import { BrowserRouter, Routes, Route } from "react-router-dom";
import LandingPage from "./pages/LandingPage";
import Login from "./pages/auth/Login";
import Register from "./pages/auth/Register";
import ReferrerLogin from "./pages/auth/ReferrerLogin";

// Auth
// import Login from "./pages/auth/Login";
// import Register from "./pages/auth/Register";

// // Admin
// import AdminDashboard from "./pages/admin/AdminDashboard";
// import ManageUsers from "./pages/admin/ManageUsers";
// import ManageJobs from "./pages/admin/ManageJobs";
// import Applications from "./pages/admin/Applications";
// import LoginActivity from "./pages/admin/LoginActivity";
// import Categories from "./pages/admin/Categories";

// Recruiter
// import RecruiterDashboard from "./pages/recruiter/RecruiterDashboard";
 import PostJob from "./pages/recruiter/PostJob";
import RecruiterDashboard from "./pages/recruiter/RecruiterDashboard";
// import PostJob from "./pages/recruiter/PostJob";
// import Applicants from "./pages/recruiter/Applicants";
// import JobSeekers from "./pages/recruiter/JobSeekers";
// import Resources from "./pages/recruiter/Resources";
// Change "SearchApplications" to "SearchApplication" at the end of the string
import ViewApplications from "./pages/recruiter/ViewApplications";
import SearchApplications from "./pages/recruiter/SearchApplications";
import SearchJobSeeker from "./pages/recruiter/SearchJobSeeker";
import Resources from "./pages/recruiter/Resources";


// Job Seeker
import Dashboard from "./pages/seeker/Dashboard";
import Jobs from "./pages/seeker/Jobs";
import AppliedJobs from "./pages/seeker/AppliedJobs";
import Resume from "./pages/seeker/Resume";
import Profile from "./pages/seeker/Profile";
import AIMatchDashboard from "./pages/seeker/AIMatchDashboard";
import SavedJobs from "./pages/seeker/SavedJobs";
import MessagesPage from "./pages/seeker/MessagePage";
// import AIMatch from "./pages/seeker/AIMatch";
// import Messages from "./pages/seeker/Messages";

function App() {
  return (
        <BrowserRouter>
      <Routes>
        <Route path="/" element={<LandingPage />} />
        {/* Auth Routes */}

        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/referrer-login" element={<ReferrerLogin/> } />




        {/* ================= ADMIN ================= */}

        {/* <Route path="/admin/dashboard" element={<AdminDashboard />} />

        <Route path="/admin/users" element={<ManageUsers />} />

        <Route path="/admin/jobs" element={<ManageJobs />} />

        <Route
          path="/admin/applications"
          element={<Applications />}
        />

        <Route
          path="/admin/login-activity"
          element={<LoginActivity />}
        />

        <Route
          path="/admin/categories"
          element={<Categories />}
        /> */}

          <Route
          path="/recruiter/post-job"
          element={<PostJob />}
        />

        {/* ================= RECRUITER ================= */}

        <Route
          path="/recruiter/dashboard"
          element={<RecruiterDashboard />}
        />

{/* 
        <Route
          path="/recruiter/post-job"
          element={<PostJob />}
        />
        <Route
          path="/recruiter/applicants"
          element={<Applicants />}
        />

        <Route
          path="/recruiter/job-seekers"
          element={<JobSeekers />}
        />

        <Route
          path="/recruiter/resources"
          element={<Resources />}
        /> */}

        <Route
          path="/recruiter/applications"
          element={<ViewApplications />}
        />

        <Route
          path="/recruiter/applicants"
          element={<ViewApplications />}
        />

        <Route
          path="/recruiter/search-applications"
          element={<SearchApplications />}
        />

<Route
  path="/recruiter/search-jobseeker"
  element={<SearchJobSeeker />}
/>

<Route
  path="/recruiter/resources"
  element={<Resources />}
/>


       <Route
          path="/seeker/dashboard"
          element={<Dashboard />}
        />

        <Route
          path="/seeker/jobs"
          element={<Jobs />}
        />

        <Route
          path="/seeker/applied-jobs"
          element={<AppliedJobs />}
        />

        <Route
          path="/seeker/resume"
          element={<Resume />}
        />

        <Route
          path="/seeker/profile"
          element={<Profile />}
        />
        <Route
          path="/seeker/ai-match"
          element={<AIMatchDashboard />}
        />
        <Route
          path="/seeker/saved-jobs"
          element={<SavedJobs />}
        />

        <Route
          path="/seeker/messages"
          element={<MessagesPage />}
        />
        {/* ================= JOB SEEKER ================= */}

        {/*

       

       

        

         */}

      </Routes>
    </BrowserRouter>
  );

}
export default App;