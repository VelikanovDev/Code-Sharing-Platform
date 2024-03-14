import React from "react";
import AppHeader from "./AppHeader";
import { Outlet } from "react-router-dom";

/**
 * Layout component that provides a common structure for pages.
 * It renders the AppHeader component and an Outlet for nested routes.
 *
 */
const Layout = ({ isLoggedIn, deleteAll, logout }) => {
  return (
    <>
      <AppHeader
        isLoggedIn={isLoggedIn}
        username={localStorage.getItem("username")}
        role={localStorage.getItem("role")}
        deleteAll={deleteAll}
        logout={logout}
      />
      <Outlet />
    </>
  );
};

export default Layout;
