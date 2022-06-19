import { Box, Button, Typography } from "@mui/material";
import { CreateButton } from "react-admin";
import { Navigate } from "react-router-dom";
import { CreateProfile } from "./CreateProfile";

export const Empty = () => (
    <div>
        <Box textAlign="center" m={1}>
            <Typography variant="h4" paragraph>
                Your profile is not set
            </Typography>
            <Typography variant="body1">
                Create one
            </Typography>
            <CreateProfile/>
        </Box>
    </div>
);