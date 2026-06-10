import { Bell, UserCircle2 } from "lucide-react";

export default function RecruiterNavbar() {
  return (
    <header className="h-16 bg-[#1a56a0] text-white flex items-center justify-between px-6 shadow-md">
      <div>
        <h1 className="font-bold text-xl">
          JobX
        </h1>
      </div>

      <div className="flex items-center gap-6">
        <Bell size={20} />

        <div className="flex items-center gap-2">
          <UserCircle2 size={30} />
          <span className="text-sm">
            virendra
          </span>
        </div>
      </div>
    </header>
  );
}