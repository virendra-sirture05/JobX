import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  users: [
    {
      id: 1,
      name: "Admin",
      email: "admin@gmail.com",
      password: "admin123",
      mobile: "9999999999",
      role: "admin",
      status: "Active",
    },
    {
      id: 2,
      name: "Rahul Sharma",
      email: "rahul@gmail.com",
      password: "rahul123",
      mobile: "9876543210",
      role: "user",
      status: "Active",
    },
    {
      id: 3,
      name: "Priya Mehta",
      email: "priya@gmail.com",
      password: "priya123",
      mobile: "9123456780",
      role: "recruiter",
      status: "Active",
    },
    {
      id: 4,
      name: "Aman Verma",
      email: "aman@gmail.com",
      password: "aman123",
      mobile: "9988776655",
      role: "user",
      status: "Suspended",
    },
    {
      id: 5,
      name: "Neha Singh",
      email: "neha@gmail.com",
      password: "neha123",
      mobile: "8877665544",
      role: "recruiter",
      status: "Active",
    },
  ],
};

const userSlice = createSlice({
  name: "users",
  initialState,

  reducers: {
    addUser: (state, action) => {
      state.users.push(action.payload);
    },

    deleteUser: (state, action) => {
      state.users = state.users.filter((user) => user.id !== action.payload);
    },

    toggleUserStatus: (state, action) => {
      const user = state.users.find((user) => user.id === action.payload);

      if (user) {
        user.status = user.status === "Active" ? "Suspended" : "Active";
      }
    },
  },
});

export const { addUser, deleteUser, toggleUserStatus } = userSlice.actions;

export default userSlice.reducer;
