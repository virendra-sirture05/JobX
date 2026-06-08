import {
  LayoutDashboard,
  Briefcase,
  Users,
  Search,
  MessageSquare,
  User,
  LogOut,
} from "lucide-react";

const menus = [
  {
    label: "Dashboard",
    icon: LayoutDashboard,
  },
  {
    label: "Post Job",
    icon: Briefcase,
  },
  {
    label: "My Jobs",
    icon: Briefcase,
  },
  {
    label: "Applicants",
    icon: Users,
  },
  {
    label: "Search Seekers",
    icon: Search,
  },
  {
    label: "Messages",
    icon: MessageSquare,
  },
  {
    label: "Profile",
    icon: User,
  },
];

export default function RecruiterSidebar() {
  return (
    <aside className="w-64 bg-white border-r min-h-[calc(100vh-64px)]">
      <div className="p-5 border-b">
        <h2 className="font-semibold text-gray-700">
          Recruiter Panel
        </h2>
      </div>

      <nav className="p-3">
        {menus.map((item) => (
          <button
            key={item.label}
            className="
              w-full
              flex
              items-center
              gap-3
              px-4
              py-3
              rounded-xl
              text-gray-700
              hover:bg-blue-50
              hover:text-[#1a56a0]
            "
          >
            <item.icon size={18} />
            {item.label}
          </button>
        ))}

        <button
          className="
            mt-4
            w-full
            flex
            items-center
            gap-3
            px-4
            py-3
            rounded-xl
            text-red-600
            hover:bg-red-50
          "
        >
          <LogOut size={18} />
          Logout
        </button>
      </nav>
    </aside>
  );
}