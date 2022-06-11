
export const auth = (tk = localStorage.getItem('auth')) => {
    if (tk !== null){
      
      let localtoken = JSON.parse(tk)
      return localtoken.token
    }
    return '';
  }