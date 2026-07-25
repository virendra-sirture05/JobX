import { useState } from "react";
import { useNavigate } from "react-router-dom";

import {
  Dialog,
  DialogContent,
} from "@/components/ui/dialog";

import RoleSelection from "./RoleSelection";
import AuthForm from "./AuthForm";
import ForgotPasswordForm from "./ForgotPassword";
import OtpVerificationForm from "./OtpVerificationForm";
import SignupSuccess from "./SignupSuccess";
import authService from '@/services/authService'

export default function AuthModal({
  open,
  onOpenChange,
  mode = "login",
  setAuthModal
}) {
  const [step, setStep] =
     useState("role");
   // useState("signup-success")

  const [role, setRole] =
    useState("");
const [email, setEmail] =
  useState("");
  const navigate = useNavigate();

  const selectRole = (selectedRole) => {
    setRole(selectedRole);
    setStep("form");
  };

  const resetModal = () => {
    setStep("role");
    setRole("");
    onOpenChange(false);
  };

  const handleLogin = () => {
    console.log("Login", role);

    switch (role) {
      case "ROLE_REFERRER":
        navigate(
          "/recruiter/dashboard"
        );
        break;

      case "ROLE_ADMIN":
        navigate(
          "/admin/dashboard"
        );
        break;

      default:
        navigate(
          "/seeker/dashboard"
        );
    }
  };

  return (
    <Dialog
      open={open}
      onOpenChange={resetModal}
    >
      <DialogContent
        className="
          z-1000
          sm:max-w-lg
          rounded-3xl
          p-8
        "
      >

        {step === "role" && (
          <RoleSelection
            onSelectRole={selectRole}
          />
        )}

        {step === "form" && (
          <AuthForm
            role={role}
            mode={mode}
            email={email}
            onBack={() =>
              setStep("role")
            }
            onForgotPassword={() =>
              setStep("forgot-pass")
            }
             onOtpSent={(email) => {
        setEmail(email);
        setStep("otp");
         }}
            onSubmit={handleLogin}
          />
        )}


    {step === "otp" && (
  <OtpVerificationForm
    email={email}
    onBack={() =>
      setStep("form")
    }
    onVerify={ async (otp) => {

      console.log(
        "Verify OTP",
        otp
      );
      const payload = {
        email:email,
        otp:otp
      }
     const respose = await  authService.verifyOtp(payload);
     console.log(respose);
      setStep(
        "signup-success"
      );
    }}
  />
)}
{step === "signup-success" && (
  <SignupSuccess
    onContinue={() => {
      setStep("form"); 
      
      setEmail(email)
   //   onOpenChange(false);
      setAuthModal(prev => ({
            ...prev,
            open: true,
            mode: "login"
        }));
    }}
  />
)}
        {step === "forgot-pass" && (
          <ForgotPasswordForm
            onBack={() =>
              setStep("form")
            }
          />
        )}

      </DialogContent>
    </Dialog>
  );
}

