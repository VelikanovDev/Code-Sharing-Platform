import React from "react";
import AppHeader from "./AppHeader";
import { Outlet, useNavigate } from "react-router-dom";
import { deleteAllSnippets } from "../services/SnippetService";
import { useAuth } from "../provider/AuthProvider";

/**
 * Layout component that provides a common structure for pages.
 * It renders the AppHeader component and an Outlet for nested routes.
 *
 */
const Layout = () => {
  const navigate = useNavigate();
  const { setToken } = useAuth();

  const handleLogout = () => {
    localStorage.clear();
    setToken(null);
    navigate("/login");
  };

  const handleDeleteAll = async () => {
    if (window.confirm("Do you really want to delete all snippets?")) {
      try {
        await deleteAllSnippets();
        window.location.reload(); // Force a full page reload
      } catch (error) {
        console.error("Error in handleAddNewSnippet", error);
        throw error;
      }
    }
  };

  return (
    <>
      <AppHeader deleteAll={handleDeleteAll} logout={handleLogout} />
      <Outlet />
    </>
  );
};

export default Layout;
