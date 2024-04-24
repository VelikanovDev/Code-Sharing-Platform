import { Navigate } from "react-router-dom";
import React from "react";
import { useAuth } from "../provider/AuthProvider";
import ProtectedRoute from "./ProtectedRoute";

const RoleProtectedRoute = ({ children }) => {
  const { userDetails } = useAuth();
  console.log("RoleProtectedRoute userDetails: ", userDetails);
  if (!userDetails) {
    return <Navigate to="/login" />;
  }
  if (userDetails.role !== "ADMIN") {
    console.log(userDetails.role + " is not ADMIN");
    return <Navigate to="/home" replace />;
  }
  return children;
};

export default RoleProtectedRoute;
