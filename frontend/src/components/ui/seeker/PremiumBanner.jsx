import { Button } from "@/components/ui/button";
import { Crown } from "lucide-react";

export default function PremiumBanner() {
  return (
    <div
      className="
        bg-amber-50
        border
        border-amber-200
        rounded-2xl
        p-6
        flex
        justify-between
        items-center
      "
    >
      <div className="flex items-center gap-4">
        <div className="bg-amber-100 p-3 rounded-xl">
          <Crown className="text-amber-600" />
        </div>

        <div>
          <h2 className="font-bold text-lg text-amber-900">
            Premium Features
          </h2>

          <p className="text-amber-700">
            Unlock AI Resume Matching and Referrer
            Credibility Analysis
          </p>
        </div>
      </div>

      <Button className="bg-[#1a56a0] hover:bg-[#154a8a]">
        Subscribe Now
      </Button>
    </div>
  );
}