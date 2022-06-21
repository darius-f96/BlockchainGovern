import * as React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/css/bootstrap-grid.min.css'

import ErrorBoundary from './components/ErrorBoundary';
import { Admin, CustomRoutes, Authenticated, Resource, ListGuesser, ShowGuesser, List, EditGuesser, Button  } from 'react-admin';
import SpringBootDataProvider from './services/SpringBootDataProvider';
import authProvider from './services/AuthProvider';
import {Route} from 'react-router-dom'
import {ProfileComponent} from './components/user/ProfileComponent';
import MyAppLayout from './components/navigation/AppLayout';
import { CompanyCreate } from './components/company/CompanyCreate';
import { CompanyList } from './components/company/CompanyList';
import { CompanyEdit } from './components/company/CompanyEdit';
import { CreateProfile } from './components/user/CreateProfile';
import { CompanyShow } from './components/company/CompanyShow';
import SpringBootRequest from './services/SpringBootRequest';
import { metamask, provider, web3 } from './utils/provider';
import toast, { Toaster } from 'react-hot-toast';
import LoginPage from './components/LoginPage';


function App() {
  const dataProvider = SpringBootDataProvider("http://localhost:8080")
  
  if (!metamask){
    toast.error("You must to install Metamask in order to use this app!")
  }

  return (
    <ErrorBoundary>
      <div id="main" className="App" >
        <Toaster
            position="top-center"
            reverseOrder={true}
          />
          <Admin  dataProvider={dataProvider} 
                  authProvider={authProvider}
                  layout={MyAppLayout}
                  loginPage={LoginPage}
          >
            <Resource name='company' list={CompanyList} create={CompanyCreate} edit={CompanyEdit} show={CompanyShow}></Resource>
            <CustomRoutes>
              
              <Route path="/profile" element={<ProfileComponent />} />
              <Route path="/createprofile" element={<CreateProfile />} />
            </CustomRoutes>
          </Admin>
      
        </div>
    </ErrorBoundary>
  );
}

export default App;
