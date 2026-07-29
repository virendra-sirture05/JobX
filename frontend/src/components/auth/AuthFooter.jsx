export default function AuthFooter({
  mode,
}) {
  return (
    <div className="text-center text-sm text-gray-500">

      {mode === "login" ? (
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
      )}

    </div>
  );
}