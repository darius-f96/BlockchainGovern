import { Layout } from "react-admin";
import MyAppBar from "./AppBar";


const MyAppLayout = props => <Layout {...props} appBar={MyAppBar} />;

export default MyAppLayout;