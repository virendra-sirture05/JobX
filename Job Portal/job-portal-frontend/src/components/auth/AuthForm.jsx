import {
  ArrowLeft,
  Mail,
  Lock,
} from "lucide-react";

import {
  useForm,
} from "react-hook-form";

import {
  zodResolver,
} from "@hookform/resolvers/zod";
import authService from "@/services/authService";
import {
  signupSchema,
//  loginSchema,
} from "@/schema/signupSchema";
import InputField from "./InputField";
import SocialLoginButtons from "./SocialLoginButtons";
import AuthFooter from "./AuthFooter";
import { useEffect, useRef } from "react";
import { Button } from "@/components/ui/button";
import { loginSchema } from "@/schema/loginSchema";
import { useNavigate } from "react-router-dom";
export default function AuthForm({
  role,
  mode,
  email,
  onBack,
  onForgotPassword,
  onOtpSent,
}) {
  const navigate = useNavigate();
  const schema =
    mode === "signup"
      ? signupSchema
      : loginSchema;

  const {
    register,
    handleSubmit,
    setValue,
    setFocus,
    formState: {
      errors,
      isSubmitting,
    },
  } = useForm({
    resolver:
      zodResolver(schema),
    mode:"onChange"
  });
const roleMap = {
  "Admin": "ROLE_ADMIN",
  "Job Seeker": "ROLE_JOBSEEKER",
  "Referrer": "ROLE_REFERRER",
};
useEffect(() => {

    if (email) {
        setValue("email", email);
    }

    if (mode === "login") {

        setFocus("password");

    }

}, [email, mode, setValue, setFocus]);

  const submitForm =
    async (data) => {

      console.log(
        "Validated Form Data",
        data
      );

      try {

        if (
          mode === "signup"
        ) {

          const payload = {
              email: data.email,
              password: data.password,
              userRole: roleMap[role], // or roleMap[data.role] depending on your variable
            };

          console.log(
            "Signup Payload",
            payload
          );
       onOtpSent(data.email);

         authService.sendOtp(payload);

        } else {

          const payload = {
            email:
              data.email,
            password:
              data.password,
            role,
          };

          console.log(
            "Login Payload",
            payload
          );

          const response = await authService.login(payload);
          
          
        if (response.message === "Login successful") {
            console.log("navigating to dashboard");
            
            navigate("/seeker/dashboard");

        } else {
          console.log("login failed");
          
            console.log(response.message);

}
             

        }

      } catch (error) {

        console.error(
          "Auth Error",
          error
        );

      }
    };

  return (
    <>
      <button
        onClick={onBack}
        className="
          flex
          items-center
          gap-2
          text-blue-600
          
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

        <p className="text-gray-500 mt-1">
          Welcome back to Job Referral
        </p>

      </div>

      {role ===
        "ROLE_JOBSEEKER" && (
        <SocialLoginButtons />
      )}

      <form
        onSubmit={handleSubmit(
          submitForm
        )}
        className="space-y-4"
      >

        <InputField
          icon={Mail}
          type="email"
          placeholder="Email Address"
          register={register(
            "email"
          )}
          error={
            errors.email
          }
        />

        <InputField
          icon={Lock}
          type="password"
          placeholder="Password"
          register={register(
            "password"
          )}
          error={
            errors.password
          }
        />

        {mode ===
          "signup" && (
          <InputField
            icon={Lock}
            type="password"
            placeholder="Confirm Password"
            register={register(
              "confirmPassword"
            )}
            error={
              errors.confirmPassword
            }
          />
        )}

        {mode ===
          "login" && (
          <div className="flex justify-end">

            <button
              type="button"
              onClick={
                onForgotPassword
              }
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
          type="submit"
          disabled={
            isSubmitting
          }
          
          className="
            w-full
            h-11
            bg-[#1a56a0]
            hover:bg-[#154f96]
          "
        >
          {isSubmitting
            ? "Please wait..."
            : mode ===
              "login"
            ? "Login"
            : "Create Account"}
        </Button>

        {role !==
          "ROLE_REFERRER" && (
          <AuthFooter
            mode={mode}
          />
        )}

      </form>
    </>
  );
}

