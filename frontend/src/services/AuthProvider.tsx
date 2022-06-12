interface parameters {
    username : any
    password:any
}
const authProvider = {
    
    login: async ({ username, password } : parameters) => {
        const request = new Request('http://localhost:8080/appUser/signin', {
            method: 'POST',
            body: JSON.stringify({ username, password }),
            headers: new Headers({ 'Content-Type': 'application/json',
                                    "Access-Control-Allow-Origin": "*",
                                    "Access-Control-Allow-Methods": "DELETE, POST, GET, OPTIONS",
                                    "Access-Control-Allow-Headers": "Content-Type, Authorization, X-Requested-With" }),
        });
        return fetch(request)
            .then(response => {

                if (response.status < 200 || response.status >= 300) {
                    throw new Error(response.statusText);
                }
                return response.json();
            })
            .then(auth => {
                localStorage.setItem('auth', JSON.stringify(auth));
              
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
    checkAuth: () => localStorage.getItem('auth')
    ? Promise.resolve()
    : Promise.reject(),
    getPermissions: () => {
        // Required for the authentication to work
        return Promise.resolve();
    },
    logout: () => {
        localStorage.removeItem('auth');
        return Promise.resolve();
    },
};

export default authProvider;