import "./styles/App.css";
import React from "react";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import {
  createBrowserRouter,
  Navigate,
  RouterProvider,
} from "react-router-dom";
import HomePage from "./pages/HomePage";
import NewSnippetPage from "./pages/NewSnippetPage";
import EditPage from "./pages/EditPage";
import UsersPage from "./pages/UsersPage";
import Layout from "./components/Layout";
import { AuthProvider } from "./provider/AuthProvider";
import ProtectedRoute from "./routes/ProtectedRoute";
import RoleProtectedRoute from "./routes/RoleProtectedRoute";

function App() {
  const router = createBrowserRouter([
    {
      path: "/",
      element: <Layout />,
      children: [
        {
          path: "/",
          element: (
            <ProtectedRoute>
              <Navigate to="/home" replace />
            </ProtectedRoute>
          ),
        },
        {
          path: "login",
          element: <LoginPage />,
        },
        { path: "register", element: <RegisterPage /> },
        {
          path: "home",
          element: (
            <ProtectedRoute>
              <HomePage />
            </ProtectedRoute>
          ),
        },
        {
          path: "edit",
          element: (
            <ProtectedRoute>
              <EditPage />
            </ProtectedRoute>
          ),
        },
        {
          path: "new",
          element: (
            <ProtectedRoute>
              <NewSnippetPage />
            </ProtectedRoute>
          ),
        },
        {
          path: "users",
          element: (
            <RoleProtectedRoute>
              <UsersPage />
            </RoleProtectedRoute>
          ),
        },
      ],
    },
  ]);

  return (
    <AuthProvider>
      <div className="App">
        <RouterProvider router={router} />
      </div>
    </AuthProvider>
  );
}

export default App;
