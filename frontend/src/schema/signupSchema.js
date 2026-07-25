import { z } from "zod";

export const signupSchema = z
  .object({
    email: z
      .string()
      .min(1, "Email required")
      .email("Invalid email"),

    password: z
      .string()
      .min(
        8,
        "Password must be at least 8 characters"
      ),

    confirmPassword: z.string(),
  })

  .refine(
    (data) =>
      data.password ===
      data.confirmPassword,
    {
      path: ["confirmPassword"],
      message:
        "Passwords do not match",
    }
  );