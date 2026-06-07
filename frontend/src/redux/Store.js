import {configureStore} from '@reduxjs/toolkit'

import usersReducer from './slices/UserSlice'
import authReducer from './slices/AuthSlice'
const Store = configureStore({
    reducer:{
        user : usersReducer,
        auth : authReducer
        
    }
});

export default Store;