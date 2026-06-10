import { useState } from "react";
import MessageSidebar from "@/components/ui/seeker/MessageSidebar";
import MessageChat from "@/components/ui/seeker/MessageChat";
import MessageInput from "@/components/ui/seeker/MessageInput";

export default function MessagesPage() {
const conversations = [
  {
    id: 1,
    name: "HR Team",
    role: "Recruiter",
    unread: true,
    time: "2m ago",
    avatar: "HR",
    lastMessage: "Interview schedule updated",
  },
  {
    id: 2,
    name: "Jane Smith",
    role: "Referrer",
    unread: true,
    time: "1h ago",
    avatar: "JS",
    lastMessage: "Your referral has been accepted",
  },
  {
    id: 3,
    name: "System",
    role: "Platform",
    unread: false,
    time: "2h ago",
    avatar: "SY",
    lastMessage: "New job matching your profile",
  },
];
  const [selected, setSelected] =
    useState(conversations[0]);

  return (
    <div
      className="
        h-[calc(100vh-64px)]
        flex
        bg-white
      "
    >
      <MessageSidebar
        conversations={conversations}
        selected={selected}
        setSelected={setSelected}
      />

      <div className="flex flex-col flex-1">
        <MessageChat selected={selected} />
        <MessageInput />
      </div>
    </div>
  );
}