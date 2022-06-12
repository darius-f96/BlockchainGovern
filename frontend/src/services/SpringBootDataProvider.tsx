import {
  fetchUtils,
  GET_LIST,
  GET_ONE,
  GET_MANY,
  GET_MANY_REFERENCE,
  CREATE,
  UPDATE,
  UPDATE_MANY,
  DELETE,
  DELETE_MANY
} from "react-admin";

import {auth} from '../utils/auth'

interface Pagination {
  page:Number,
  perPage:Number
}
interface Sort{
  field:string,
  order:string
}
interface Params {
  pagination:Pagination,
  sort:Sort,
  filter:Object,
  meta:Object,
  id:string,
  ids:Array<String>,
  data:JSON
}
interface Options{
  method:string,
  body:string | null,
  headers?: Headers
}
interface SimpleResponse{
  status:number,
  headers:Headers,
  body:string,
  json:JSON
}

 export default (apiUrl:String, httpClient = fetchUtils.fetchJson) => {

  const convertDataRequestToHTTP = (type:String, resource:string, params:Params) => {

    const token = auth()
    let url = '';
    let options:Options = {method:'GET', body:null};
    if (!options.headers) {
      options.headers = new Headers({ Accept: 'application/json' });
    }
    console.log("my token is ", token)
    options.headers.set("Authorization", `Bearer ${token}`);
    switch (type) {
      case GET_LIST: {
        const { page, perPage } = params.pagination;
        url = `${apiUrl}/${resource}?page=${page}&pageSize=${perPage}`;
        break;
      }
      case GET_ONE:
        console.log('got to' + resource)
        if (resource === 'appUser/userContext'){
          url = `${apiUrl}/${resource}`
        }
        else {url = `${apiUrl}/${resource}/${params.id}`}
        break;
      case GET_MANY: {
        const query = {
          filter: JSON.stringify({ id: params.ids })
        };
        let idStr = "";
        const queryString = params.ids.map(id => idStr + `id=${id}`);
        url = `${apiUrl}/${resource}?${queryString}`
        break;
      }
      case GET_MANY_REFERENCE: {
        const { page, perPage } = params.pagination;
        url = `${apiUrl}/${resource}?page=${page}&pageSize=${perPage}`;
        break;
      }
      case UPDATE:
        url = `${apiUrl}/${resource}/${params.id}`;
        options.method = "PUT";
        options.body = JSON.stringify(params.data);
        break;
      case CREATE:
        url = `${apiUrl}/${resource}`;
        options.method = "POST";
        options.body = JSON.stringify(params.data);
        break;
      case DELETE:
        url = `${apiUrl}/${resource}/${params.id}`;
        options.method = "DELETE";
        break;
      default:
        throw new Error(`Unsupported fetch action type ${type}`);
    }
    console.log('got here to', options.body, options.method)
    return { url, options };
  };

  const convertHTTPResponse = (response:SimpleResponse, type:String, resource:String, params:Params) => {
    let arr:any[] = []
    let totalElements:number = 0
    const json = response.json;
    Object.entries(json).map( ([key, val]) => {
      if (typeof(val) === 'object'){
        arr = val
      }
      if (key === 'totalElements'){
        totalElements = val
      }
    })

    switch (type) {
      case GET_MANY_REFERENCE:
      case GET_LIST:
      case GET_MANY:
        if (!json.hasOwnProperty("totalElements")) {
          throw new Error(
            "The totalElements property must be must be present in the Json response"
          );
        }
        console.log(response)
        return {
          data: arr,
          total: totalElements
        };
      case CREATE:
        return { data: { ...params.data, id: json } };
      default:
        return { data: json };
    }
  };

  return async (type:string, resource:string, params:Params) => {
    
    // simple-rest doesn't handle filters on UPDATE route, so we fallback to calling UPDATE n times instead
    if (type === UPDATE_MANY) {
      return Promise.all(
        params.ids.map(id =>
          httpClient(`${apiUrl}/${resource}/${id}`, {
            method: "PUT",
            body: JSON.stringify(params.data)
          })
        )
      ).then(responses => ({
        data: responses.map(response => response.json)
      }));
    }
    // simple-rest doesn't handle filters on DELETE route, so we fallback to calling DELETE n times instead
    if (type === DELETE_MANY) {
      return  Promise.all(
        params.ids.map(id =>
          httpClient(`${apiUrl}/${resource}/${id}`, {
            method: "DELETE"
          })
        )
      ).then(responses => ({
        data: responses.map(response => response.json)
      }));
    }

    const { url, options } = convertDataRequestToHTTP(type, resource, params);
    return await httpClient(url, options).then(response => {
      if (response.status !== 204){
        return convertHTTPResponse(response, type, resource, params)
      }
    }
    ).catch( err =>{
      console.log(err)
    })
  };

};