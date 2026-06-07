import { Link, useNavigate } from "react-router-dom";

function Login() {
  const navigate = useNavigate();

  const handleLogin = (e) => {
    e.preventDefault();

    // Temporary Navigation
    navigate("/admin/dashboard");

    // Later:
    // if(role === "admin")
    // navigate("/admin/dashboard")
    // else if(role === "recruiter")
    // navigate("/recruiter/dashboard")
    // else
    // navigate("/seeker/dashboard")
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-slate-100">
      <div className="w-full max-w-md bg-white p-6 rounded-lg shadow">

        <h1 className="text-2xl font-bold text-center mb-6">
          Job Referral Platform
        </h1>

        <form onSubmit={handleLogin} className="space-y-4">

          <div>
            <label className="block mb-1">
              Email
            </label>

            <input
              type="email"
              placeholder="Enter Email"
              className="w-full border p-2 rounded"
            />
          </div>

          <div>
            <label className="block mb-1">
              Password
            </label>

            <input
              type="password"
              placeholder="Enter Password"
              className="w-full border p-2 rounded"
            />
          </div>

          <button
            type="submit"
            className="w-full bg-black text-white p-2 rounded"
          >
            Login
          </button>

        </form>

        <p className="text-center mt-4">
          Don't have an account?{" "}
          <Link
            to="/register"
            className="text-blue-600"
          >
            Register
          </Link>
        </p>

      </div>
    </div>
  );
}

export default Login;