import { Eye } from "lucide-react";
import { useNavigate } from "react-router-dom";

export default function ReferrerLogin() {
  return (
    <div className="min-h-screen bg-white flex items-center justify-center px-6">
      <div className="w-full max-w-md">
        {/* Logo */}

        <div className="flex justify-center mb-10">
          <div className="flex items-center gap-3">
            <div className="w-10 h-10 rounded-xl bg-gradient-to-br from-[#1F8A70] to-[#4DBA67]" />

            <span className="text-3xl font-semibold text-gray-900">
              Job Refferrer
            </span>
          </div>
        </div>

        {/* Card */}

        <div className="bg-white border border-gray-200 rounded-3xl p-8 shadow-sm">
          <h1 className="text-4xl font-semibold text-gray-900">
            Welcome back
          </h1>

          <p className="mt-3 text-gray-500">
            Sign in to continue
          </p>

          {/* Email */}

          <div className="mt-8">
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Email
            </label>

            <input
              type="email"
              placeholder="Enter your email"
              className="
                w-full
                h-14
                px-4
                rounded-xl
                border
                border-gray-300
                focus:border-[#1F8A70]
                focus:ring-2
                focus:ring-[#1F8A70]/20
                outline-none
              "
            />
          </div>

          {/* Password */}

          <div className="mt-5">
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Password
            </label>

            <div className="relative">
              <input
                type="password"
                placeholder="Enter password"
                className="
                  w-full
                  h-14
                  px-4
                  pr-12
                  rounded-xl
                  border
                  border-gray-300
                  focus:border-[#1F8A70]
                  focus:ring-2
                  focus:ring-[#1F8A70]/20
                  outline-none
                "
              />

              <Eye
                className="absolute right-4 top-4 text-gray-500 cursor-pointer"
                size={20}
              />
            </div>
          </div>

          {/* Forgot */}

          <button className="mt-4 text-[#1F8A70] font-medium">
            Forgot Password?
          </button>

          {/* Sign In */}

          <button
            className="
              mt-8
              w-full
              h-14
              rounded-xl
              text-white
              font-semibold
              bg-gradient-to-r
              from-[#1F8A70]
              to-[#4DBA67]
              hover:opacity-95
            "
          >
            Sign In
          </button>

          {/* Divider */}

          <div className="flex items-center my-8">
            <div className="flex-1 h-px bg-gray-200" />
            <span className="px-4 text-gray-400">
              OR
            </span>
            <div className="flex-1 h-px bg-gray-200" />
          </div>

          {/* Phone Login */}

          <button
            className="
              w-full
              h-14
              rounded-xl
              border
              border-gray-300
              font-medium
              hover:bg-gray-50
            "
          >
            Continue with Phone Number
          </button>

          {/* Signup */}

          <p className="mt-8 text-center text-gray-500">
            New here?
            <button className="ml-2 text-[#1F8A70] font-semibold" onClick={()=> useNavigate('/referrer-signup')}>
              Create Account
            </button>
          </p>
        </div>
      </div>
    </div>
  );
}