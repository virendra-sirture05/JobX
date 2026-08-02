import PremiumBanner from "@/components/ui/seeker/PremiumBanner";
import JobMatchesTable from "@/components/ui/seeker/JobMatchesTable";
import ReferrerReviewsTable from "@/components/ui/seeker/ReferrerReviewTable";

export default function AIMatchDashboard() {
  return (
    <div className="p-8 space-y-6">

      <div>
        <h1 className="text-3xl font-bold">
          AI Match Analysis
        </h1>

        <p className="text-gray-500">
          Personalized job matching powered by AI
        </p>
      </div>

      <PremiumBanner />

      <div className="grid lg:grid-cols-2 gap-6">
        <JobMatchesTable />

        <ReferrerReviewsTable />
      </div>

    </div>
  );
}