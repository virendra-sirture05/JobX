import { useDispatch, useSelector } from "react-redux";
import {
  deleteUser,
  toggleUserStatus,
} from "../../redux/slices/UserSlice";
import { Navbar } from "./Navbar";

function ManageUsers() {
  const dispatch = useDispatch();

  const users = useSelector(
    (state) => state.users.users
  );

  return (
    <div className="p-5">
      <Navbar/>
      <h1 className="text-2xl font-bold mb-5">
        Manage Users
      </h1>

      <table className="w-full border">

        <thead>
          <tr className="bg-gray-200">
            <th className="p-2">Name</th>
            <th className="p-2">Email</th>
            <th className="p-2">Role</th>
            <th className="p-2">Status</th>
            <th className="p-2">Actions</th>
          </tr>
        </thead>

        <tbody>

          {users.map((user) => (
            <tr
              key={user.id}
              className="border-t"
            >
              <td className="p-2">
                {user.name}
              </td>

              <td className="p-2">
                {user.email}
              </td>

              <td className="p-2">
                {user.role}
              </td>

              <td className="p-2">
                {user.status}
              </td>

              <td className="p-2 flex gap-2">

                <button
                  onClick={() =>
                    dispatch(
                      toggleUserStatus(
                        user.id
                      )
                    )
                  }
                  className="bg-yellow-500 text-white px-2 py-1 rounded"
                >
                  Toggle
                </button>
 
                <button
                  onClick={() =>
                    dispatch(
                      deleteUser(
                        user.id
                      )
                    )
                  }
                  className="bg-red-500 text-white px-2 py-1 rounded"
                >
                  Delete
                </button>

              </td>
            </tr>
          ))}

        </tbody>

      </table>
    </div>
  );
}

export default ManageUsers;