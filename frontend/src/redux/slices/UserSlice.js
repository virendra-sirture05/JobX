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
    },
  ],
};

const usersSlice = createSlice({
  name: "users",
  initialState,
  reducers: {
    addUser: (state, action) => {
      state.users.push(action.payload);
    },

    deleteUser: (state, action) => {
      state.users = state.users.filter(
        (user) => user.id !== action.payload
      );
    },
  },
});

export const { addUser, deleteUser } = usersSlice.actions;

export default usersSlice.reducer;