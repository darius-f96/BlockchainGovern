import { Datagrid, EmailField, List, EditButton, TextField, useRecordContext, useResourceContext, Edit, SimpleForm, TextInput } from 'react-admin';

export const AppuserList = () => (
    <List>
        <Datagrid rowClick="edit"
            isRowSelectable={ record => true}
            // isRowExpandable={row => false}
            expand={<EditView/>}>
            <EmailField source="email" />
            <TextField source="password" />
            <TextField source="username" />
            <EditButton/>
        </Datagrid>
    </List>
);

const EditView = () => {
    const record = useRecordContext()
    const resource = useResourceContext()
    
    return (
        <Edit
            resource={resource}
            id={record.id}
            /* disable the app title change when shown */
            title=" "
        >
            <SimpleForm>
                <TextInput source="email" />
            </SimpleForm>
        </Edit>
    )
}