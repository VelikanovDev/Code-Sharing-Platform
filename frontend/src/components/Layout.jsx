import React from "react";
import AppHeader from "./AppHeader";
import { Outlet } from "react-router-dom";

/**
 * Layout component that provides a common structure for pages.
 * It renders the AppHeader component and an Outlet for nested routes.
 *
 */
const Layout = ({ isLoggedIn, userdata, handleDeleteAll, handleLogout }) => {
  return (
    <>
      <AppHeader
        isLoggedIn={isLoggedIn}
        username={userdata.username}
        role={userdata.role}
        deleteAll={handleDeleteAll}
        logout={handleLogout}
      />
      <Outlet />
    </>
  );
};

export default Layout;
