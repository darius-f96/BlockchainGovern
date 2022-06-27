import toast from 'react-hot-toast';
import { auth, getAuthToken } from '../utils/auth';

const SpringBootRequest = async (path:string, method:string, body:object|undefined ) => {

    const token = await auth()

    const request = new Request(`http://localhost:8080/${path}`, {
            method: method,
            body: method!=='GET'?JSON.stringify(body) :null,
            headers: new Headers({ 'Content-Type': 'application/json',
                                    "Access-Control-Allow-Origin": "*",
                                    "Access-Control-Allow-Methods": "DELETE, POST, GET, OPTIONS",
                                    "Access-Control-Allow-Headers": "Content-Type, Authorization, X-Requested-With",
                                    "Authorization" : "Bearer "+ token  }),
        })
        try {
        const response = await fetch(request)
        switch (response.status){
            case 404 : toast.error("Record not found, please refresh your page"); break
            case 500 : toast.error("Unknown error occurred...data not saved"); break
            case 401 : toast.error("Unauthorized access, you do not have permission to do that!"); break
            case 417 : toast.error("Inconsistent data detected...data not saved"); break
            case 204 :toast.success("Data removed successfuly!"); break
            case 201 : toast.success("Data was saved successfuly!"); break
            case 200 : if (method==='POST'||method==='PUT') toast.success("Data was saved successfuly!")
        }
        if (response.status < 200 || response.status >= 300) {

            throw new Error(response.statusText);
        }
        return response.json();
        } catch (err) {
            console.log(err)
        }
            
}

export default SpringBootRequest