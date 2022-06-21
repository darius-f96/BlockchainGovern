import cookie from 'react-cookies'

export const getAuthToken = (tk = cookie.load("authorization")) => {
    if (tk)
      return tk
    return '';
  }

  
export const auth = async (tk = cookie.load("authorization")) => {
  
  if (tk){
    return tk
  }
  else {
    console.log("refreshing token", tk)
    const refreshtkn = cookie.load("refreshtkn")

    const request = new Request('http://localhost:8080/appUser/rfrtkn', {
            method: 'GET',
            headers: new Headers({ 'Content-Type': 'application/json',
                                    "Access-Control-Allow-Origin": "*",
                                    "Access-Control-Allow-Methods": "DELETE, POST, GET, OPTIONS",
                                    "Access-Control-Allow-Headers": "Content-Type, Authorization, X-Requested-With",
                                    "Authorization": "Bearer " + refreshtkn }),
        });
        fetch(request)
            .then(response => {

                if (response.status < 200 || response.status >= 300) {
                    throw new Error(response.statusText);
                }
                const accessExpires = new Date() 
                accessExpires.setMinutes(accessExpires.getMinutes() + 1)
                const accessToken = response.headers.get("access_token")
                tk = accessToken
                if (accessToken)
                    cookie.save("authorization", accessToken, {
                        path: '/',
                        expires: accessExpires,
                        maxAge : 1000,
                        secure: true,
                    })
                    window.location.reload()
                    return tk
        }).then(resp =>{
          return resp
        })
  }
}