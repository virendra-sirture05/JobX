import { useState } from "react"
import { Button } from "@/components/ui/button"
import {
  Sparkles, SlidersHorizontal, Search,
  Briefcase, TrendingUp, X, ChevronLeft, ChevronRight,
  Wand2, Loader2,
} from "lucide-react"

const Jobs = () => {
  const [aiQuery, setAiQuery] = useState("")

  const handleEnhance = () => {
    console.log("Enhancing with AI query:", aiQuery)
  }
  
    
  return (
    <>
    {/* Hero Section*/}
    <section>
     <div className="min-h-screen bg-slate-50">

      {/* ── Hero ──────────────────────────────────────────────────────────── */}
      <div className="bg-linear-to-br from-primary via-blue-950 to-indigo-950 py-12 px-4">
        <div className="max-w-4xl mx-auto text-center">
          <div className="inline-flex items-center gap-2 bg-white/15 backdrop-blur-sm text-white text-sm px-3 py-1.5 rounded-full mb-4">
            <Sparkles className="h-4 w-4" />
            AI-Powered Job Search
          </div>
          <h1 className="text-3xl sm:text-4xl font-bold text-white mb-2">
            Find Your Next Opportunity
          </h1>
          <p className="text-blue-100 mb-8 text-sm sm:text-base">
            Discover thousands of jobs matched to your skills and experience
          </p>

          {/* AI Search card */}
          <div className="bg-white rounded-2xl shadow-xl p-4 space-y-3">
            <div className="relative">
              <Wand2 className="absolute left-3 top-3 h-4 w-4 text-slate-400 pointer-events-none" />
              <textarea
                 value={aiQuery}
                onChange={(e) => setAiQuery(e.target.value)}
                placeholder={"Describe your ideal job in plain English…\ne.g. \"remote senior React developer, full-time, above $80k\""}
                className="w-full resize-none rounded-lg border border-slate-200 bg-slate-50 pl-9 pr-4 py-2.5 text-sm text-slate-800 placeholder:text-slate-400 focus:outline-none focus:ring-2 focus:ring-brand/30 focus:border-brand transition"
              />
            </div>
            <div className="flex items-center justify-between">
              <p className="text-xs text-slate-400">Tip: Ctrl + Enter to search</p>
              <Button onClick={handleEnhance} className="bg-blue-600 hover:bg-blue-700 text-white text-xs py-1.5 h-8">
                <Wand2 className="h-3.5 w-3.5 mr-1.5" />
                Search with AI
              </Button>
            </div>
          </div>
        </div>
      </div>
      </div>
      </section>
    </>
  )
}

export default Jobs