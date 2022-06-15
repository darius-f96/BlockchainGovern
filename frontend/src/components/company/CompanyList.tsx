import { 
    TopToolbar,
    FilterButton,
    CreateButton,
    ExportButton,
    Button,
    List,
    Datagrid,
    TextField,
    EditButton,
    SimpleList,
    ShowButton,
    FunctionField
} from 'react-admin';
import { userModifyCompanyAllowed } from '../../utils/isUserAllowed';
import { ListActions } from '../../utils/ListActions';
import { CompanyDetails } from './CompanyDetails';

export const CompanyList = () => (

    <List actions={<ListActions/>}>
          <Datagrid
            isRowSelectable={ record => true}
            isRowExpandable={row => true}
            expand={<CompanyDetails/>}
            >
            <TextField source="name" />
            <TextField source="cui" />
            <TextField source="regIdentifier" />
            <TextField source="description" />
            {/* <EditButton/> */}
            <FunctionField render={(record:any) => userModifyCompanyAllowed({userRoles:record.userRoles}) && <EditButton/> } />
            <ShowButton/>
        </Datagrid>
    </List>
);