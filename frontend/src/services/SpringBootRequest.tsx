import { auth } from '../utils/auth';

const SpringBootRequest = async (path:string, method:string, body:object|undefined ) => {
    const request = new Request(`http://localhost:8080/${path}`, {
            method: method,
            body: method!=='GET'?JSON.stringify(body) :null,
            headers: new Headers({ 'Content-Type': 'application/json',
                                    "Access-Control-Allow-Origin": "*",
                                    "Access-Control-Allow-Methods": "DELETE, POST, GET, OPTIONS",
                                    "Access-Control-Allow-Headers": "Content-Type, Authorization, X-Requested-With",
                                    "Authorization" : "Bearer "+ auth() }),
        });
        try {
        const response = await fetch(request);
        if (response.status < 200 || response.status >= 300) {
            console.log('error' , response)
            throw new Error(response.statusText);
        }
        return response.json();
    } catch (err) {
        console.log(err);
    }
            
}

export default SpringBootRequest