import { Navigate } from "react-router-dom";
import React from "react";
import { useAuth } from "../provider/AuthProvider";

const RoleProtectedRoute = ({ children }) => {
  const { userDetails } = useAuth();
  if (!userDetails) {
    return <Navigate to="/login" />;
  }
  if (userDetails.role !== "ADMIN") {
    return <Navigate to="/home" replace />;
  }
  return children;
};

export default RoleProtectedRoute;
