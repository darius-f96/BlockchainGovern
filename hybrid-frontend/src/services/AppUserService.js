import axios from 'axios'

const AppUser_REST_API='http://localhost:8080/appUser';

class AppUserService {
    async getAppUsers(){
        const response = await axios.get(AppUser_REST_API)
        console.log(response)
        return response;
    }
}

export default new AppUserService()