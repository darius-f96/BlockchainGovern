import SpringBootRequest from "./SpringBootRequest";
import cookie from 'react-cookies'
import { auth, getAuthToken } from "../utils/auth";

interface parameters {
    username : any
    password:any
}
const authProvider = {
    
    login: async ({ username, password } : parameters) => {
        const request = new Request('http://localhost:8080/appUser/signin', {
            method: 'POST',
            body: JSON.stringify({ username, password }),
            mode:'cors',
            headers: new Headers({ 'Content-Type': 'application/json',
                                    "Access-Control-Allow-Origin": "*",
                                    "Access-Control-Allow-Methods": "DELETE, POST, GET, OPTIONS",
                                    "Access-Control-Allow-Headers": "Content-Type, Authorization, access_token, refresh_token, X-Requested-With",
                                    "Access-Control-Expose-Headers": "access_token" }),
        });
        return fetch(request)
            .then(response => {

                if (response.status < 200 || response.status >= 300) {
                    throw new Error(response.statusText);
                }
                const accessExpires = new Date() 
                const refreshExpires = new Date()
                accessExpires.setMinutes(accessExpires.getMinutes() + 10)
                refreshExpires.setHours(refreshExpires.getHours() + 4)
                const accessToken = response.headers.get("access_token")
                const refreshToken = response.headers.get("refresh_token")
                if (accessToken)
                    cookie.save("authorization", accessToken, {
                        path: '/',
                        expires: accessExpires,
                        secure: true,
                    })
                if (refreshToken)
                    cookie.save("refreshtkn", refreshToken, {
                        path: '/',
                        expires: refreshExpires,
                        secure: true,
                    })
            })
            .then(() => {
                SpringBootRequest('appUser/userContext', "GET", undefined).then(resp=>{
                    localStorage.setItem('userid', resp.appUserId)
                    localStorage.setItem('username', resp.username)
                })
                SpringBootRequest('roleType', 'GET', undefined).then(response => Object.values(response).map((obj:any) => localStorage.setItem(obj.roleCode, obj.id)))
            })
            .catch(() => {
                throw new Error('Network error');
            });
    },
    checkError: (error:Response) => {
        const status = error.status;
        if (status === 401 || status === 403) {
            localStorage.removeItem('auth');
            return Promise.reject();
        }
        
        return Promise.resolve();
    },
    checkAuth: async() => {
        const token = getAuthToken()
        return token !== ''
        ? Promise.resolve()
        : Promise.reject()
    },
    
    getPermissions: () => {
        // Required for the authentication to work
        return Promise.resolve();
    },
    logout: () => {
        localStorage.removeItem('auth')
        localStorage.removeItem('userid')
        localStorage.removeItem('username')
        cookie.remove('authorization', { path: '/' })
        cookie.remove('refreshtkn', { path: '/' })
        return Promise.resolve();
    },
};

export default authProvider;