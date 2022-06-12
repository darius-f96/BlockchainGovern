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
    SimpleList
} from 'react-admin';
import { CompanyDetails } from './CompanyDetails';

const ListActions = () => (
    <TopToolbar>
        <CreateButton/>
        <ExportButton/>
        
    </TopToolbar>
);

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
            <EditButton/>
            
        </Datagrid>
    </List>
);