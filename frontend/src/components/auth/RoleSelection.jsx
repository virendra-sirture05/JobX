import {
  User,
  Shield,
  Briefcase,
} from "lucide-react";

const roles = [
  {
    title: "Job Seeker",
    icon: User,
    iconBg: "bg-blue-100",
    iconColor: "text-blue-700",
    description:
      "Find jobs, request referrals and track applications",
  },
  {
    title: "Referrer",
    icon: Briefcase,
    iconBg: "bg-green-100",
    iconColor: "text-green-700",
    description:
      "Refer candidates and earn credibility",
  },
  {
    title: "Admin",
    icon: Shield,
    iconBg: "bg-purple-100",
    iconColor: "text-purple-700",
    description:
      "Platform monitoring and management",
  },
];

export default function RoleSelection({
  onSelectRole,
}) {
  return (
    <>
      <div className="text-center">
        <h2 className="text-3xl font-bold">
          Welcome to Job Referral
        </h2>

        <p className="text-gray-500 mt-2">
          Choose how you want to continue
        </p>
      </div>

      <div className="grid gap-4 mt-8">
        {roles.map((role) => (
          <div
            key={role.title}
            onClick={() =>
              onSelectRole(role.title)
            }
            className="
              cursor-pointer
              border
              rounded-2xl
              p-5
              hover:shadow-lg
              transition-all
            "
          >
            <div className="flex items-center gap-4">

              <div
                className={`
                  p-3
                  rounded-xl
                  ${role.iconBg}
                `}
              >
                <role.icon
                  className={`
                    w-6 h-6
                    ${role.iconColor}
                  `}
                />
              </div>

              <div>
                <h3 className="font-semibold text-lg">
                  {role.title}
                </h3>

                <p className="text-sm text-gray-500">
                  {role.description}
                </p>
              </div>

            </div>
          </div>
        ))}
      </div>
    </>
  );
}