import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

import { useSelector, useDispatch } from "react-redux";
import { loginSuccess } from "../../redux/slices/AuthSlice";

function Login() {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  // const users = useSelector(
  //   (state) => state.users?.users || []
  // );
  const users = useSelector((state)=>state.user.users);
  console.log(users);

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = (e) => {
    e.preventDefault();

    const user = users.find(
      (u) =>
        u.email === email &&
        u.password === password
    );
    console.log(user);

    if (!user) {
      alert("Invalid Email or Password");
      return;
    }

    dispatch(loginSuccess(user));

    if (user.role === "admin") {
      navigate("/admin/dashboard");
    } else if (user.role === "recruiter") {
      navigate("/recruiter/dashboard");
    } else {
      navigate("/seeker/dashboard");
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-slate-100">
      <div className="w-full max-w-md bg-white p-6 rounded-lg shadow">

        <h1 className="text-2xl font-bold text-center mb-6">
          Job Referral Platform
        </h1>

        <form
          onSubmit={handleLogin}
          className="space-y-4"
        >
          <input
            type="email"
            placeholder="Enter Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            className="w-full border p-2 rounded"
          />

          <input
            type="password"
            placeholder="Enter Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="w-full border p-2 rounded"
          />

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