// components/AuthModal.jsx
import { useState } from "react";
import {
  User,
  Shield,
  Briefcase,
  ArrowLeft,
  Mail,
  Lock,
} from "lucide-react";

import {
  Dialog,
  DialogContent,
} from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";


export default function AuthModal({
  open,
  onOpenChange,
  mode = "login",
}) {
  const [step, setStep] = useState("role");
  const [role, setRole] = useState("");
  const [forgotWindow,setForgotWindow] = useState(false);

  const selectRole = (selectedRole) => {
    setRole(selectedRole);
    setStep("form");
  };

  const resetModal = () => {
    setStep("role");
    setRole("");
    onOpenChange(false);
  };
  
  return (
    <Dialog
      open={open}
      onOpenChange={resetModal}
    >
      <DialogContent className="sm:max-w-lg rounded-3xl p-8">

        {/* STEP 1 */}

      {step === "role" && (
  <>
    <div className="text-center">
      <h2 className="text-3xl font-bold">
        Welcome to Job Referral
      </h2>

      <p className="text-gray-500 mt-2">
        Choose how you want to continue
      </p>
    </div>

    <div className="grid gap-4 mt-8">

      <div
        onClick={() => selectRole("Job Seeker")}
        className="
          cursor-pointer
          border rounded-2xl p-5
          hover:border-blue-500
          hover:shadow-lg
          transition-all
        "
      >
        <div className="flex items-center gap-4">
          <div className="p-3 rounded-xl bg-blue-100">
            <User className="w-6 h-6 text-blue-700" />
          </div>

          <div>
            <h3 className="font-semibold text-lg">
              Job Seeker
            </h3>

            <p className="text-sm text-gray-500">
              Find jobs, request referrals and track applications
            </p>
          </div>
        </div>
      </div>

      <div
        onClick={() => selectRole("Referrer")}
        className="
          cursor-pointer
          border rounded-2xl p-5
          hover:border-green-500
          hover:shadow-lg
          transition-all
        "
      >
        <div className="flex items-center gap-4">
          <div className="p-3 rounded-xl bg-green-100">
            <Briefcase className="w-6 h-6 text-green-700" />
          </div>

          <div>
            <h3 className="font-semibold text-lg">
              Referrer
            </h3>

            <p className="text-sm text-gray-500">
              Refer candidates and earn credibility
            </p>
          </div>
        </div>
      </div>

      <div
        onClick={() => selectRole("Admin")}
        className="
          cursor-pointer
          border rounded-2xl p-5
          hover:border-purple-500
          hover:shadow-lg
          transition-all
        "
      >
        <div className="flex items-center gap-4">
          <div className="p-3 rounded-xl bg-purple-100">
            <Shield className="w-6 h-6 text-purple-700" />
          </div>

          <div>
            <h3 className="font-semibold text-lg">
              Admin
            </h3>

            <p className="text-sm text-gray-500">
              Platform monitoring and management
            </p>
          </div>
        </div>
      </div>

    </div>
  </>
)}

        {/* STEP 2 */}

       {step === "form" && 
       (
  <>
    <button
      onClick={() => setStep("role")}
      className="
        flex items-center gap-2
        text-sm text-blue-600
        mb-4
      "
    >
      <ArrowLeft size={16} />
      Back
    </button>

    <div className="text-center">
      <h2 className="text-3xl font-bold">
        {mode === "login"
          ? `Login as ${role}`
          : `Create ${role} Account`}
      </h2>

      <p className="text-gray-500 mt-2">
        Welcome back to Job Referral
      </p>
    </div>

    {role === "Job Seeker" && (
      <>
        <div className="grid gap-3 mt-6">

          <Button
            variant="outline"
            className="h-11"
          >
            <div className="mr-2 h-4 w-4" />
            Continue with Google
          </Button>

          <Button
            variant="outline"
            className="h-11"
          >
            in Continue with LinkedIn
          </Button>

        </div>

        <div className="flex items-center my-5">
          <div className="flex-1 border-t" />
          <span className="px-3 text-xs text-gray-500">
            OR
          </span>
          <div className="flex-1 border-t" />
        </div>
      </>
    )}

    <form className="space-y-4">

      <div className="relative">
        <Mail
          size={18}
          className="
             absolute
             left-3
             top-3.5
             text-gray-400
          "
        />

        <input
          type="email"
          placeholder="Email Address"
          className="
            w-full
            border
            rounded-xl
            pl-10
            p-3
            focus:outline-none
            focus:ring-2
            focus:ring-blue-500
          "
        />
      </div>

      <div className="relative">
        <Lock
          size={18}
          className="
             absolute
             left-3
             top-3.5
             text-gray-400
          "
        />

        <input
          type="password"
          placeholder="Password"
          className="
            w-full
            border
            rounded-xl
            pl-10
            p-3
            focus:outline-none
            focus:ring-2
            focus:ring-blue-500
          "
        />
      </div>

      {mode === "signup" && (
        <input
          type="password"
          placeholder="Confirm Password"
          className="
            w-full
            border
            rounded-xl
            p-3
            focus:outline-none
            focus:ring-2
            focus:ring-blue-500
          "
        />
      )}

      {mode === "login" && (
        <div className="flex justify-end">
          <button onClick={()=>{}}
            type="button"
            className="
              text-sm
              text-blue-600
            "
          >
            Forgot Password?
          </button>
        </div>
      )}
        
      <Button
        className="
          w-full
          h-11
          bg-[#1a56a0]
          hover:bg-[#154f96]
        "
      >
        {mode === "login"
          ? "Login"
          : "Create Account"}
      </Button>

      <div className="text-center text-sm text-gray-500">

        {role !== "Referrer" && (mode === "login" ? (
          <>
            New user?{" "}
            <button
              type="button"
              className="
                text-blue-600
                font-medium
              "
            >
              Create account
            </button>
          </>
        ) : (
          <>
            Already have an account?{" "}
            <button
              type="button"
              className="
                text-blue-600
                font-medium
              "
            >
              Login
            </button>
          </>
        ))}

      </div>

    </form>
  </>
)}
  {/* STEP 3 */}
  {step === "forgot-pass" && (
  <>
    <button
      onClick={() => setStep("forgot-pass")}
      className="
        flex items-center gap-2
        text-sm text-blue-600
        mb-4
      "
    >
      <ArrowLeft size={16} />
      Back
    </button>

    <div className="text-center">
      <h2 className="text-3xl font-bold">
        {mode === "forgot-pass"
          ? `Login as ${role}`
          : `Create ${role} Account`}
      </h2>

      <p className="text-gray-500 mt-2">
        Welcome back to Job Referral
      </p>
    </div>

    {role === "Job Seeker" && (
      <>
        <div className="grid gap-3 mt-6">

          <Button
            variant="outline"
            className="h-11"
          >
            <div className="mr-2 h-4 w-4" />
            Continue with Google
          </Button>

          <Button
            variant="outline"
            className="h-11"
          >
            in Continue with LinkedIn
          </Button>

        </div>

        <div className="flex items-center my-5">
          <div className="flex-1 border-t" />
          <span className="px-3 text-xs text-gray-500">
            OR
          </span>
          <div className="flex-1 border-t" />
        </div>
      </>
    )}

    <form className="space-y-4">

      <div className="relative">
        <Mail
          size={18}
          className="
             absolute
             left-3
             top-3.5
             text-gray-400
          "
        />

        <input
          type="email"
          placeholder="Email Address"
          className="
            w-full
            border
            rounded-xl
            pl-10
            p-3
            focus:outline-none
            focus:ring-2
            focus:ring-blue-500
          "
        />
      </div>

      <div className="relative">
        <Lock
          size={18}
          className="
             absolute
             left-3
             top-3.5
             text-gray-400
          "
        />

        <input
          type="password"
          placeholder="Password"
          className="
            w-full
            border
            rounded-xl
            pl-10
            p-3
            focus:outline-none
            focus:ring-2
            focus:ring-blue-500
          "
        />
      </div>

      {mode === "signup" && (
        <input
          type="password"
          placeholder="Confirm Password"
          className="
            w-full
            border
            rounded-xl
            p-3
            focus:outline-none
            focus:ring-2
            focus:ring-blue-500
          "
        />
      )}

      {mode === "login" && (
        <div className="flex justify-end">
          <button onClick={()=>{}}
            type="button"
            className="
              text-sm
              text-blue-600
            "
          >
            Forgot Password?
          </button>
        </div>
      )}
        
      <Button
        className="
          w-full
          h-11
          bg-[#1a56a0]
          hover:bg-[#154f96]
        "
      >
        {mode === "login"
          ? "Login"
          : "Create Account"}
      </Button>

      <div className="text-center text-sm text-gray-500">

        {role !== "Referrer" && (mode === "login" ? (
          <>
            New user?{" "}
            <button
              type="button"
              className="
                text-blue-600
                font-medium
              "
            >
              Create account
            </button>
          </>
        ) : (
          <>
            Already have an account?{" "}
            <button
              type="button"
              className="
                text-blue-600
                font-medium
              "
            >
              Login
            </button>
          </>
        ))}

      </div>

    </form>
  </>
)}
      </DialogContent>
    </Dialog>
  );
}