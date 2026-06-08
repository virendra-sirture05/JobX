import {createSlice} from "@reduxjs/toolkit";

const initialState={
  jobs:[
    {
      id:1,
      title:"AI Engineer",
      company:"Google",
      location:"New York",
      category:"Engineering",
    },
  ],
  appliedJobs:[],
};

const jobSlice=createSlice({
  name:"jobs",
  initialState,
  reducers:{
    addJob:(state,action)=>{
      state.jobs.push(action.payload);
    },
    applyJob:(state,action)=>{
      const existingJob=state.jobs.find((job)=>job.id===action.payload.id);

      if(existingJob&&!state.appliedJobs.some((job)=>job.id===existingJob.id)){
        state.appliedJobs.push(existingJob);
      }
    },
  },
});

export const {addJob,applyJob}=jobSlice.actions;
export default jobSlice.reducer;