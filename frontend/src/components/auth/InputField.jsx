import React, { useState } from "react";
import { Eye, EyeOff } from "lucide-react";

export default function InputField({
  icon: Icon,
  type,
  placeholder,
  register,
  error,
}) {

  const [showPassword,
    setShowPassword] =
      useState(false);

  const isPassword =
    type === "password";

  return (
    <div>

      <div className="relative">

        {Icon && (
          <Icon
            size={18}
            className="
              absolute
              left-3
              top-1/2
              -translate-y-1/2
              text-gray-400
            "
          />
        )}

        <input
          {...register}
          type={
            isPassword
              ? showPassword
                ? "text"
                : "password"
              : type
          }
          placeholder={placeholder}
          className="
            w-full
            border
            rounded-xl
            p-3
            pl-10
            pr-10
            focus:ring-2
            focus:ring-blue-500
          "
        />

        {isPassword && (
          <button
            type="button"
            onClick={() =>
              setShowPassword(
                !showPassword
              )
            }
            className="
              absolute
              right-3
              top-1/2
              -translate-y-1/2
            "
          >
            {showPassword ? (
              <EyeOff size={18} />
            ) : (
              <Eye size={18} />
            )}
          </button>
        )}

      </div>

      {error && (
        <p className="text-red-500 text-sm mt-1">
          {error.message}
        </p>
      )}

    </div>
  );
}