import {configureStore} from '@reduxjs/toolkit'

import usersReducer from './slices/UserSlice'
import authReducer from './slices/AuthSlice'
import jobReducer from "./slices/jobSlice";
const Store = configureStore({
    reducer:{
        users : usersReducer,
        auth : authReducer,
        jobs : jobReducer

    }
});

export default Store;