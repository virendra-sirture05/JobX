export default function MessageChat({
  selected,
}) {
  if (!selected) {
    return (
      <div className="flex-1 flex items-center justify-center">
        Select a conversation
      </div>
    );
  }

  return (
    <div className="flex flex-col flex-1">

      <div className="border-b p-4 bg-white">
        <h2 className="font-bold">
          {selected.name}
        </h2>

        <p className="text-sm text-gray-500">
          {selected.role}
        </p>
      </div>

      <div className="flex-1 p-6 bg-slate-50">

        <div className="flex justify-start mb-4">
          <div
            className="
              bg-white
              border
              rounded-xl
              px-4
              py-2
            "
          >
            Hello, we reviewed your profile.
          </div>
        </div>

        <div className="flex justify-end mb-4">
          <div
            className="
              bg-[#1a56a0]
              text-white
              rounded-xl
              px-4
              py-2
            "
          >
            Thank you.
          </div>
        </div>

      </div>
    </div>
  );
}