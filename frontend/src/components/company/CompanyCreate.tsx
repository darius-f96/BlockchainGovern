import { Create, required, SimpleForm, TextInput } from "react-admin";

export const CompanyCreate = () => (
    <Create>
        <SimpleForm>
            <TextInput source="name" validate={[required()]} fullWidth />
            <TextInput source="cui" label="CUI" validate={[required()]}/>
            <TextInput source="regIdentifier" label="regIdentifier" validate={[required()]}/>
            <TextInput source="description" label="Description" />
        </SimpleForm>
    </Create>
);
