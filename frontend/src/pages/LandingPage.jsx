import { Link, useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { useState } from "react";
import AuthModal from "@/components/ui/AuthModal";
import { Card, CardContent } from "@/components/ui/card";

export default function LandingPage() {
  const navigate = useNavigate();
  const features = [
    {
      icon: "🔍",
      title: "Smart Job Search",
      desc: "Filter by location, role, experience, and job type.",
    },
    {
      icon: "🤝",
      title: "Direct Referrals",
      desc: "Connect with employees and get referrals easily.",
    },
    {
      icon: "🤖",
      title: "AI Resume Match",
      desc: "Upload resume and get instant match score.",
    },
    {
      icon: "⭐",
      title: "Employer Credibility",
      desc: "Trust score for every referrer.",
    },
    {
      icon: "📊",
      title: "Track Applications",
      desc: "Live application tracking.",
    },
    {
      icon: "💬",
      title: "Direct Messaging",
      desc: "Chat with recruiters directly.",
    },
  ];

  const roles = [
    {
      title: "Job Seeker",
      icon: "👤",
      color: "#1a56a0",
      bg: "#dce8f5",
      border: "#b3d1f0",
      desc: "Find jobs and get referrals",
      path: "/register",
      points: [
        "Search jobs",
        "Request referrals",
        "AI matching",
        "Track status",
      ],
    },
    {
      title: "Recruiter / Referrer",
      icon: "🧑‍💼",
      color: "#1a7a56",
      bg: "#d4edda",
      border: "#b2dfdb",
      desc: "Post jobs and refer candidates",
      path: "/register",
      points: [
        "Post jobs",
        "Review referrals",
        "Find candidates",
        "Manage applicants",
      ],
    },
    {
      title: "Admin",
      icon: "🔧",
      color: "#7b2cbf",
      bg: "#ede7f6",
      border: "#ce93d8",
      desc: "Manage platform",
      path: "/",
      points: [
        "Users control",
        "Jobs monitoring",
        "Activity tracking",
        "Categories",
      ],
    },
  ];
  const [openLogin, setOpenLogin] = useState(false);
  const [openSignup, setOpenSignup] = useState(false);
  return (
    <div className="min-h-screen bg-white text-gray-900">
      {/* NAVBAR */}
      <nav className="sticky top-0 z-50 flex justify-between items-center px-6 py-4 border-b shadow-sm bg-white">
        <div className="flex items-center gap-2 font-bold">
          <div className="w-8 h-8 bg-[#1a56a0] text-white flex items-center justify-center rounded">
            JR
          </div>
          Job Referral
        </div>

        <div className="hidden md:flex gap-6 text-gray-600">
          <span className="hover:text-[#1a56a0] cursor-pointer">Features</span>
          <span className="hover:text-[#1a56a0] cursor-pointer">
            How it Works
          </span>
          <span className="hover:text-[#1a56a0] cursor-pointer">
            Recruiters
          </span>
        </div>

        <div className="flex gap-2">
          <Button
            variant="outline"
            onClick={()=>{setOpenLogin(true)}}
            className="border-[#1a56a0] text-[#1a56a0] hover:bg-blue-50"
          >
            Login
          </Button>

          <Button
            onClick={() => setOpenSignup(true)}
            className="bg-[#1a56a0] hover:bg-[#154f96]"
          >
            Sign Up
          </Button>
        </div>
      </nav>

      {/* HERO */}
      <section className="text-center py-20 bg-gradient-to-br from-[#f0f5ff] via-white to-[#f0faf4]">
        <div
          className="inline-block px-4 py-1 text-sm rounded-full mb-4 border"
          style={{
            background: "#dce8f5",
            color: "#1a56a0",
            borderColor: "#b3d1f0",
          }}
        >
          🚀 AI-Powered Job Referral Platform
        </div>

        <h1 className="text-4xl md:text-5xl font-extrabold">
          Get Referred. <br />
          <span className="text-[#1a56a0]">Land Your Dream Job</span>
        </h1>

        <p className="mt-4 text-gray-600 max-w-xl mx-auto">
          Connect with employees, get referrals, and track applications in one
          place.
        </p>

        <div className="mt-6 flex justify-center gap-3">
          <Button
            onClick={() => navigate("/register")}
            className="bg-[#1a56a0] hover:bg-[#154f96]"
          >
            Get Started
          </Button>

          <Button variant="outline" onClick={() => navigate("/login")}>
            Login
          </Button>
        </div>
      </section>

      {/* FEATURES */}
      <section className="py-16 px-6 bg-[#f8faff] border-y">
        <h2 className="text-3xl font-bold text-center mb-10">
          Everything you need
        </h2>

        <div className="grid md:grid-cols-3 gap-6 max-w-5xl mx-auto">
          {features.map((f) => (
            <Card key={f.title} className="shadow-sm">
              <CardContent className="p-5">
                <div className="text-2xl">{f.icon}</div>
                <h3 className="font-semibold mt-2">{f.title}</h3>
                <p className="text-gray-600 mt-1">{f.desc}</p>
              </CardContent>
            </Card>
          ))}
        </div>
      </section>

      {/* ROLES */}
      <section className="py-20 px-6">
        <h2 className="text-3xl font-bold text-center mb-10">
          Who is this for?
        </h2>

        <div className="grid md:grid-cols-3 gap-6 max-w-5xl mx-auto">
          {roles.map((role) => (
            <Card key={role.title} className="overflow-hidden shadow-md">
              {/* header */}
              <div
                className="p-4 text-white"
                style={{ background: role.color }}
              >
                <div className="text-2xl">{role.icon}</div>
                <h3 className="font-bold">{role.title}</h3>
                <p className="opacity-90">{role.desc}</p>
              </div>

              {/* body */}
              <CardContent className="p-5">
                <ul className="space-y-2">
                  {role.points.map((p) => (
                    <li key={p}>✔ {p}</li>
                  ))}
                </ul>

                <Button
                  className="w-full mt-4"
                  style={{ background: role.color }}
                  onClick={() => navigate(role.path)}
                >
                  Get Started →
                </Button>
              </CardContent>
            </Card>
          ))}
        </div>
      </section>

      {/* CTA */}
      <section
        className="text-white text-center py-16"
        style={{
          background: "linear-gradient(135deg,#1a56a0,#1a7a56)",
        }}
      >
        <h2 className="text-2xl font-bold">Ready to get referred?</h2>
        <p className="mt-2 text-white/80">
          Join thousands already using the platform
        </p>

        <div className="mt-6 flex justify-center gap-3">
          <Button
            className="bg-white text-[#1a56a0] hover:bg-gray-100"
            onClick={() => navigate("/register")}
          >
            Sign Up
          </Button>

          <Button
            variant="outline"
            className="text-white border-white"
            onClick={() => navigate("/login")}
          >
            Login
          </Button>
        </div>
      </section>

      {/* FOOTER */}
      <footer className="bg-[#1a1a2e] text-gray-400 py-6 px-6 flex justify-between">
        <div className="text-white font-semibold">Job Referral</div>
        <div>Built with React</div>
        <div className="flex gap-4">
          <span className="cursor-pointer">Login</span>
          <span className="cursor-pointer">Sign Up</span>
        </div>
      </footer>
      <AuthModal open={openLogin} onOpenChange={setOpenLogin} mode="login"  />
      <AuthModal open={openSignup} onOpenChange={setOpenSignup} mode="signup"/>
    </div>
  );
}
