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

function App() {
  const dataProvider = SpringBootDataProvider("http://localhost:8080")
  return (
    <ErrorBoundary>
      <div className="App">
      
          <Admin  dataProvider={dataProvider} 
                  authProvider={authProvider}
                  layout={MyAppLayout}
          >
            <Button></Button>
            <Resource name='company' list={CompanyList} create={CompanyCreate} edit={CompanyEdit}></Resource>
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
