import { Button } from "@/components/ui/button";

export default function SocialLoginButtons() {
  return (
    <>
      <div className="grid gap-3 mt-3">

        <Button
          variant="outline"
          className="h-11"
        >
          Continue with Google
        </Button>

        <Button
          variant="outline"
          className="h-11"
        >
          Continue with LinkedIn
        </Button>

      </div>

      <div className="flex items-center my-1">
        <div className="flex-1 border-t" />

        <span className="px-3 text-xs text-gray-500">
          OR
        </span>

        <div className="flex-1 border-t" />
      </div>
    </>
  );
}