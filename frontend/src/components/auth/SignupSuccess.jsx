import { Button } from "@/components/ui/button";

export default function SignupSuccess({
  onContinue,
}) {

  return (
    <div className="text-center">

      <div className="text-5xl mb-4">
        ✓
      </div>

      <h2 className="text-2xl font-bold">
        Account Created
      </h2>

      <p className="text-gray-500 mt-2">
        Your email has been verified successfully.
      </p>

      <Button
        className="w-full mt-6       
        bg-[#1a56a0] hover:"
        onClick={onContinue}
      >
        Go To Login
      </Button>

    </div>
  );
}