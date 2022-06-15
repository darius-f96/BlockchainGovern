import { B2BContract, B2PContract, UserRole } from "./definitions"


export const userModifyCompanyAllowed = (props : { userRoles:Array<UserRole>}):Boolean => {
    let check:Boolean = false
    Object.values(props.userRoles).map ((role:UserRole) => {
        if (role.appUserId === localStorage.getItem('userid') && (localStorage.getItem("ADMIN") === role.roleTypeId || localStorage.getItem("HR") === role.roleTypeId))
        {
            check = true
        }       
    })
    return check
}

export const userCanAcceptContract = (props: {contract:B2PContract}):Boolean =>{
    return (localStorage.getItem('userid') === props.contract.appUserId && !props.contract.accepted)
}

export const userCanAcceptCompanyContract = (props: {companyId: string, contract:B2BContract}):Boolean =>{
    return (props.companyId === props.contract.companyId2 && !props.contract.accepted)
}