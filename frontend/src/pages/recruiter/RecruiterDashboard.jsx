import RecruiterNavbar from "@/components/ui/RecruiterNavbar";
import RecruiterSidebar from "@/components/ui/RecruiterSidebar";
import DashboardStats from "@/components/ui/RecruiterDashboardStats";
import ReferralTable from "@/components/ui/ReferralTable";

export default function RecruiterDashboard() {
  return (
    <div className="bg-slate-50 min-h-screen">
      <RecruiterNavbar />

      <div className="flex">
        <RecruiterSidebar />

        <main className="flex-1 p-8">
          <h1 className="text-3xl font-bold mb-6">
            Dashboard
          </h1>

          <DashboardStats />

          <ReferralTable />
        </main>
      </div>
    </div>
  );
}