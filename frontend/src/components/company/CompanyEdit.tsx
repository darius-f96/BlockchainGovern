import { Edit, required, SimpleForm, TextInput, useRecordContext } from "react-admin";

export const CompanyEdit = () => (
    useRecordContext(),

    <Edit>
        <SimpleForm>
            <TextInput source="name" validate={required()} />
            <TextInput disabled source="cui" />
            <TextInput disabled source="regIdentifier" />
            <TextInput multiline source="description" validate={required()} />
        </SimpleForm>
    </Edit>
);