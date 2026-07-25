import { useRef, useState } from "react";
import { motion, useInView } from "framer-motion";
import BgGradient from "./gradient/BgGradient";
import AuthModal from "@/components/auth/AuthModal";

// PDFGlow exact palette
const C = {
  rose500: "#f43f5e",
  rose600: "#e11d48",
  rose700: "#be123c",
  rose200: "#fecdd3",
  rose100: "#ffe4e6",
  rose50: "#fff1f2",
  slate900: "#0f172a",
  slate800: "#1e293b",
  slate700: "#334155",
  slate500: "#64748b",
  slate300: "#cbd5e1",
  slate100: "#f1f5f9",
  white: "#ffffff",
};

// Variants — same pattern as PDFGlow
const containerV = {
  hidden: { opacity: 0 },
  visible: {
    opacity: 1,
    transition: { staggerChildren: 0.13, delayChildren: 0.08 },
  },
};
const itemV = {
  hidden: { opacity: 0, y: 24 },
  visible: { opacity: 1, y: 0, transition: { duration: 0.5, ease: "easeOut" } },
};
const fadeUpV = {
  hidden: { opacity: 0, y: 30 },
  visible: {
    opacity: 1,
    y: 0,
    transition: { duration: 0.55, ease: "easeOut" },
  },
};
const scaleV = {
  hidden: { opacity: 0, scale: 0.94 },
  visible: {
    opacity: 1,
    scale: 1,
    transition: { duration: 0.42, ease: "easeOut" },
  },
};
const buttonHover = {
  scale: 1.05,
  transition: { type: "spring", stiffness: 300, damping: 10 },
};

function InView({ children, delay = 0, stagger = true }) {
  const ref = useRef(null);
  const inView = useInView(ref, { once: true, margin: "-70px" });
  return (
    <motion.div
      ref={ref}
      initial="hidden"
      animate={inView ? "visible" : "hidden"}
      variants={
        stagger
          ? {
              hidden: {},
              visible: {
                transition: { staggerChildren: 0.13, delayChildren: delay },
              },
            }
          : fadeUpV
      }
    >
      {children}
    </motion.div>
  );
}

const features = [
  {
    icon: "🔍",
    title: "Smart Job Search",
    desc: "Filter by location, role, experience, and job type with precision.",
  },
  {
    icon: "🤝",
    title: "Direct Referrals",
    desc: "Connect with insiders and get referred before the job goes public.",
  },
  {
    icon: "🤖",
    title: "AI Resume Match",
    desc: "Upload your resume and get an instant compatibility score.",
  },
  {
    icon: "⭐",
    title: "Employer Credibility",
    desc: "Every referrer has a verified trust score — know who you're talking to.",
  },
  {
    icon: "📊",
    title: "Track Applications",
    desc: "Live status updates across every application in one dashboard.",
  },
  {
    icon: "💬",
    title: "Direct Messaging",
    desc: "Chat directly with recruiters — no middlemen, no delays.",
  },
];

const steps = [
  {
    num: "01",
    icon: "📄",
    title: "Upload Resume",
    desc: "Drop your resume and let our AI analyze your strengths instantly.",
  },
  {
    num: "02",
    icon: "🔗",
    title: "Match & Connect",
    desc: "Get matched with jobs and connect with employee referrers.",
  },
  {
    num: "03",
    icon: "🎉",
    title: "Get Referred",
    desc: "Receive referral and track your application status live.",
  },
];

const stats = [
  { value: "12K+", label: "Active Jobs" },
  { value: "3.4K+", label: "Referrals Made" },
  { value: "91%", label: "Match Accuracy" },
  { value: "48hr", label: "Avg. Response" },
];


const slideLeftV = {
  hidden: { opacity: 0, x: -100 },
  visible: {
    opacity: 1,
    x: 0,
    transition: { duration: 0.5, ease: "easeOut" },
  },
};


