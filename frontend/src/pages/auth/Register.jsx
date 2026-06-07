import { Link, useNavigate } from "react-router-dom";

function Register() {
  const navigate = useNavigate();

  const handleRegister = (e) => {
    e.preventDefault();

    navigate("/");
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-slate-100">

      <div className="w-full max-w-lg bg-white p-6 rounded-lg shadow">

        <h1 className="text-2xl font-bold text-center mb-6">
          Create Account
        </h1>

        <form
          onSubmit={handleRegister}
          className="space-y-4"
        >

          <input
            type="text"
            placeholder="Full Name"
            className="w-full border p-2 rounded"
          />

          <input
            type="email"
            placeholder="Email"
            className="w-full border p-2 rounded"
          />

          <input
            type="password"
            placeholder="Password"
            className="w-full border p-2 rounded"
          />

          <input
            type="tel"
            placeholder="Mobile Number"
            className="w-full border p-2 rounded"
          />

          <select
            className="w-full border p-2 rounded"
          >
            <option>Select Role</option>
            <option>Job Seeker</option>
            <option>Recruiter</option>
          </select>

          <button
            type="submit"
            className="w-full bg-black text-white p-2 rounded"
          >
            Register
          </button>

        </form>

        <p className="text-center mt-4">
          Already have an account?{" "}
          <Link
            to="/login"
            className="text-blue-600"
          >
            Login
          </Link>
        </p>

      </div>

    </div>
  );
}

export default Register;