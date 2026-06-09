import { Search } from "lucide-react";

export default function MessageSidebar({
  conversations,
  selected,
  setSelected,
}) {
  return (
    <div className="w-80 border-r bg-white">

      <div className="p-4 border-b">
        <h2 className="text-xl font-bold">
          Messages
        </h2>

        <div className="relative mt-3">
          <Search
            size={18}
            className="absolute left-3 top-3"
          />

          <input
            placeholder="Search..."
            className="
              w-full
              border
              rounded-lg
              pl-10
              py-2
            "
          />
        </div>
      </div>

      <div>
        {conversations.map((chat) => (
          <button
            key={chat.id}
            onClick={() => setSelected(chat)}
            className={`
              w-full
              p-4
              flex
              gap-3
              border-b
              text-left
              hover:bg-slate-50

              ${
                selected?.id === chat.id
                  ? "bg-blue-50"
                  : ""
              }
            `}
          >
            <div
              className="
                h-12
                w-12
                rounded-full
                bg-[#1a56a0]
                text-white
                flex
                items-center
                justify-center
                font-semibold
              "
            >
              {chat.avatar}
            </div>

            <div className="flex-1">
              <div className="flex justify-between">
                <h3 className="font-semibold">
                  {chat.name}
                </h3>

                <span className="text-xs text-gray-500">
                  {chat.time}
                </span>
              </div>

              <p className="text-sm text-gray-500">
                {chat.lastMessage}
              </p>
            </div>
          </button>
        ))}
      </div>
    </div>
  );
}