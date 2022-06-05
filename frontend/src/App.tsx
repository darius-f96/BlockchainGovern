import * as React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/css/bootstrap-grid.min.css'

import ErrorBoundary from './components/ErrorBoundary';
import AppUserComponent from './components/AppUserComponent';
import { Admin, CustomRoutes, Authenticated, Resource, ListGuesser  } from 'react-admin';
import SpringBootDataProvider from './services/SpringBootDataProvider';
import authProvider from './services/AuthProvider';
import {Route} from 'react-router-dom'
import ProfileComponent from './components/ProfileComponent';

function App() {
  const dataProvider = SpringBootDataProvider("http://localhost:8080")

  return (
    <ErrorBoundary>
      <div className="App">
      
          <Admin dataProvider={dataProvider} authProvider={authProvider}>
            <Resource name='person' list={ListGuesser}></Resource>
            <CustomRoutes>
              <Route path="/profile" element={<Authenticated><AppUserComponent children=""></AppUserComponent><ProfileComponent children=""/></Authenticated>} />
            </CustomRoutes>
          </Admin>
      
      </div>
    </ErrorBoundary>
  );
}

export default App;
