import { Star } from "lucide-react";

const reviews = [
  {
    name: "Jane Smith",
    company: "Google",
    rating: 5,
    comment: "Very genuine",
  },
  {
    name: "Emily Davis",
    company: "Adobe",
    rating: 5,
    comment: "Quick response",
  },
];

export default function ReferrerReviewsTable() {
  return (
    <div className="bg-white border rounded-2xl shadow-sm">
      <div className="p-5 border-b">
        <h2 className="font-bold text-lg">
          Referrer Credibility Reviews
        </h2>
      </div>

      <table className="w-full">
        <thead>
          <tr className="bg-slate-50">
            <th className="p-4 text-left">
              Referrer
            </th>

            <th className="p-4 text-left">
              Company
            </th>

            <th className="p-4 text-left">
              Rating
            </th>

            <th className="p-4 text-left">
              Comment
            </th>
          </tr>
        </thead>

        <tbody>
          {reviews.map((review) => (
            <tr
              key={review.name}
              className="border-t"
            >
              <td className="p-4">
                {review.name}
              </td>

              <td className="p-4">
                {review.company}
              </td>

              <td className="p-4">
                <div className="flex gap-1">
                  {[...Array(review.rating)].map(
                    (_, index) => (
                      <Star
                        key={index}
                        size={16}
                        fill="currentColor"
                        className="text-yellow-500"
                      />
                    )
                  )}
                </div>
              </td>

              <td className="p-4">
                {review.comment}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}