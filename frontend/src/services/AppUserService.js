import axios from 'axios'

const AppUser_REST_API='http://localhost:8080/appUser';

class AppUserService {
    async getAppUsers(){
        const response = await axios.get(AppUser_REST_API)
        .catch(error => {
            console.log('There is an error with the get operation: ' + error.message)
            throw error            
        })
        console.log(response)
        return response;
    }
}

export default new AppUserService()