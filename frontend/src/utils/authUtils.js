export const authUtils = {

    isAuthenticated(){

        return document.cookie
            .includes("loggedIn=true");

    }

}