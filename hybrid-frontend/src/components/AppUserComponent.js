import React from 'react';
import AppUserService from '../services/AppUserService';

class AppUserComponent extends React.Component {
    
    state = {  } 

    constructor(props){
        super(props)
        this.state = {
            appusers:[]
        }
    }

    componentDidMount(){
        AppUserService.getAppUsers().then(response => {
            if (response.status != 204)
             {this.setState( {appusers : response.data} )}
             else {alert("No data")}
        })
        
    }
    
    render() { 
        return (
            <div>
                <h1 className='text-center'> App Users</h1> 
                <table className='table table-striped'>
                    <thead>
                        <tr>
                            <td>Username</td>
                            <td>UserEmail</td>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            this.state.appusers.map(
                                appuser =>
                                <tr key={appuser.AppUserId}>
                                    <td>{appuser.Username}</td> 
                                    <td>{appuser.Email}</td> 

                                </tr>
                            )
                        }
                    </tbody>
                </table>

            </div>
        );
        
    }
}
 
export default AppUserComponent;