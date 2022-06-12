import React from "react";
import { UserMenu, MenuItemLink, Logout } from "react-admin";
import SettingsIcon from '@mui/icons-material/Settings';

const MyUserMenu = (props) => {
 
  return (
    <UserMenu {...props}>
      <MenuItemLink
        to="/profile"
        primaryText="My Profile"
        leftIcon={<SettingsIcon />}
      />
      <Logout></Logout>
    </UserMenu>
  );
};

export default MyUserMenu;