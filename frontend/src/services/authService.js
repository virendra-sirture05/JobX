import axiosClient from "@/api/axiosClient";
import { ENDPOINTS } from "@/api/endpoints";

class AuthService {

    async login(data) {

        const response = await axiosClient.post(
            ENDPOINTS.AUTH.LOGIN,
            data
        );

        return response.data;

    }

    async logout() {

        const response = await axiosClient.post(
            ENDPOINTS.AUTH.LOGOUT
        );

        return response.data;

    }

    async sendOtp(data) {

        const response = await axiosClient.post(
            ENDPOINTS.AUTH.SEND_OTP,
            data
        );

        return response.data;

    }

    async verifyOtp(data) {

        const response = await axiosClient.post(
            ENDPOINTS.AUTH.VERIFY_OTP,
            data
        );

        return response.data;

    }

    async me() {

        const response = await axiosClient.get(
            ENDPOINTS.AUTH.ME
        );

        return response.data;

    }

}

export default new AuthService();