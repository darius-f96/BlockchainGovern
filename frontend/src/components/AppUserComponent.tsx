import React from 'react';
import { Props, AppUserData } from '../utils/definitions';

class AppUserComponent extends React.Component<Props, AppUserData>{
    
    mockup : AppUserData = {
        appusers: [
            {AppUserId : 'adad',
            Username: 'test12',
            Email: 'testmail@temp-mail.org'},
            {AppUserId : 'adad',
            Username: 'test12',
            Email: 'testmail@temp-mail.org'},
            {AppUserId : 'adad',
            Username: 'test12',
            Email: 'testmail@temp-mail.org'},
            {AppUserId : 'adad',
            Username: 'test12',
            Email: 'testmail@temp-mail.org'},
            {AppUserId : 'adad',
            Username: 'test12',
            Email: 'testmail@temp-mail.org'},
            {AppUserId : 'adad',
            Username: 'test12',
            Email: 'testmail@temp-mail.org'},
            {AppUserId : 'adad',
            Username: 'test12',
            Email: 'testmail@temp-mail.org'},
            {AppUserId : 'adad',
            Username: 'test12',
            Email: 'testmail@temp-mail.org'}
        ]
    }

    constructor(props : Props){
        super(props)
        this.state = {appusers : undefined}
    }

    // componentDidMount(){
    //     AppUserService.getAppUsers().then(response => {
    //         if (response.status !== 204)
    //          {this.setState( {appusers : response.data} )}
    //          else { this.setState( {appusers : undefined})}
    //     })
    //     .catch(error => {
    //         alert(error.message)      
    //     })
        
    // }

    displayData() {
        return(
                <div>
                    <h1 className='text-center'> App Users</h1> 
                    <div className='container'>
                        <div className='row'>
                                <div className='col'>Username</div>
                                <div className='col'>User Email</div>
                                <div className='col'></div>
                        </div>
                            {
                                this.mockup.appusers?.map(
                                    appuser =>
                                    <div className='row' key={appuser.AppUserId}>
                                        <div className='col'>{appuser.Username}</div> 
                                        <div className='col'>{appuser.Email}</div>
                                        <div className='col'>
                                            <button type="button" className="btn btn-warning">Edit</button>
                                            <button type="button" className="btn btn-danger">Delete</button>
                                        </div>
                                    </div>           
                                )
                            }
                    </div>
    
                </div>
            );
    }

    render(): JSX.Element { 
        return (
                this.displayData()
        );
        
    }
}
 
export default AppUserComponent;