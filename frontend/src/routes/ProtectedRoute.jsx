import React from "react";
import { Navigate } from "react-router-dom";
import { useAuth } from "../provider/AuthProvider";

const ProtectedRoute = ({ children }) => {
  const { token } = useAuth();

  if (!token) {
    return <Navigate to="/login" />;
  }

  return children;
  // const loggedIn = localStorage.getItem("isLoggedIn") === "true";
  // return loggedIn ? children : <Navigate to="/login" replace />;
};

export default ProtectedRoute;
