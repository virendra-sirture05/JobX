import axios from "axios";
import { ENDPOINTS } from "./endpoints";

const axiosClient = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL,
    withCredentials: true,
    headers: {
        "Content-Type": "application/json"
    }
});
let isRefreshing = false;
let failedQueue = [];

/**
 * Resolve all queued requests
 */
const processQueue = (error = null) => {
    // 1. Snapshot and clear the queue immediately
    const queueToProcess = [...failedQueue];
    failedQueue = [];

    // 2. Process the snapshot
    queueToProcess.forEach((promise) => {
        if (error) {
            promise.reject(error);
        } else {
            promise.resolve();
        }
    });
};


axiosClient.interceptors.request.use(

    (config) => {
              const fullUrl = new URL(
            config.url,
            config.baseURL || window.location.origin
        ).href;
console.log(`backend url is ${import.meta.env.VITE_API_BASE_URL}`);

        console.log(
            "REQUEST",
            config.method?.toUpperCase(),
            config.url
        );
        console.log(`url is ${fullUrl}`);
        

        return config;

    },

    (error) => {

        console.error("REQUEST ERROR", error);

        return Promise.reject(error);

    }

);

axiosClient.interceptors.response.use(

    (response) => {

        console.log(
            "SUCCESS",
            response.status,
            response.config.url
        );

        return response;

    },

    async (error) => {

        console.error(
            "ERROR",
            error.response?.status,
            error.response?.data
        );

        const originalRequest = error.config;

        if (
            error.response?.status !== 401 ||
            originalRequest.url === ENDPOINTS.AUTH.REFRESH
        ) {

            return Promise.reject(error);

        }

        if (originalRequest._retry) {

            console.error("Request already retried.");

            return Promise.reject(error);

        }

        originalRequest._retry = true;

        if (isRefreshing) {

            console.log("Refresh already in progress. Waiting...");

            return new Promise((resolve, reject) => {

                failedQueue.push({
                    resolve,
                    reject
                });

            }).then(() => {

                return axiosClient(originalRequest);

            });

        }

        isRefreshing = true;

        try {

            console.log("Refreshing access token...");

            await axiosClient.post(
                ENDPOINTS.AUTH.REFRESH
            );

            console.log("Refresh successful.");

            processQueue();

            return axiosClient(originalRequest);

        } catch (refreshError) {

            console.error("Refresh failed.");

            processQueue(refreshError);
            // TODO:
            /*
             * Cleanup
             * Example:
             *
             * authService.logout();
             * window.location.href = "/login";
             */

            return Promise.reject(refreshError);

        } finally {

            isRefreshing = false;

        }

    }

);

export default axiosClient;