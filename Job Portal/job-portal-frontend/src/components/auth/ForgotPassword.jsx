import { ArrowLeft, Mail } from "lucide-react";
import InputField from "./InputField";
import { Button } from "@/components/ui/button";

export default function ForgotPasswordForm({
  onBack,
}) {
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

      <div className="text-center mt-4">
        <h2 className="text-3xl font-bold">
          Forgot Password
        </h2>

        <p className="text-gray-500 mt-2">
          Enter your email address
        </p>
      </div>

      <div className="mt-6">
        <InputField
          icon={Mail}
          type="email"
          placeholder="Email Address"
        />
      </div>

      <Button className="w-full mt-4">
        Send Reset Link
      </Button>
    </>
  );
}