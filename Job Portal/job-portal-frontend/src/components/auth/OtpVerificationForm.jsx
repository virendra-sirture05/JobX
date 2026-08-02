import { useState, useRef } from "react";
import { Button } from "@/components/ui/button";

export default function OtpVerificationForm({
  email,
  onVerify,
  onBack,
}) {

  const [otp, setOtp] =
    useState(["", "", "", "", "", ""]);

  const inputRefs =
    useRef([]);

  const handleChange = (
    value,
    index
  ) => {

    if (!/^\d*$/.test(value))
      return;

    const digit =
      value.slice(-1);

    const newOtp =
      [...otp];

    newOtp[index] =
      digit;

    setOtp(newOtp);

    if (
      digit &&
      index < 5
    ) {
      inputRefs.current[
        index + 1
      ]?.focus();
    }

    const otpValue =
      newOtp.join("");

    if (
      otpValue.length === 6 &&
      !newOtp.includes("")
    ) {

      setTimeout(() => {
        onVerify(
          otpValue
        );
      }, 200);

    }

  };

  const handleKeyDown = (
    e,
    index
  ) => {

    if (
      e.key ===
        "Backspace" &&
      !otp[index] &&
      index > 0
    ) {

      inputRefs.current[
        index - 1
      ]?.focus();

    }

  };

  const handlePaste = (
    e
  ) => {

    e.preventDefault();

    const pasted =
      e.clipboardData
        .getData("text")
        .replace(/\D/g, "")
        .slice(0, 6);

    if (
      pasted.length === 6
    ) {

      const digits =
        pasted.split("");

      setOtp(digits);

      setTimeout(() => {
        onVerify(
          pasted
        );
      }, 200);

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
        ← Back
      </button>

      <div className="text-center mt-3">

        <h2 className="text-2xl font-bold">
          Verify Email
        </h2>

        <p className="text-gray-500 mt-1">
          We sent a verification
          code to
        </p>

        <p className="font-medium mt-1">
          {email}
        </p>

      </div>

      <div
        className="
          flex
          justify-center
          gap-3
          mt-6
        "
      >
        {otp.map(
          (
            digit,
            index
          ) => (
            <input
              key={index}
              ref={(el) =>
                (
                  inputRefs.current[
                    index
                  ] = el
                )
              }
              value={digit}
              maxLength={1}
              inputMode="numeric"
              onPaste={
                handlePaste
              }
              onChange={(
                e
              ) =>
                handleChange(
                  e.target
                    .value,
                  index
                )
              }
              onKeyDown={(
                e
              ) =>
                handleKeyDown(
                  e,
                  index
                )
              }
              className="
                h-12
                w-12
                border
                rounded-xl
                text-center
                text-lg
                font-semibold
                focus:outline-none
                focus:ring-2
                focus:ring-blue-500
              "
            />
          )
        )}
      </div>

      <p
        className="
          text-center
          text-sm
          text-gray-500
          mt-4
        "
      >
        Enter the 6-digit code
      </p>

      <Button
        className="
          w-full
          mt-6
          bg-[#1a56a0]
        "
        onClick={() =>
          onVerify(
            otp.join("")
          )
        }
        disabled={
          otp.join("")
            .length !== 6
        }
      >
        Verify OTP
      </Button>

      <button
        type="button"
        className="
          w-full
          text-center
          mt-4
          text-sm
          text-blue-600
        "
      >
        Resend OTP
      </button>

    </>
  );
}