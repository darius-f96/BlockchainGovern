import * as React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/css/bootstrap-grid.min.css'

import ErrorBoundary from './components/ErrorBoundary';
import { Admin, CustomRoutes, Authenticated, Resource, ListGuesser, ShowGuesser, List  } from 'react-admin';
import SpringBootDataProvider from './services/SpringBootDataProvider';
import authProvider from './services/AuthProvider';
import {Route} from 'react-router-dom'
import {ProfileComponent} from './components/ProfileComponent';
import MyAppLayout from './components/AppLayout';

function App() {
  const dataProvider = SpringBootDataProvider("http://localhost:8080")


  return (
    <ErrorBoundary>
      <div className="App">
      
          <Admin  dataProvider={dataProvider} 
                  authProvider={authProvider}
                  layout={MyAppLayout}
          >
            <Resource name='person' list={ListGuesser}></Resource>
            <CustomRoutes>
              
              <Route path="/profile" element={<ProfileComponent />} />
            </CustomRoutes>
          </Admin>
      
      </div>
    </ErrorBoundary>
  );
}

export default App;
