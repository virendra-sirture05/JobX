import { Send } from "lucide-react";

export default function MessageInput() {
  return (
    <div className="border-t bg-white p-4">
      <div className="flex gap-3">

        <input
          placeholder="Type a message..."
          className="
            flex-1
            border
            rounded-lg
            px-4
            py-3
          "
        />

        <button
          className="
            bg-[#1a56a0]
            text-white
            px-5
            rounded-lg
          "
        >
          <Send size={18} />
        </button>

      </div>
    </div>
  );
}