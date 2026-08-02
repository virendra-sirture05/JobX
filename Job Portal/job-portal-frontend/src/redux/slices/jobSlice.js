import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  jobs: [
    {
      id: 1,
      title: "Frontend Developer",
      company: "Google",
      category: "IT",
      referrer: "Internal",
      applications: 24,
      status: "Active",
      posted: "2025-06-01",
    },
    {
      id: 2,
      title: "Backend Developer",
      company: "Amazon",
      category: "IT",
      referrer: "Referral",
      applications: 18,
      status: "Closed",
      posted: "2025-05-21",
    },
  ],

  appliedJobs: [],
};

const jobSlice = createSlice({
  name: "jobs",
  initialState,

  reducers: {
    addJob: (state, action) => {
      state.jobs.push(action.payload);
    },

    deleteJob: (state, action) => {
      state.jobs = state.jobs.filter((job) => job.id !== action.payload);
    },

    toggleJobStatus: (state, action) => {
      const job = state.jobs.find((job) => job.id === action.payload);

      if (job) {
        job.status = job.status === "Active" ? "Closed" : "Active";
      }
    },

    applyJob: (state, action) => {
      const jobId =
        typeof action.payload === "object"
          ? action.payload?.id
          : action.payload;
      const existingJob = state.jobs.find((job) => job.id === jobId);

      if (
        existingJob &&
        !state.appliedJobs.some((job) => job.id === existingJob.id)
      ) {
        state.appliedJobs.push(existingJob);
      }
    },
  },
});

export const { addJob, deleteJob, toggleJobStatus, applyJob } =
  jobSlice.actions;

export default jobSlice.reducer;
