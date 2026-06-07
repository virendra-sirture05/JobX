import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  jobs: [
    {
      id: 1,
      title: "AI Engineer",
      company: "Google",
      location: "New York",
      category: "Engineering",
    },
  ],
};

const jobSlice = createSlice({
  name: "jobs",
  initialState,

  reducers: {
    addJob: (state, action) => {
      state.jobs.push(action.payload);
    },
  },
});

export const { addJob } = jobSlice.actions;
export default jobSlice.reducer;