export default function JobXLanding() {
const [authModal, setAuthModal] = useState({

    open: false,

    mode: "login"

});

  return (
    <div
      style={{
        fontFamily: "'Inter',sans-serif",
        background: C.white,
        color: C.slate900,
        minHeight: "100vh",
      }}
    >
      {/* <style>{`
        @import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700;800&display=swap');
        * { box-sizing: border-box; margin: 0; padding: 0; }
      `}</style> */}

      <BgGradient />

      {/* ── NAVBAR ── */}
      <motion.nav
        initial={{ opacity: 0, y: -18 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.45, ease: "easeOut" }}
        style={{
          position: "sticky",
          top: 0,
          zIndex: 100,
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          padding: "0 6%",
          height: 62,
          background: "rgba(255,255,255,0.45)",
          backdropFilter: "blur(16px)",
          borderBottom: "1px solid rgba(255,255,255,0.3)",
        }}
      >
        {/* Logo */}
        <div style={{ display: "flex", alignItems: "center", gap: 9 }}>
          <div
            style={{
              width: 34,
              height: 34,
              borderRadius: 8,
              background: `linear-gradient(135deg, ${C.slate900}, ${C.rose600})`,
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
              color: "white",
              fontWeight: 800,
              fontSize: 13,
              letterSpacing: 0.3,
            }}
          >
            JX
          </div>
          <span
            style={{
              fontWeight: 800,
              fontSize: 17,
              letterSpacing: -0.3,
              color: C.slate900,
            }}
          >
            JobX
          </span>
        </div>

        {/* Buttons */}
        <div style={{ display: "flex", gap: 8 }}>
          <motion.button
            whileHover={{ scale: 1.03 }}
            whileTap={{ scale: 0.97 }}
            onClick={() => setAuthModal({ open: true,mode: "login"})}
            style={{
              background: "transparent",
              color: C.slate700,
              border: `1.5px solid ${C.slate300}`,
              padding: "8px 18px",
              borderRadius: 8,
              fontSize: 14,
              fontWeight: 600,
              cursor: "pointer",
            }}
          >
            Login
          </motion.button>
          <motion.button
            whileHover={buttonHover}
            whileTap={{ scale: 0.96 }}
            onClick={() => setAuthModal({ open: true,mode: "signup"})}
            style={{
              background: `linear-gradient(135deg, ${C.slate900}, ${C.rose600})`,
              color: "white",
              border: "none",
              padding: "8px 20px",
              borderRadius: 8,
              fontSize: 14,
              fontWeight: 600,
              cursor: "pointer",
            }}
          >
            Sign Up
          </motion.button>
        </div>
      </motion.nav>

      {/* ── HERO ── */}
      <section
        style={{
          textAlign: "center",
          padding: "84px 6% 96px",
          background: C.white,
        }}
      >
        <motion.div variants={containerV} initial="hidden" animate="visible">
          {/* Badge */}
          <motion.div variants={itemV}>
            <motion.span
              animate={{ scale: [1, 1.04, 1] }}
              transition={{ duration: 2, repeat: Infinity, ease: "easeInOut" }}
              style={{
                display: "inline-flex",
                alignItems: "center",
                gap: 7,
                background: C.white,
                color: C.rose600,
                border: `1.5px solid ${C.rose600}`,
                borderRadius: 100,
                padding: "6px 16px",
                fontSize: 14,
                fontWeight: 600,
                marginBottom: 28,
                cursor: "pointer",
              }}
            >
              ✨ <span>AI-Powered Job Referral Platform</span>
            </motion.span>
          </motion.div>

          {/* H1 */}
          <motion.h1
            variants={itemV}
            style={{
              fontSize: "clamp(34px, 5.5vw, 58px)",
              fontWeight: 800,
              lineHeight: 1.13,
              letterSpacing: -1.5,
              marginBottom: 18,
              color: C.slate900,
            }}
          >
            Get Referred. Land{" "}
            <span style={{ position: "relative", display: "inline-block" }}>
              <motion.span
                whileHover={{
                  scale: 1.04,
                  transition: { type: "spring", stiffness: 300, damping: 10 },
                }}
                style={{
                  position: "relative",
                  zIndex: 1,
                  padding: "0 6px",
                  background: `linear-gradient(135deg, ${C.rose500}, ${C.rose700})`,
                  WebkitBackgroundClip: "text",
                  WebkitTextFillColor: "transparent",
                  display: "inline-block",
                }}
              >
                Your Dream Job
              </motion.span>
              <span
                style={{
                  position: "absolute",
                  inset: 0,
                  background: `${C.rose200}55`,
                  transform: "rotate(-1.5deg) skewY(-1deg)",
                  borderRadius: 8,
                  zIndex: 0,
                }}
              />
            </span>
          </motion.h1>

          {/* Subtitle */}
          <motion.p
            variants={itemV}
            style={{
              color: C.slate500,
              fontSize: 16,
              lineHeight: 1.75,
              maxWidth: 500,
              margin: "0 auto 36px",
            }}
          >
            Connect with employees at top companies, get referrals, and track
            every application — all in one place.
          </motion.p>

          {/* CTA Button */}
          <motion.div
            variants={itemV}
            style={{ display: "flex", justifyContent: "center" }}
          >
            <motion.button
              whileHover={buttonHover}
              whileTap={{ scale: 0.96 }}
              onClick={() => setAuthModal({ open: true,mode: "login"})}
              style={{
                display: "flex",
                alignItems: "center",
                gap: 8,
                background: `linear-gradient(135deg, ${C.slate900}, ${C.rose600})`,
                color: "white",
                border: "none",
                padding: "13px 32px",
                borderRadius: 9999,
                fontSize: 15,
                fontWeight: 700,
                cursor: "pointer",
                boxShadow: `0 4px 20px ${C.rose200}`,
              }}
            >
              Try Now <span>→</span>
            </motion.button>
          </motion.div>
        </motion.div>
      </section>
   
      <br/>

      {/* ── FEATURES ── */}
      <section style={{ padding: "80px 6%", background: C.slate100 }}>
        <InView>
          <motion.div
            variants={fadeUpV}
            style={{ textAlign: "center", marginBottom: 52 }}
          >
            <p
              style={{
                fontSize: 12,
                fontWeight: 700,
                letterSpacing: 1.6,
                color: C.rose600,
                textTransform: "uppercase",
                marginBottom: 8,
              }}
            >
              Features
            </p>
            <h2
              style={{
                fontSize: "clamp(24px, 3.5vw, 36px)",
                fontWeight: 800,
                letterSpacing: -0.8,
                color: C.slate900,
              }}
            >
              Everything you need to get hired
            </h2>
            <p style={{ color: C.slate500, marginTop: 10, fontSize: 15 }}>
              Built for job seekers who want more than just job boards.
            </p>
          </motion.div>
        </InView>

        <InView delay={0.05}>
          <div
            style={{
              display: "grid",
              gridTemplateColumns: "repeat(auto-fit, minmax(270px, 1fr))",
              gap: 18,
              maxWidth: 960,
              margin: "0 auto",
            }}
          >
            {features.map((f) => (
              <motion.div
                key={f.title}
                variants={scaleV}
                whileHover={{ y: -5, boxShadow: `0 16px 40px ${C.rose200}80` }}
                transition={{ duration: 0.2 }}
                style={{
                  background: C.white,
                  border: `1px solid ${C.slate300}`,
                  borderRadius: 14,
                  padding: "24px 22px",
                }}
              >
                <div
                  style={{
                    width: 44,
                    height: 44,
                    borderRadius: 10,
                    background: C.rose50,
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "center",
                    fontSize: 22,
                    marginBottom: 14,
                  }}
                >
                  {f.icon}
                </div>
                <h3
                  style={{
                    fontWeight: 700,
                    fontSize: 15,
                    marginBottom: 6,
                    color: C.slate900,
                  }}
                >
                  {f.title}
                </h3>
                <p
                  style={{ color: C.slate500, fontSize: 13, lineHeight: 1.65 }}
                >
                  {f.desc}
                </p>
              </motion.div>
            ))}
          </div>
        </InView>
      </section>

      {/* ── HOW IT WORKS ── */}
      <section
        style={{ padding: "80px 6%", background: C.white, textAlign: "center" }}
      >
        <InView>
          <motion.div variants={fadeUpV} style={{ marginBottom: 52 }}>
            <h2
              style={{
                fontSize: "clamp(24px, 3.5vw, 36px)",
                fontWeight: 800,
                letterSpacing: -0.8,
                background: `linear-gradient(135deg, ${C.rose500}, ${C.rose700})`,
                WebkitBackgroundClip: "text",
                WebkitTextFillColor: "transparent",
                display: "inline-block",
                marginBottom: 8,
              }}
            >
              How It Works
            </h2>
            <p style={{ color: C.slate500, fontSize: 15 }}>
              Get your referral in three simple steps
            </p>
          </motion.div>
        </InView>

        <InView delay={0.05}>
          <div
            style={{
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
              flexWrap: "wrap",
              gap: 4,
              maxWidth: 860,
              margin: "0 auto",
            }}
          >
            {steps.map((step, i) => (
              <div
                key={step.num}
                style={{ display: "flex", alignItems: "center" }}
              >
                <motion.div
                  variants={scaleV}
                  whileHover={{
                    scale: 1.04,
                    boxShadow: `0 10px 32px ${C.rose200}`,
                  }}
                  transition={{ duration: 0.2 }}
                  style={{
                    background: C.white,
                    border: `1px solid ${C.slate300}`,
                    borderRadius: 16,
                    padding: "28px 22px",
                    width: 210,
                    textAlign: "center",
                  }}
                >
                  <div
                    style={{
                      width: 54,
                      height: 54,
                      borderRadius: 14,
                      border: `1px solid ${C.slate300}`,
                      background: C.slate100,
                      display: "flex",
                      alignItems: "center",
                      justifyContent: "center",
                      fontSize: 24,
                      margin: "0 auto 12px",
                    }}
                  >
                    {step.icon}
                  </div>
                  <p
                    style={{
                      fontSize: 11,
                      fontWeight: 700,
                      letterSpacing: 1.5,
                      color: C.rose600,
                      marginBottom: 6,
                    }}
                  >
                    {step.num}
                  </p>
                  <h3
                    style={{
                      fontWeight: 700,
                      fontSize: 15,
                      color: C.slate900,
                      marginBottom: 6,
                    }}
                  >
                    {step.title}
                  </h3>
                  <p
                    style={{ color: C.slate500, fontSize: 13, lineHeight: 1.6 }}
                  >
                    {step.desc}
                  </p>
                </motion.div>
                {i < steps.length - 1 && (
                  <motion.span
                    variants={fadeUpV}
                    style={{
                      fontSize: 20,
                      color: C.rose600,
                      padding: "0 8px",
                      display: "block",
                    }}
                  >
                    →
                  </motion.span>
                )}
              </div>
            ))}
          </div>
        </InView>
      </section>

      {/* ── PRICING ── */}
<section style={{ padding: "80px 6%", background: C.slate100 }}>
  <InView>
    <motion.div
      variants={fadeUpV}
      style={{ textAlign: "center", marginBottom: 48 }}
    >
      <p
        style={{
          fontSize: 12,
          fontWeight: 700,
          letterSpacing: 1.6,
          color: C.rose600,
          textTransform: "uppercase",
          marginBottom: 8,
        }}
      >
        Pricing
      </p>
      <h2
        style={{
          fontSize: "clamp(24px, 3.5vw, 36px)",
          fontWeight: 800,
          letterSpacing: -0.8,
          color: C.slate900,
        }}
      >
        One Plan. Everything Included.
      </h2>
      <p style={{ color: C.slate500, marginTop: 10, fontSize: 15 }}>
        No tiers, no confusion — just full access to JobX.
      </p>
    </motion.div>
  </InView>

  <InView delay={0.05}>
    <motion.div
      variants={slideLeftV}
      whileHover={{ y: -6, boxShadow: `0 24px 56px ${C.rose200}99` }}
      transition={{ duration: 0.22 }}
      style={{
        background: C.white,
        border: `2px solid ${C.rose500}`,
        borderRadius: 20,
        overflow: "hidden",
        maxWidth: 380,
        margin: "0 auto",
      }}
    >
      <div
        style={{
          background: `linear-gradient(135deg, ${C.slate900}, ${C.rose600})`,
          color: "white",
          fontSize: 11,
          fontWeight: 700,
          letterSpacing: 1.2,
          textAlign: "center",
          padding: "7px 0",
        }}
      >
        ⭐ MOST POPULAR
      </div>

      <div style={{ padding: "32px 28px 26px", textAlign: "center" }}>
        <h3
          style={{
            fontWeight: 800,
            fontSize: 18,
            color: C.slate900,
            marginBottom: 6,
          }}
        >
          JobX Pro
        </h3>
        <p style={{ color: C.slate500, fontSize: 13, marginBottom: 22 }}>
          Everything you need to land referrals faster
        </p>

        <div
          style={{
            display: "flex",
            alignItems: "baseline",
            justifyContent: "center",
            gap: 4,
            marginBottom: 4,
          }}
        >
          <span
            style={{ fontSize: 22, fontWeight: 700, color: C.slate500 }}
          >
            ₹
          </span>
          <span
            style={{
              fontSize: 44,
              fontWeight: 800,
              background: `linear-gradient(135deg, ${C.rose500}, ${C.rose700})`,
              WebkitBackgroundClip: "text",
              WebkitTextFillColor: "transparent",
            }}
          >
            999
          </span>
          <span
            style={{ fontSize: 14, color: C.slate500, fontWeight: 600 }}
          >
            /mo
          </span>
        </div>
        <p style={{ color: C.slate500, fontSize: 12, marginBottom: 26 }}>
          Cancel anytime
        </p>

        <ul
          style={{
            listStyle: "none",
            textAlign: "left",
            marginBottom: 26,
          }}
        >
          {[
            "Unlimited job applications",
            "Direct referral requests",
            "AI resume match score",
            "Priority recruiter messaging",
            "Live application tracking",
            "24/7 priority support",
          ].map((p) => (
            <li
              key={p}
              style={{
                fontSize: 14,
                color: C.slate700,
                padding: "7px 0",
                display: "flex",
                alignItems: "center",
                gap: 9,
              }}
            >
              <span
                style={{
                  width: 18,
                  height: 18,
                  borderRadius: "50%",
                  background: C.rose50,
                  color: C.rose600,
                  display: "flex",
                  alignItems: "center",
                  justifyContent: "center",
                  fontSize: 11,
                  fontWeight: 800,
                  flexShrink: 0,
                }}
              >
                ✓
              </span>
              {p}
            </li>
          ))}
        </ul>

        <motion.button
          whileHover={buttonHover}
          whileTap={{ scale: 0.96 }}
          onClick={() => setOpenSignup(true)}
          style={{
            width: "100%",
            padding: "13px 0",
            background: `linear-gradient(135deg, ${C.slate900}, ${C.rose600})`,
            color: "white",
            border: "none",
            borderRadius: 10,
            fontSize: 15,
            fontWeight: 700,
            cursor: "pointer",
            boxShadow: `0 6px 20px ${C.rose200}`,
          }}
        >
          Get Started →
        </motion.button>

        <p style={{ color: C.slate500, fontSize: 11, marginTop: 14 }}>
          Secure payment · Data encrypted
        </p>
      </div>
    </motion.div>
  </InView>
</section>



      {/* ── CTA ── */}
      <InView stagger={false}>
        <motion.div
          variants={fadeUpV}
          style={{
            textAlign: "center",
            padding: "80px 6% 90px",
            background: C.white,
          }}
        >
          <h2
            style={{
              fontWeight: 800,
              fontSize: "clamp(24px, 3.5vw, 36px)",
              color: C.slate900,
              marginBottom: 10,
            }}
          >
            Ready to Save Hours of Job Hunting?
          </h2>
          <p
            style={{
              color: C.slate500,
              fontSize: 15,
              marginBottom: 28,
              maxWidth: 440,
              margin: "0 auto 28px",
            }}
          >
            Transform your job search into a referral machine with our
            AI-powered platform.
          </p>
          <motion.button
            whileHover={buttonHover}
            whileTap={{ scale: 0.96 }}
            onClick={() => setOpenSignup(true)}
            style={{
              display: "inline-flex",
              alignItems: "center",
              gap: 8,
              background: `linear-gradient(135deg, ${C.slate900}, ${C.rose600})`,
              color: "white",
              border: "none",
              padding: "13px 36px",
              borderRadius: 8,
              fontSize: 15,
              fontWeight: 700,
              cursor: "pointer",
            }}
          >
            Get Started <span>→</span>
          </motion.button>
        </motion.div>
      </InView>

      {/* ── FOOTER ── */}
      <footer
        style={{
          background: C.slate900,
          color: "rgba(255,255,255,0.4)",
          padding: "24px 6%",
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          flexWrap: "wrap",
          gap: 12,
        }}
      >
        <div style={{ display: "flex", alignItems: "center", gap: 8 }}>
          <div
            style={{
              width: 28,
              height: 28,
              borderRadius: 6,
              background: `linear-gradient(135deg, ${C.slate800}, ${C.rose600})`,
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
              color: "white",
              fontWeight: 800,
              fontSize: 11,
            }}
          >
            JX
          </div>
          <span style={{ color: "white", fontWeight: 700, fontSize: 15 }}>
            JobX
          </span>
        </div>
        <div style={{ fontSize: 13 }}>Built with React + Framer Motion</div>
        <div style={{ display: "flex", gap: 20, fontSize: 13 }}>
          <span
            style={{ cursor: "pointer", color: "rgba(255,255,255,0.55)" }}
            onClick={() => setOpenLogin(true)}
          >
            Login
          </span>
          <span
            style={{ cursor: "pointer", color: "rgba(255,255,255,0.55)" }}
            onClick={() => setOpenSignup(true)}
          >
            Sign Up
          </span>
          <span style={{ cursor: "pointer", color: "rgba(255,255,255,0.55)" }}>
            Privacy
          </span>
        </div>
      </footer>

      {/* <AuthModal open={openLogin} onOpenChange={setOpenLogin} mode="login" />
      <AuthModal open={openSignup} onOpenChange={setOpenSignup} mode="signup" /> */}
      <AuthModal
    open={authModal.open}
    mode={authModal.mode}
    onOpenChange={(open) =>
        setAuthModal((prev) => ({
            ...prev,
            open
        }))
    }
    setAuthModal={setAuthModal}
/>
    </div>
  );
}