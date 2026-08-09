import { Card, CardContent } from "./ui/card"
import { FileText, Target, Zap, BarChart3, Users } from "lucide-react"

export default function Features() {
  const features = [
    {
      icon: FileText,
      title: "Professional Profiles",
      description: "Present your experience, skills, and achievements in a profile built to earn trust."
    },
    {
      icon: Target,
      title: "Relevant Opportunities",
      description: "Discover roles aligned with your strengths, interests, and professional goals."
    },
    {
      icon: Zap,
      title: "Easy Referrals",
      description: "Recommend qualified candidates and help strong talent reach the right employers."
    },
    {
      icon: BarChart3,
      title: "Application Tracking",
      description: "Keep applications, referrals, and progress organized in one clear dashboard."
    },
    {
      icon: Users,
      title: "Trusted Hiring Connections",
      description: "Help employers reduce hiring noise and connect with candidates through credible referrals."
    }
  ]

  return (
    // Features section
    <section id="features" className="py-20">
      <div className="container mx-auto px-4">
        {/* Section Header */}
        <div className="text-center mb-12">
          <h2 className="text-3xl md:text-4xl font-bold text-slate-900 mb-3">
            Why Job Referrer System
          </h2>
          <p className="text-lg text-slate-600 max-w-2xl mx-auto">
            Everything you need to make hiring and referrals more effective
          </p>
        </div>

        {/* Features Grid */}
        <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-3 max-w-6xl mx-auto">
          {features.map((feature, index) => (
            <Card key={index} className="border-slate-200 hover:shadow-md transition-shadow">
              <CardContent className="pt-6 pb-6 px-6">
                {/* Icon */}
                <div className="mb-4 w-12 h-12 rounded-lg bg-blue-100 flex items-center justify-center">
                  <feature.icon className="w-6 h-6 text-brand" />
                </div>

                {/* Title */}
                <h3 className="text-lg font-semibold text-slate-900 mb-2">
                  {feature.title}
                </h3>

                {/* Description */}
                <p className="text-sm text-slate-600">
                  {feature.description}
                </p>
              </CardContent>
            </Card>
          ))}
        </div>
      </div>
    </section>
  )
}
